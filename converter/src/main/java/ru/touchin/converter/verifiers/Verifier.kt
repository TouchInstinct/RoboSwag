package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command
import java.math.BigDecimal

/**
 * Basic verifier class used as step of input verification
 */
interface Verifier<T : BigDecimal> {

    fun verify(text: String): Command<T>

}
