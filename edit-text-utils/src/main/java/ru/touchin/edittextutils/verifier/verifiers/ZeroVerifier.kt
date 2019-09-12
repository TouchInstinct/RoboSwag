package ru.touchin.edittextutils.verifier.verifiers

import ru.touchin.edittextutils.commands.Command
import java.math.BigDecimal

/**
 * Verifiers text input correctness for amount of zeros.
 * @param separatorChar separator character
 */
class ZeroVerifier(val separatorChar: Char) : Verifier<BigDecimal> { // todo fix regular expression to catch repeating zeros

    override fun verify(text: String): Command<BigDecimal> =
            if (text.length > 1 && text.startsWith('0') && !text.startsWith("0$separatorChar")) {
                Command.Set(Character.getNumericValue(text[1]).toBigDecimal())
            } else {
                Command.Success()
            }

}
