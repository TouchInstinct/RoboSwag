package ru.touchin.recaptcha

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.huawei.hms.api.HuaweiApiAvailability

//TODO: in the future move to a module with services
object ServicesUtils {

    fun checkHuaweiServices(context: Context): Boolean =
            HuaweiApiAvailability.getInstance()
                    .isHuaweiMobileNoticeAvailable(context) == ConnectionResult.SUCCESS

    fun checkGooglePlayServices(context: Context): Boolean =
            GoogleApiAvailability.getInstance()
                    .isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS

}
