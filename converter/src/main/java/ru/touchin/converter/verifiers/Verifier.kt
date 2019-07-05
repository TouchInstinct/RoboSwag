package ru.touchin.converter.verifiers

/**
 * Basic verifier class used as step of input verification
 */
interface Verifier {

    fun verify(text: String): Boolean

}
