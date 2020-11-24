package ru.touchin.basemap

import android.os.Bundle
import android.view.View

@Suppress("detekt.TooManyFunctions")
abstract class AbstractMapManager<TMapView : View, TMap : Any, TLocation : Any>(protected val mapView: TMapView) {

    protected lateinit var map: TMap

    protected var mapListener: AbstractMapListener<TMapView, TMap, TLocation>? = null

    abstract fun getCameraTarget(): TLocation

    abstract fun getCameraZoom(): Float

    abstract fun getCameraAzimuth(): Float

    abstract fun getCameraTilt(): Float

    abstract fun moveCamera(
            target: TLocation,
            zoom: Float = getCameraZoom(),
            azimuth: Float = getCameraAzimuth(),
            tilt: Float = getCameraTilt()
    )

    abstract fun smoothMoveCamera(
            target: TLocation,
            zoom: Float = getCameraZoom(),
            azimuth: Float = getCameraAzimuth(),
            tilt: Float = getCameraTilt()
    )

    abstract fun smoothMoveCamera(targets: List<TLocation>, padding: Int = 0)

    abstract fun smoothMoveCamera(targets: List<TLocation>, width: Int, height: Int, padding: Int)

    abstract fun setMapAllGesturesEnabled(enabled: Boolean)

    abstract fun setMyLocationEnabled(enabled: Boolean)

    abstract fun isLocationInVisibleRegion(location: TLocation): Boolean

    open fun initialize(mapListener: AbstractMapListener<TMapView, TMap, TLocation>? = null) {
        this.mapListener = mapListener
    }

    open fun onCreate(savedInstanceState: Bundle?) = Unit

    open fun onDestroy() = Unit

    open fun onStart() = Unit

    open fun onStop() = Unit

    open fun onResume() = Unit

    open fun onPause() = Unit

    open fun onLowMemory() = Unit

    open fun onSaveInstanceState(outState: Bundle) = Unit

    protected open fun initMap(map: TMap) {
        this.map = map
        this.mapListener?.onMapInitialized(map)
    }

    interface AbstractMapListener<TMapView, TMap, TLocation> {

        fun onMapInitialized(map: TMap)

        fun onMapLoaded() = Unit

        fun onMapTap(location: TLocation) = Unit

        fun onMapLongTap(location: TLocation) = Unit

        fun onCameraMoved(finished: Boolean) = Unit

    }

}
