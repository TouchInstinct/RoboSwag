package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command
import java.math.BigDecimal

class CorrectVerifier : Verifier<BigDecimal> { // todo fix regular expression to catch repeating zeros

    private val pattern = "([^0]+)\\.?(\\d)".toRegex()

    override fun verify(text: String): Command<BigDecimal> = if (pattern.matches(text) == true) {
        Command.Success()
    } else {
        val result = pattern.matchEntire(text)?.groupValues
        val string = result?.get(1) + " " + result?.get(2)
        Command.Set(BigDecimal(string))
    }

}
