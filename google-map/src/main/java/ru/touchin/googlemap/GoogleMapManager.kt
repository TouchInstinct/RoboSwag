package ru.touchin.googlemap

import android.os.Bundle
import androidx.annotation.RequiresPermission
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import ru.touchin.basemap.AbstractMapManager

@Suppress("detekt.TooManyFunctions")
class GoogleMapManager(mapView: MapView) : AbstractMapManager<MapView, GoogleMap, LatLng>(mapView) {

    companion object {
        private const val CAMERA_ANIMATION_DURATION = 1f
        private const val CAMERA_DEFAULT_STEP = 2
    }

    override fun initialize(mapListener: AbstractMapListener<MapView, GoogleMap, LatLng>?) {
        super.initialize(mapListener)
        mapView.getMapAsync(this::initMap)
    }

    override fun initMap(map: GoogleMap) {
        super.initMap(map)
        with(map) {
            setOnMapLoadedCallback { mapListener?.onMapLoaded() }
            setOnCameraMoveStartedListener { mapListener?.onCameraMoved(true) }
            setOnCameraMoveListener { mapListener?.onCameraMoved(true) }
            setOnMapClickListener { location -> mapListener?.onMapTap(location) }
            setOnMapLongClickListener { location -> mapListener?.onMapLongTap(location) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun getCameraTarget(): LatLng = map.cameraPosition.target

    override fun getCameraZoom(): Float = map.cameraPosition.zoom

    override fun getCameraAzimuth(): Float = map.cameraPosition.bearing

    override fun getCameraTilt(): Float = map.cameraPosition.tilt

    override fun getDefaultDuration(): Float = CAMERA_ANIMATION_DURATION

    override fun getDefaultZoomStep(): Int = CAMERA_DEFAULT_STEP

    override fun moveCamera(target: LatLng, zoom: Float, azimuth: Float, tilt: Float) {
        map.moveCamera(CameraUpdateFactory.newCameraPosition(buildCameraPosition(target, zoom, azimuth, tilt)))
    }

    override fun smoothMoveCamera(target: LatLng, zoom: Float, azimuth: Float, tilt: Float, animationDuration: Float) {
        map.animateCamera(
                CameraUpdateFactory.newCameraPosition(buildCameraPosition(target, zoom, azimuth, tilt)),
                animationDuration.toInt(),
                null
        )
    }

    override fun smoothMoveCamera(targets: List<LatLng>, padding: Int, animationDuration: Float) {
        val boundingBox = getBoundingBox(targets)
        map.animateCamera(
                CameraUpdateFactory.newLatLngBounds(boundingBox, padding),
                animationDuration.toInt(),
                null
        )
    }

    override fun smoothMoveCamera(targets: List<LatLng>, width: Int, height: Int, padding: Int, animationDuration: Float) {
        val boundingBox = getBoundingBox(targets)
        map.animateCamera(
                CameraUpdateFactory.newLatLngBounds(boundingBox, width, height, padding),
                animationDuration.toInt(),
                null
        )
    }

    override fun increaseZoom(target: LatLng, zoomIncreaseValue: Int) {
        smoothMoveCamera(
                target = target,
                zoom = getCameraZoom() + zoomIncreaseValue
        )
    }

    override fun decreaseZoom(target: LatLng, zoomDecreaseValue: Int) {
        smoothMoveCamera(
                target = target,
                zoom = getCameraZoom() - zoomDecreaseValue
        )
    }

    override fun setMapAllGesturesEnabled(enabled: Boolean) = map.uiSettings.setAllGesturesEnabled(enabled)

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    override fun setMyLocationEnabled(enabled: Boolean) {
        map.isMyLocationEnabled = enabled
    }

    override fun isLocationInVisibleRegion(location: LatLng): Boolean = map.projection.visibleRegion.latLngBounds.contains(location)

    private fun getBoundingBox(targets: List<LatLng>) = LatLngBounds.builder()
            .also { builder ->
                targets.forEach { target -> builder.include(target) }
            }
            .build()

    private fun buildCameraPosition(target: LatLng, zoom: Float, azimuth: Float, tilt: Float) = CameraPosition.Builder()
            .target(target)
            .zoom(zoom)
            .bearing(azimuth)
            .tilt(tilt)
            .build()

    interface MapListener : AbstractMapListener<MapView, GoogleMap, LatLng>

}
