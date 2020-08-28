package ru.touchin.roboswag.extensions.utils

import android.os.SystemClock
import ru.touchin.roboswag.extensions.RIPPLE_EFFECT_DELAY_MS
import java.util.*
import kotlin.concurrent.schedule

class ActionThrottler(
        private val throttleDelay: Long = DEFAULT_DELAY_MS,
        private val executeLastSkippedAction: Boolean = true
) {

    companion object {

        private const val DEFAULT_DELAY_MS = 500L

    }

    private var lastActionTime = 0L
    private var lastSkippedAction: (() -> Unit)? = null

    fun throttleAction(action: () -> Unit): Boolean {
        val currentTime = SystemClock.elapsedRealtime()
        val diff = currentTime - lastActionTime
        return if (diff >= throttleDelay) {
            lastActionTime = currentTime
            action.invoke()
            true
        } else {
            if (executeLastSkippedAction) {
                if (lastSkippedAction == null) {
                    Timer().schedule(throttleDelay - diff) {
                        lastSkippedAction?.invoke()
                        lastSkippedAction = null
                    }
                }
                lastSkippedAction = action
            }
            false
        }
    }

}

object RippleEffectThrottler {

    private const val PREVENTION_OF_CLICK_AGAIN_COEFFICIENT = 2
    private const val DELAY_MS = PREVENTION_OF_CLICK_AGAIN_COEFFICIENT * RIPPLE_EFFECT_DELAY_MS

    val throttler = ActionThrottler(throttleDelay = DELAY_MS, executeLastSkippedAction = false)

}
