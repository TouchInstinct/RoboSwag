package ru.touchin.code_confirm

import android.os.CountDownTimer
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

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

        private val formatter = SimpleDateFormat("mm:ss", Locale.ROOT)

        fun getFormattedCodeLifetimeString(secondsUntilFinished: Long): String =
                formatter.format(Date(secondsUntilFinished * 1000L))

    }

}
