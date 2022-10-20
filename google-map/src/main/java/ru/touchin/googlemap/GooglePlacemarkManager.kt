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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.sample

@OptIn(FlowPreview::class)

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

    var onCameraIdleListener: (() -> Unit)? = null

    var clusterRenderer: GoogleMapItemRenderer<TClusterItem>? = null
        set(value) {
            field = value
            setRenderer(value)
        }

    init {
        googleMap.setOnCameraIdleListener(this)
        googleMap.setOnMarkerClickListener(this)
        setAlgorithm(clusterAlgorithm)
        setOnClusterClickListener(clusterTapAction)
        setOnClusterItemClickListener(clusterItemTapAction)
    }

    @Synchronized
    override fun addItems(items: Collection<TClusterItem>) {
        markers.addAll(items)
        onDataChanged()
    }

    @Synchronized
    override fun addItem(clusterItem: TClusterItem) {
        markers.add(clusterItem)
        onDataChanged()
    }

    @Synchronized
    override fun removeItem(atmClusterItem: TClusterItem) {
        markers.remove(atmClusterItem)
        onDataChanged()
    }

    @Synchronized
    override fun clearItems() {
        markers.clear()
        onDataChanged()
    }

    override fun onCameraIdle() {
        onDataChanged()
        onCameraIdleEvent.tryEmit(true)
    }

    @Synchronized
    fun setItems(items: Collection<TClusterItem>) {
        markers.clear()
        markers.addAll(items)
        onDataChanged()
    }

    fun startClustering() {
        if (clusteringJob != null || cameraIdleJob != null) return
        clusteringJob = lifecycleOwner.lifecycleScope.launchWhenStarted {
            onVisibilityChangedEvent
                    .debounce(CLUSTERING_START_DEBOUNCE_MILLI)
                    .flowOn(Dispatchers.Default)
                    .onStart { emit(getData()) }
                    .mapNotNull { (region, items) -> findItemsInRegion(region, items) }
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

    fun stopClustering() {
        clusteringJob?.cancel()
        cameraIdleJob?.cancel()
    }

    private fun onDataChanged() {
        onVisibilityChangedEvent.tryEmit(getData())
    }

    private fun getData(): Pair<VisibleRegion?, List<TClusterItem>> =
            googleMap.projection.visibleRegion to markers

    private fun findItemsInRegion(region: VisibleRegion?, items: List<TClusterItem>): List<TClusterItem>? =
            region?.let { items.filter { item -> item.position in region.latLngBounds } }

    private companion object {
        const val CAMERA_DEBOUNCE_MILLI = 50L

        const val CLUSTERING_START_DEBOUNCE_MILLI = 50L

        const val CLUSTERING_NEW_LIST_CONSUMING_THROTTLE_MILLIS = 350L
    }

}
