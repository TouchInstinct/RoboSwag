package ru.touchin.edittextutils.verifier.verifiers

import ru.touchin.edittextutils.commands.Command
import java.math.BigDecimal

/**
 * Verifies value of text input.
 * @param minValue minimum allowed value in text input.
 */
class MinimumValueVerifier(val minValue: BigDecimal) : Verifier<BigDecimal> {

    override fun verify(text: String): Command<BigDecimal> = if (text.toBigDecimal() >= minValue) {
        Command.Success()
    } else {
        Command.Fallback()
    }

}
