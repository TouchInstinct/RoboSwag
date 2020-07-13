package ru.touchin.templates

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Process
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import ru.touchin.templates.DeviceUtils.NetworkType

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

fun getMobileNetworkType(info: NetworkInfo): NetworkType =
        when (info.subtype) {
            TelephonyManager.NETWORK_TYPE_GPRS,
            TelephonyManager.NETWORK_TYPE_EDGE,
            TelephonyManager.NETWORK_TYPE_CDMA,
            TelephonyManager.NETWORK_TYPE_1xRTT,
            TelephonyManager.NETWORK_TYPE_IDEN -> NetworkType.MOBILE_2G
            TelephonyManager.NETWORK_TYPE_UMTS,
            TelephonyManager.NETWORK_TYPE_EVDO_0,
            TelephonyManager.NETWORK_TYPE_EVDO_A,
            TelephonyManager.NETWORK_TYPE_HSDPA,
            TelephonyManager.NETWORK_TYPE_HSUPA,
            TelephonyManager.NETWORK_TYPE_HSPA,
            TelephonyManager.NETWORK_TYPE_EVDO_B,
            TelephonyManager.NETWORK_TYPE_EHRPD,
            TelephonyManager.NETWORK_TYPE_HSPAP -> NetworkType.MOBILE_3G
            TelephonyManager.NETWORK_TYPE_LTE,
            19 -> NetworkType.MOBILE_LTE
            TelephonyManager.NETWORK_TYPE_UNKNOWN -> NetworkType.UNKNOWN
            else -> NetworkType.UNKNOWN
        }


@RequiresPermission(anyOf = [Manifest.permission.USE_FINGERPRINT, Manifest.permission.USE_BIOMETRIC])
fun Context.canAuthenticateWithBiometrics(): Boolean {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        val fingerprintManagerCompat = FingerprintManagerCompat.from(this)
        fingerprintManagerCompat.hasEnrolledFingerprints() && fingerprintManagerCompat.isHardwareDetected
    } else {
        getSystemService(BiometricManager::class.java)?.let { biometricManager ->
            (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS)
        } ?: false
    }
}
