package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command
import java.math.BigDecimal

class IntegerVerifier(maxIntegerNumbers: Int) : Verifier<BigDecimal> {

    private val pattern = "(\\d{1,4})((\\.)\\d*)?".toRegex()

    override fun verify(text: String): Command<BigDecimal> = if (pattern.matches(text) == true || text.isEmpty()) {
        Command.Success()
    } else {
        Command.Fallback()
    }

}
