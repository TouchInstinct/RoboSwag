package ru.touchin.converter.verifiers

import android.util.Log
import ru.touchin.converter.commands.Command
import java.math.BigDecimal

class PointVerifier : Verifier<BigDecimal> { // todo fix regular expression to catch repeating zeros

    private val pattern = "[^.,]*[.,]?[^.,]*".toRegex()

    override fun verify(text: String): Command<BigDecimal> {
        Log.d("verify", "text:${text}")
        return if (pattern.matches(text) == true) {
            Command.Success()
        } else {

            Command.Fallback()
        }
    }

}
