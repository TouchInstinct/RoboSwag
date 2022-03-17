package ru.touchin.client_services

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.huawei.hms.api.HuaweiApiAvailability

/**
 * A class with utils for interacting with Google, Huawei, etc. services
 */

class ServicesUtils {

    fun getCurrentService(context: Context): MobileService = when {
        checkHuaweiServices(context) -> MobileService.HUAWEI_SERVICE
        checkGooglePlayServices(context) -> MobileService.GOOGLE_SERVICE
        else -> MobileService.GOOGLE_SERVICE
    }

    private fun checkHuaweiServices(context: Context): Boolean =
            HuaweiApiAvailability.getInstance()
                    .isHuaweiMobileNoticeAvailable(context) == ConnectionResult.SUCCESS

    private fun checkGooglePlayServices(context: Context): Boolean =
            GoogleApiAvailability.getInstance()
                    .isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS

}
