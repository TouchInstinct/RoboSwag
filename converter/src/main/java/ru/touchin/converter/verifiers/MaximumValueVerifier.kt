package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command
import java.math.BigDecimal

/**
 * Verifies value of text input.
 * @param maxValue maximum allowed value in text input.
 */
class MaximumValueVerifier(val maxValue: BigDecimal) : Verifier<BigDecimal> {

    override fun verify(text: String): Command<BigDecimal> = if (text.toBigDecimal() <= maxValue) {
        Command.Success()
    } else {
        Command.Set(maxValue)
    }

}
