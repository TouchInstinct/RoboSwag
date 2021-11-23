package ru.touchin.utils

import android.os.SystemClock
import ru.touchin.extensions.RIPPLE_EFFECT_DELAY_MS

object ActionThrottler {

    // It is necessary because in interval after ripple effect finish and before
    // action invoking start user may be in time to click and launch action again
    private const val PREVENTION_OF_CLICK_AGAIN_COEFFICIENT = 2
    private const val DELAY_MS = PREVENTION_OF_CLICK_AGAIN_COEFFICIENT * RIPPLE_EFFECT_DELAY_MS

    const val DEFAULT_THROTTLE_DELAY = 500L
    private var lastActionTime = 0L

    fun throttleAction(throttleDelay: Long = DELAY_MS, action: () -> Unit): Boolean {
        val currentTime = SystemClock.elapsedRealtime()
        val diff = currentTime - lastActionTime

        return if (diff >= throttleDelay) {
            lastActionTime = currentTime
            action.invoke()
            true
        } else {
            false
        }
    }

}
