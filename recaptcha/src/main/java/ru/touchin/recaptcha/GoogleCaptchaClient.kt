package ru.touchin.recaptcha

import android.app.Activity
import com.google.android.gms.safetynet.SafetyNet

class GoogleCaptchaClient(
        onNewTokenReceived: (String) -> Unit,
        private val processThrowable: (Throwable) -> Unit
) : CaptchaClient(onNewTokenReceived, processThrowable) {

    override fun showCaptcha(activity: Activity, captchaKey: String) {
        SafetyNet.getClient(activity)
                .verifyWithRecaptcha(captchaKey)
                .addOnSuccessListener(activity) { response ->
                    onServiceTokenResponse(response?.tokenResult)
                }
                .addOnFailureListener(activity, processThrowable)
    }

}
