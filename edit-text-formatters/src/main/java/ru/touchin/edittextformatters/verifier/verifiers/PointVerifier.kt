package ru.touchin.edittextformatters.verifier.verifiers

import android.util.Log
import ru.touchin.edittextformatters.commands.Command
import java.math.BigDecimal

/**
 * Verifies separator existence in text input.
 * @param separatorChar character to check.
 */
class PointVerifier(separatorChar: Char = '.') : Verifier<BigDecimal> { // todo fix regular expression to catch repeating zeros

    private val pattern = "[^$separatorChar]*[$separatorChar]?[^$separatorChar]*".toRegex()

    override fun verify(text: String): Command<BigDecimal> {
        Log.d("verify", "text:${text}")
        return if (pattern.matches(text) == true) {
            Command.Success()
        } else {

            Command.Fallback()
        }
    }

}
