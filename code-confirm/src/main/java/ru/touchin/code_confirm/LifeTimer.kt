package ru.touchin.code_confirm

import android.os.CountDownTimer

/** [LifeTimer] is extends [CountDownTimer] for countdown in seconds and lifetime text formatting
 * @param seconds Lifetime of timer in seconds
 * @param tickAction Action will be called on regular interval
 * @param finishAction Action will be called on finish */
class LifeTimer(
        seconds: Int,
        private val tickAction: (Long) -> Unit,
        private val finishAction: () -> Unit
) : CountDownTimer(seconds.toLong() * 1000, 1000) {

    override fun onTick(millisUntilFinished: Long) {
        tickAction.invoke(millisUntilFinished / 1000)
    }

    override fun onFinish() {
        finishAction.invoke()
    }

    companion object {
        fun getFormattedCodeLifetimeString(secondsUntilFinished: Long): String {
            val seconds = (secondsUntilFinished % 60).let { if (it < 10) "0$it" else "$it" }
            val minutes = (secondsUntilFinished / 60).let { if (it < 10) "0$it" else "$it" }
            return "$minutes:$seconds"
        }
    }

}
