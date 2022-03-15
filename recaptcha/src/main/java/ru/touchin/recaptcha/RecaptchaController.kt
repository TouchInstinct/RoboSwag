package ru.touchin.recaptcha

import android.app.Activity
import com.google.android.gms.safetynet.SafetyNet
import com.huawei.hms.support.api.safetydetect.SafetyDetect
import ru.touchin.recaptcha.ServicesUtils.checkGooglePlayServices
import ru.touchin.recaptcha.ServicesUtils.checkHuaweiServices

class RecaptchaController(
        private val activity: Activity,
        private val googleRecaptchaKey: String? = null,
        private val huaweiAppId: String? = null,
        private val onNewTokenReceived: (String) -> Unit,
        private val processThrowable: (Throwable) -> Unit
) {

    fun showRecaptchaAlert() {
        when {
            checkHuaweiServices(activity) -> showHuaweiCaptcha()
            checkGooglePlayServices(activity) -> showGoogleCaptcha()
        }
    }

    private fun showGoogleCaptcha() {
        if (googleRecaptchaKey != null) {
            SafetyNet.getClient(activity)
                    .verifyWithRecaptcha(googleRecaptchaKey)
                    .addOnSuccessListener(activity) { response ->
                        onServiceTokenResponse(response?.tokenResult)
                    }
                    .addOnFailureListener(activity, processThrowable)
        }
    }

    private fun showHuaweiCaptcha() {
        if (huaweiAppId != null) {
            val huaweiSafetyDetectClient = SafetyDetect.getClient(activity)
            huaweiSafetyDetectClient.initUserDetect()
                    .addOnSuccessListener {
                        huaweiSafetyDetectClient.userDetection(huaweiAppId)
                                .addOnSuccessListener { response ->
                                    onServiceTokenResponse(response?.responseToken)
                                }
                                .addOnFailureListener(activity, processThrowable)
                    }
                    .addOnFailureListener(activity, processThrowable)
        }
    }

    private fun onServiceTokenResponse(newToken: String?) {
        if (!newToken.isNullOrBlank()) {
            onNewTokenReceived.invoke(newToken)
        } else {
            processThrowable.invoke(EmptyCaptchaTokenException())
        }
    }

}

class EmptyCaptchaTokenException : Throwable("Captcha token is empty")
