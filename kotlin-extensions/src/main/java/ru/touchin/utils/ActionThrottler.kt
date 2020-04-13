package ru.touchin.utils

import android.os.SystemClock
import ru.touchin.extensions.RIPPLE_EFFECT_DELAY

object ActionThrottler {

    private const val DELAY = 2 * RIPPLE_EFFECT_DELAY
    private var lastActionTime = 0L

    fun throttleAction(action: () -> Unit): Boolean {
        val currentTime = SystemClock.elapsedRealtime()
        val diff = currentTime - lastActionTime

        return if (diff >= DELAY) {
            lastActionTime = currentTime
            action.invoke()
            true
        } else {
            false
        }
    }

}


