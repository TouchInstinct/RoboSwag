package ru.touchin.recaptcha

import android.app.Activity
import com.huawei.hms.support.api.safetydetect.SafetyDetect

class HuaweiCaptchaClient(
        onNewTokenReceived: (String) -> Unit,
        private val processThrowable: (Throwable) -> Unit
) : CaptchaClient(onNewTokenReceived, processThrowable) {

    override fun showCaptcha(activity: Activity, captchaKey: String) {
        val huaweiSafetyDetectClient = SafetyDetect.getClient(activity)
        huaweiSafetyDetectClient.initUserDetect()
                .addOnSuccessListener {
                    huaweiSafetyDetectClient.userDetection(captchaKey)
                            .addOnSuccessListener { response ->
                                onServiceTokenResponse(response?.responseToken)
                            }
                            .addOnFailureListener(activity, processThrowable)
                }
                .addOnFailureListener(activity, processThrowable)
    }

}
