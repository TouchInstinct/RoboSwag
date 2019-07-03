package ru.touchin.converter.verifiers

class LengthVerifier(val maxLength: Int) : Verifier {

    override fun verify(text: String): Boolean = text.length <= maxLength

}
