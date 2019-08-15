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

class GoogleMapManager(mapView: MapView) : AbstractMapManager<MapView, GoogleMap, LatLng>(mapView) {

    override fun initialize(mapListener: AbstractMapListener<MapView, GoogleMap, LatLng>?) {
        super.initialize(mapListener)
        mapView.getMapAsync(::initMap)
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

    override fun moveCamera(target: LatLng, zoom: Float) {
        map.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.Builder().target(target).zoom(zoom).build()))
    }

    override fun smoothMoveCamera(target: LatLng, zoom: Float) {
        map.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.Builder().target(target).zoom(zoom).build()))
    }

    override fun smoothMoveCamera(targets: List<LatLng>, padding: Int) {
        val boundingBox = getBoundingBox(targets)
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(boundingBox, padding))
    }

    override fun smoothMoveCamera(targets: List<LatLng>, width: Int, height: Int, padding: Int) {
        val boundingBox = getBoundingBox(targets)
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(boundingBox, width, height, padding))
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

    interface MapListener : AbstractMapListener<MapView, GoogleMap, LatLng>

}
