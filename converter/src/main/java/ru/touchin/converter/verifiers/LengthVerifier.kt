package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command
import java.math.BigDecimal

class LengthVerifier(val maxLength: Int) : Verifier<BigDecimal> {

    override fun verify(text: String): Command<BigDecimal> = if (text.length <= maxLength) {
        Command.Success()
    } else {
        Command.Remove(1.toBigDecimal())
    }

}
