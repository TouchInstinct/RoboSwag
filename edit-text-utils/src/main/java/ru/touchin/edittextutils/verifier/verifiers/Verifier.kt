package ru.touchin.edittextutils.verifier.verifiers

import ru.touchin.edittextutils.commands.Command
import java.math.BigDecimal

/**
 * Basic verifier class used as step of input verification
 */
interface Verifier<T : BigDecimal> {

    fun verify(text: String): Command<T>

}
