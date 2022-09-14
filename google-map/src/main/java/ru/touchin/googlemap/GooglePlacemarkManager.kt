package ru.touchin.googlemap

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.VisibleRegion
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.Algorithm
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm
import com.google.maps.android.clustering.algo.PreCachingAlgorithmDecorator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.sample

class GooglePlacemarkManager<TClusterItem : ClusterItem>(
        context: Context,
        private val lifecycleOwner: LifecycleOwner,
        private val googleMap: GoogleMap,
        clusterItemTapAction: (TClusterItem) -> Boolean,
        clusterTapAction: (Cluster<TClusterItem>) -> Boolean,
        clusterAlgorithm: Algorithm<TClusterItem> = PreCachingAlgorithmDecorator(NonHierarchicalDistanceBasedAlgorithm())
) : ClusterManager<TClusterItem>(context, googleMap), GoogleMap.OnCameraIdleListener {

    private var clusteringJob: Job? = null
    private val onVisibilityChangedEvent = MutableSharedFlow<Pair<VisibleRegion?, List<TClusterItem>>>(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var cameraIdleJob: Job? = null
    private val onCameraIdleEvent = MutableSharedFlow<Boolean>(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val markers = mutableListOf<TClusterItem>()
    private var lastVisibleItems = emptyList<TClusterItem>()
    private var onCameraIdleListener: (() -> Unit)? = null

    var clusterRenderer: GoogleMapItemRenderer<TClusterItem>? = null
        set(value) {
            field = value
            setRenderer(value)
        }

    private val lock = Any()

    init {
        googleMap.setOnCameraIdleListener(this)
        googleMap.setOnMarkerClickListener(this)
        setAlgorithm(clusterAlgorithm)
        setOnClusterClickListener(clusterTapAction)
        setOnClusterItemClickListener(clusterItemTapAction)
    }

    fun setItems(items: Collection<TClusterItem>) {
        synchronized(lock) {
            markers.clear()
            markers.addAll(items)
            onDataChanged()
        }
    }

    override fun addItems(items: Collection<TClusterItem>) {
        synchronized(lock) {
            markers.addAll(items)
            onDataChanged()
        }
    }

    override fun addItem(clusterItem: TClusterItem) {
        synchronized(lock) {
            markers.add(clusterItem)
            onDataChanged()
        }
    }

    override fun removeItem(atmClusterItem: TClusterItem) {
        synchronized(lock) {
            markers.remove(atmClusterItem)
            onDataChanged()
        }
    }

    override fun clearItems() {
        synchronized(lock) {
            markers.clear()
            onDataChanged()
        }
    }

    override fun onCameraIdle() {
        onDataChanged()
        onCameraIdleEvent.tryEmit(true)
    }

    private fun onDataChanged() {
        onVisibilityChangedEvent.tryEmit(getData())
    }

    private fun getData(): Pair<VisibleRegion?, List<TClusterItem>> =
            googleMap.projection.visibleRegion to markers

    private fun listenToCameraIdleEvents() {
        cameraIdleJob = lifecycleOwner.lifecycleScope.launchWhenStarted {
            onCameraIdleEvent
                    .debounce(CAMERA_DEBOUNCE_MILLI)
                    .flowOn(Dispatchers.Main)
                    .collect {
                        onCameraIdleListener?.invoke()
                    }
        }
    }

    fun startClustering() {
        if (clusteringJob != null || cameraIdleJob != null) return
        clusteringJob = lifecycleOwner.lifecycleScope.launchWhenStarted {
            onVisibilityChangedEvent
                    .debounce(CLUSTERING_START_DEBOUNCE_MILLI)
                    .flowOn(Dispatchers.Default)
                    .onStart { emit(getData()) }
                    .filter { (visibleRegion, _) -> visibleRegion != null }
                    .map { (visibleRegion, clusteringItems) ->
                        clusteringItems.filter { visibleRegion?.latLngBounds?.contains(it.position) == true }
                    }
                    .sample(CLUSTERING_NEW_LIST_CONSUMING_THROTTLE_MILLIS)
                    .catch { emit(lastVisibleItems) }
                    .flowOn(Dispatchers.Main)
                    .collect { markersToShow ->
                        lastVisibleItems = markersToShow
                        super.clearItems()
                        super.addItems(markersToShow)
                        cluster()
                    }
        }
        listenToCameraIdleEvents()
    }

    fun stopClustering() {
        clusteringJob?.cancel()
        cameraIdleJob?.cancel()
    }

    fun setOnCameraIdleListener(listener: () -> Unit) {
        if (onCameraIdleListener != null) return
        onCameraIdleListener = listener
    }

    private companion object {
        const val CAMERA_DEBOUNCE_MILLI = 50L

        const val CLUSTERING_START_DEBOUNCE_MILLI = 50L

        const val CLUSTERING_NEW_LIST_CONSUMING_THROTTLE_MILLIS = 350L
    }

}
