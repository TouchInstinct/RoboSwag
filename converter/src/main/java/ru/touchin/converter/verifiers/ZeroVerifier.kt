package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command
import java.math.BigDecimal

class ZeroVerifier(private val pointChar: Char = '.') : Verifier<BigDecimal> { // todo fix regular expression to catch repeating zeros

    override fun verify(text: String): Command<BigDecimal> =
            if (text.length > 1 && text.startsWith('0') && !text.startsWith("0$pointChar")) {
                Command.Set(Character.getNumericValue(text[1]).toBigDecimal())
            } else {
                Command.Success()
            }

}
