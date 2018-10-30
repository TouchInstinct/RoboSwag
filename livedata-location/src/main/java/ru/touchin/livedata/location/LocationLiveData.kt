package ru.touchin.livedata.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

class LocationLiveData(
        private val context: Context,
        private val request: LocationRequest
) : MutableLiveData<Location>() {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            postValue(result?.lastLocation)
        }

        override fun onLocationAvailability(availability: LocationAvailability) {
            if (!availability.isLocationAvailable) postValue(null)
        }
    }

    override fun onActive() {
        super.onActive()
        startListening()
    }

    override fun onInactive() {
        super.onInactive()
        stopListening()
    }

    private fun isLocationPermissionAccessed() = ContextCompat
            .checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun stopListening() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startListening() {
        if (isLocationPermissionAccessed()) {
            fusedLocationClient.requestLocationUpdates(
                    request,
                    locationCallback,
                    null
            )
        } else {
            throw ShouldNotHappenException("Location permission is not granted")
        }
    }

}