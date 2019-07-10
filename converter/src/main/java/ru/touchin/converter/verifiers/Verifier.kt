package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command

/**
 * Basic verifier class used as step of input verification
 */
interface Verifier<T : Number> {

    fun verify(text: String): Command<T>

}
