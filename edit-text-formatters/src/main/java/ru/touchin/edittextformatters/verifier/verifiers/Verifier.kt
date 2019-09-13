package ru.touchin.edittextformatters.verifier.verifiers

import ru.touchin.edittextformatters.commands.Command
import java.math.BigDecimal

/**
 * Basic verifier class used as step of input verification
 */
interface Verifier<T : BigDecimal> {

    fun verify(text: String): Command<T>

}
