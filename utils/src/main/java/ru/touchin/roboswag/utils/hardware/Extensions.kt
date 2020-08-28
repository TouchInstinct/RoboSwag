package ru.touchin.roboswag.utils.hardware

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.VibrationEffect.DEFAULT_AMPLITUDE
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission

@RequiresPermission(Manifest.permission.VIBRATE)
@RequiresApi(Build.VERSION_CODES.O)
fun Context.startVibrate(vibrationEffect: VibrationEffect) {
    (this.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator)?.vibrate(vibrationEffect)

}

@RequiresPermission(Manifest.permission.VIBRATE)
fun Context.startVibrate(duration: Long = 500, pattern: LongArray = LongArray(0)) {
    (this.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator)?.let { vibrationService ->
        if (pattern.isEmpty()) {
            vibrationService.vibrate(duration)
        } else {
            vibrationService.vibrate(pattern, duration.toInt())
        }
    }
}

@RequiresPermission(Manifest.permission.VIBRATE)
fun Context.startSimpleVibration(duration: Long = 200) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startVibrate(VibrationEffect.createOneShot(duration, DEFAULT_AMPLITUDE))
    } else {
        startVibrate(duration)
    }
}

@RequiresPermission(Manifest.permission.VIBRATE)
fun Context.cancelVibrate() {
    (this.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator)?.cancel()
}
