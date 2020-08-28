package ru.touchin.utils

import android.os.SystemClock
import ru.touchin.roboswag.extensions.RIPPLE_EFFECT_DELAY_MS

class ActionThrottler(private val throttleDelay: Long = DEFAULT_DELAY_MS) {

    companion object {

        private const val DEFAULT_DELAY_MS = 500L

    }

    private var lastActionTime = 0L

    fun throttleAction(action: () -> Unit): Boolean {
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

object RippleEffectThrottler {

    private const val PREVENTION_OF_CLICK_AGAIN_COEFFICIENT = 2
    private const val DELAY_MS = PREVENTION_OF_CLICK_AGAIN_COEFFICIENT * RIPPLE_EFFECT_DELAY_MS

    val throttler = ActionThrottler(DELAY_MS)

}
