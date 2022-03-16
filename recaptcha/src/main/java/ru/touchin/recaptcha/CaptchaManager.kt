package ru.touchin.recaptcha

import android.app.Activity

/**
 * onNewTokenReceived - callback на успешную проверку каптчи
 * processThrowable - callback на ошибку проверки каптчи
 */

class CaptchaManager(
        onNewTokenReceived: (String) -> Unit,
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
