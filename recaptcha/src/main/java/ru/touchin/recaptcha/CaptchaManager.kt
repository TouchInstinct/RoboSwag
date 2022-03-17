package ru.touchin.recaptcha

import android.app.Activity
import ru.touchin.client_services.MobileService
import ru.touchin.client_services.ServicesUtils

/**
 * A class for displaying a dialog with a captcha
 * with a check on the current service of the application
 *
 * @param onNewTokenReceived - callback for a successful captcha check, return token
 * @param processThrowable - callback for a captcha check error, return throwable
 */

class CaptchaManager(
        private val onNewTokenReceived: (String) -> Unit,
        private val processThrowable: (Throwable) -> Unit
) {

    private val clientsMap = mapOf(
            MobileService.GOOGLE_SERVICE to GoogleCaptchaClient(onNewTokenReceived, processThrowable),
            MobileService.HUAWEI_SERVICE to HuaweiCaptchaClient(onNewTokenReceived, processThrowable)
    )

    fun showRecaptchaAlert(activity: Activity, captchaKey: String) {
        if (captchaKey.isBlank()) {
            processThrowable.invoke(EmptyCaptchaKeyException())
        } else {
            val service = ServicesUtils().getCurrentService(activity)
            clientsMap[service]?.showCaptcha(activity, captchaKey)
        }
    }

}
