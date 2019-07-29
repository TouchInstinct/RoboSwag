package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command
import java.math.BigDecimal

class ZeroVerifier : Verifier<BigDecimal> { // todo fix regular expression to catch repeating zeros

    override fun verify(text: String): Command<BigDecimal> = if (text.startsWith('0') && text.length > 1) {
        Command.Fallback()
    } else {
        Command.Success()
    }

}
