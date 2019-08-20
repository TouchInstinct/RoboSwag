package ru.touchin.yandexmap

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.BoundingBoxHelper
import com.yandex.mapkit.geometry.LinearRing
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateSource
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapLoadStatistics
import com.yandex.mapkit.map.MapLoadedListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import ru.touchin.basemap.AbstractMapManager

class YandexMapManager(
        mapView: MapView,
        private val isDebug: Boolean = false
) : AbstractMapManager<MapView, Map, Point>(mapView), MapLoadedListener, CameraListener, InputListener, UserLocationObjectListener {

    companion object {
        private const val CAMERA_ANIMATION_DURATION = 1f
        private const val CAMERA_OFFSET_ZOOM = 3f

        // Call in Application.onCreate()
        fun setApiKey(apiKey: String) = MapKitFactory.setApiKey(apiKey)

    }

    private val userLocationLayer by lazy {
        MapKitFactory.getInstance().createUserLocationLayer(mapView.mapWindow).also {
            it.isVisible = false
            it.setObjectListener(this)
        }
    }

    private var userLocationIconProvider : ImageProvider? = null
    private var userLocationAccuracyCirceColor: Int? = null

    init {
        MapKitFactory.initialize(mapView.context)
    }

    override fun initialize(mapListener: AbstractMapListener<MapView, Map, Point>?) {
        super.initialize(mapListener)
        initMap(mapView.map)
    }

    override fun initMap(map: Map) {
        super.initMap(map)
        map.isDebugInfoEnabled = isDebug
        map.setMapLoadedListener(this)
        map.addCameraListener(this)
        map.addInputListener(this)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        mapView.onStop()
    }

    override fun onMapLoaded(statistics: MapLoadStatistics) {
        mapListener?.onMapLoaded()
    }

    override fun onCameraPositionChanged(map: Map, cameraPosition: CameraPosition, cameraUpdateSource: CameraUpdateSource, finished: Boolean) {
        mapListener?.onCameraMoved(finished)
    }

    override fun onMapLongTap(map: Map, point: Point) {
        mapListener?.onMapLongTap(point)
    }

    override fun onMapTap(map: Map, point: Point) {
        mapListener?.onMapTap(point)
    }

    override fun getCameraTarget(): Point = map.cameraPosition.target

    override fun getCameraZoom(): Float = map.cameraPosition.zoom

    override fun getCameraAzimuth(): Float = map.cameraPosition.azimuth

    override fun getCameraTilt(): Float = map.cameraPosition.tilt

    override fun moveCamera(target: Point, zoom: Float, azimuth: Float, tilt: Float) {
        map.move(CameraPosition(target, zoom, azimuth, tilt), Animation(Animation.Type.LINEAR, CAMERA_ANIMATION_DURATION), null)
    }

    override fun smoothMoveCamera(target: Point, zoom: Float, azimuth: Float, tilt: Float) {
        map.move(CameraPosition(target, zoom, azimuth, tilt), Animation(Animation.Type.SMOOTH, CAMERA_ANIMATION_DURATION), null)
    }

    override fun smoothMoveCamera(targets: List<Point>, padding: Int) {
        val boundingBox = BoundingBoxHelper.getBounds(LinearRing(targets))
        val cameraPosition = map.cameraPosition(boundingBox)
        smoothMoveCamera(cameraPosition.target, cameraPosition.zoom - CAMERA_OFFSET_ZOOM)
    }

    override fun smoothMoveCamera(targets: List<Point>, width: Int, height: Int, padding: Int) {
        smoothMoveCamera(targets)
    }

    override fun setMapAllGesturesEnabled(enabled: Boolean) {
        map.isRotateGesturesEnabled = enabled
        map.isScrollGesturesEnabled = enabled
        map.isTiltGesturesEnabled = enabled
        map.isZoomGesturesEnabled = enabled
    }

    override fun setMyLocationEnabled(enabled: Boolean) {
        userLocationLayer.isVisible = enabled
    }

    override fun isLocationInVisibleRegion(location: Point): Boolean = with(map.visibleRegion) {
        topLeft.latitude > location.latitude && topLeft.longitude < location.longitude
                && topRight.latitude > location.latitude && topRight.longitude > location.longitude
                && bottomLeft.latitude < location.latitude && bottomLeft.longitude < location.longitude
                && bottomRight.latitude < location.latitude && bottomRight.longitude > location.longitude
    }

    override fun onObjectAdded(view: UserLocationView) {
        userLocationIconProvider?.let {  imageProvider ->
            view.arrow.setIcon(imageProvider)
            view.pin.setIcon(imageProvider)
        }
        userLocationAccuracyCirceColor?.let {
            view.accuracyCircle.fillColor = it }
    }

    override fun onObjectUpdated(view: UserLocationView, event: ObjectEvent) = Unit

    override fun onObjectRemoved(view: UserLocationView) = Unit

    fun setUserLocationIcon(@DrawableRes icon: Int, @ColorInt accuracyCircleColor: Int? = null) {
        userLocationIconProvider = ImageProvider.fromResource(mapView.context, icon)
        userLocationAccuracyCirceColor = accuracyCircleColor
    }

    fun getVisibleRegion() = map.visibleRegion

    fun getMapObjects() = map.mapObjects

    interface MapListener : AbstractMapListener<MapView, Map, Point>

}
