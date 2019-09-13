package ru.touchin.edittextformatters.verifier.verifiers

import ru.touchin.edittextformatters.commands.Command
import java.math.BigDecimal

/**
 * Verifies decimal numbers in text input.
 * @param maxIntegerNumbers maximum allowed numbers before separator.
 */
class IntegerVerifier(maxIntegerNumbers: Int = 5) : Verifier<BigDecimal> {

    private val pattern = "^\\d{1,$maxIntegerNumbers}$".toRegex()

    override fun verify(text: String): Command<BigDecimal> = if (pattern.matches(text)) {
        Command.Success()
    } else {
        Command.Fallback()
    }

}
