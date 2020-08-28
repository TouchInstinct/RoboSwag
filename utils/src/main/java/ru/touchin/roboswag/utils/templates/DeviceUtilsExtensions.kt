package ru.touchin.roboswag.utils.templates

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Process
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import ru.touchin.roboswag.utils.templates.DeviceUtils.NetworkType
import ru.touchin.roboswag.utils.templates.DeviceUtils.getMobileNetworkType

fun Context.isNetworkConnected(): Boolean = getNetworkType() != NetworkType.NONE

fun Context.getNetworkType(): NetworkType {
    if (checkPermission(Manifest.permission.ACCESS_NETWORK_STATE, Process.myPid(), Process.myUid())
            != PackageManager.PERMISSION_GRANTED) {
        return NetworkType.UNKNOWN
    }
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?
            ?: return NetworkType.UNKNOWN
    @SuppressLint("MissingPermission") val info = cm.activeNetworkInfo
    return if (info == null || !info.isConnected) {
        NetworkType.NONE
    } else when (info.type) {
        ConnectivityManager.TYPE_WIFI -> NetworkType.WI_FI
        ConnectivityManager.TYPE_MOBILE -> getMobileNetworkType(info)
        else -> NetworkType.UNKNOWN
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@Suppress("InlinedApi")
@RequiresPermission(anyOf = [Manifest.permission.USE_FINGERPRINT, Manifest.permission.USE_BIOMETRIC])
fun Context.canAuthenticateWithBiometrics(): Boolean = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
    val fingerprintManagerCompat = FingerprintManagerCompat.from(this)
    fingerprintManagerCompat.hasEnrolledFingerprints() && fingerprintManagerCompat.isHardwareDetected
} else {
    getSystemService(BiometricManager::class.java)?.let { biometricManager ->
        biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
    } ?: false
}
