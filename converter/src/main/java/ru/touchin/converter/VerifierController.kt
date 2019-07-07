package ru.touchin.converter

import ru.touchin.converter.verifiers.Verifier
import ru.touchin.converter.wrap.InputConvertable

class VerifierController(
        private val inputConvertable: InputConvertable,
        private val verifiers: List<Verifier>
) {

    fun verifyAll(inputString: String): Boolean {
        return verifiers.all { it.verify(inputString) }
    }

}
