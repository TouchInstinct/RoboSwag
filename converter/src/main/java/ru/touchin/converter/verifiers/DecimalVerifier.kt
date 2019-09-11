package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command
import java.math.BigDecimal

/**
 * Verifies decimal numbers in text input.
 * @param maxDecimalNumber maximum allowed numbers after separator.
 */
class DecimalVerifier(maxDecimalNumber: Int) : Verifier<BigDecimal> {

    private val pattern = "\\d+(\\.?)(\\d{0,$maxDecimalNumber})".toRegex()

    override fun verify(text: String): Command<BigDecimal> = if (pattern.matches(text) == true) {
        Command.Success()
    } else {
        Command.Fallback()
    }

}
