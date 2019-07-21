package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command
import java.math.BigDecimal

class DecimalVerifier(maxDecimalNumber: Int) : Verifier<BigDecimal> {

    private val pattern = "\\d+(\\.?)(\\d{0,$maxDecimalNumber})".toRegex()

    override fun verify(text: String): Command<BigDecimal> = if (pattern.matches(text) == true) {
        Command.Success()
    } else {
        Command.Fallback()
    }

}
