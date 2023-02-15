package ru.touchin.yandexmap

import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.Cluster
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.ClusterTapListener
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView

class YandexPlacemarkManager<TPoint : PointModel>(
        private val mapItemRenderer: YandexMapItemRenderer<TPoint>,
        private val clusterTapAction: (Cluster) -> Boolean,
        private val markerTapAction: (MapObject, Point) -> Boolean,
) : ClusterListener, ClusterTapListener, MapObjectTapListener {

    private companion object {
        const val DEFAULT_CLUSTER_RADIUS = 42.0
        const val DEFAULT_MIN_ZOOM = 35
    }

    private val markers = mutableListOf<TPoint>()
    private var placemarkCollection: ClusterizedPlacemarkCollection? = null

    fun setItems(items: Collection<TPoint>) {
        markers.clear()
        markers.addAll(items)
    }

    fun addMarkersOnMap(
            mapView: MapView,
            clusterRadius: Double = DEFAULT_CLUSTER_RADIUS,
            minZoom: Int = DEFAULT_MIN_ZOOM
    ) {
        removeMarkers()

        val clusterizedCollection = mapView.map.mapObjects.addClusterizedPlacemarkCollection(this)
        val addedPlacemarks = clusterizedCollection.addEmptyPlacemarks(markers.map { it.point })

        addedPlacemarks.forEachIndexed { index, placemark ->
            val placemarkItem = markers[index]
            placemark.userData = placemarkItem
            mapItemRenderer.getClusterItemIcon(placemarkItem)?.let {
                placemark.setView(it)
            }
        }

        clusterizedCollection.clusterPlacemarks(clusterRadius, minZoom)
        placemarkCollection = clusterizedCollection
    }

    fun addTapListener(mapView: MapView) {
        mapView.map.mapObjects.addTapListener(this)
    }

    fun removeTapListener(mapView: MapView) {
        mapView.map.mapObjects.removeTapListener(this)
    }

    fun removeMarkers() {
        placemarkCollection?.clear()
    }

    override fun onClusterAdded(cluster: Cluster) {
        val clusterIcon = mapItemRenderer.getClusterIcon(cluster.toPlacemarkList())
        clusterIcon?.let(cluster.appearance::setView)
        cluster.addClusterTapListener(this)
    }

    override fun onClusterTap(cluster: Cluster) = clusterTapAction.invoke(cluster)

    override fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean =
            markerTapAction.invoke(mapObject, point)

    @Suppress("UNCHECKED_CAST")
    private fun Cluster.toPlacemarkList() = placemarks.map { it.userData as TPoint }
}
