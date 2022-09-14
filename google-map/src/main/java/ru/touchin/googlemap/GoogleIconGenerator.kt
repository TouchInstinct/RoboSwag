package ru.touchin.googlemap

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.ui.IconGenerator
import ru.touchin.basemap.BaseIconGenerator
import ru.touchin.basemap.getOrPutIfNotNull

open class GoogleIconGenerator<T : ClusterItem>(
        private val context: Context
) : IconGenerator(context), BaseIconGenerator<T, Cluster<T>, BitmapDescriptor> {

    init {
        setBackground(ContextCompat.getDrawable(context, R.drawable.default_cluster_background))
        LayoutInflater
                .from(context)
                .inflate(R.layout.view_google_map_cluster_item, null)
                .apply {
                    setContentView(this)
                }
    }

    private val clusterIconsCache = SparseArray<BitmapDescriptor>()
    private val clusterItemIconsCache = mutableMapOf<T, BitmapDescriptor>()

    override fun getClusterIcon(cluster: Cluster<T>): BitmapDescriptor? {
        val clusterSize = cluster.size
        return BitmapDescriptorFactory.fromBitmap(makeIcon(clusterSize.toString()))
    }

    override fun getClusterItemIcon(clusterItem: T): BitmapDescriptor? =
            context.getDrawable(ru.touchin.basemap.R.drawable.marker_default_icon)?.let {
                BitmapDescriptorFactory.fromBitmap(it.toBitmap())
            }

    override fun getClusterItemView(clusterItem: T): BitmapDescriptor? =
            clusterItemIconsCache.getOrPutIfNotNull(clusterItem) { getClusterItemIcon(clusterItem) }

    override fun getClusterView(cluster: Cluster<T>): BitmapDescriptor? =
            clusterIconsCache.getOrPutIfNotNull(cluster.size) { getClusterIcon(cluster) }
}
