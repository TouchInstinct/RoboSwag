package ru.touchin.googlemap

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import ru.touchin.basemap.BaseIconGenerator

open class GoogleMapItemRenderer<TClusterItem : ClusterItem>(
        val context: Context,
        googleMap: GoogleMap,
        clusterManager: ClusterManager<TClusterItem>,
        private val minClusterItemSize: Int = 1
) : DefaultClusterRenderer<TClusterItem>(context, googleMap, clusterManager) {

    var iconGenerator: BaseIconGenerator<TClusterItem, Cluster<TClusterItem>, BitmapDescriptor> =
            GoogleIconGenerator<TClusterItem>(context).apply { setDefaultViewAndBackground() }

    override fun shouldRenderAsCluster(cluster: Cluster<TClusterItem>): Boolean =
            cluster.size > minClusterItemSize

    override fun onBeforeClusterItemRendered(item: TClusterItem, markerOptions: MarkerOptions) {
        markerOptions.icon(getMarkerIcon(item))
    }

    override fun onBeforeClusterRendered(cluster: Cluster<TClusterItem>, markerOptions: MarkerOptions) {
        markerOptions.icon(getClusterIcon(cluster = cluster))
    }

    private fun getMarkerIcon(item: TClusterItem): BitmapDescriptor? =
            iconGenerator.getClusterItemView(item)

    private fun getClusterIcon(cluster: Cluster<TClusterItem>): BitmapDescriptor? =
            iconGenerator.getClusterView(cluster)

}
