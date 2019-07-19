package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command
import java.math.BigDecimal

class MaximumValueVerifier(val maxValue: BigDecimal) : Verifier<BigDecimal> {

    override fun verify(text: String): Command<BigDecimal> = if (text.isEmpty() == true || text.toBigDecimal() <= maxValue) {
        Command.Success()
    } else {
        Command.Set(maxValue)
    }

}
