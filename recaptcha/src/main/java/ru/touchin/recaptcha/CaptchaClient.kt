package ru.touchin.recaptcha

import android.app.Activity

abstract class CaptchaClient(
        private val onNewTokenReceived: (String) -> Unit = {},
        private val processThrowable: (Throwable) -> Unit = {}
) {

    abstract fun showCaptcha(activity: Activity, captchaKey: String)

    protected fun onServiceTokenResponse(newToken: String?) {
        if (!newToken.isNullOrBlank()) {
            onNewTokenReceived.invoke(newToken)
        } else {
            processThrowable.invoke(EmptyCaptchaTokenException())
        }
    }

}

class EmptyCaptchaKeyException : Throwable("Captcha key is empty")

class EmptyCaptchaTokenException : Throwable("Captcha token is empty")
