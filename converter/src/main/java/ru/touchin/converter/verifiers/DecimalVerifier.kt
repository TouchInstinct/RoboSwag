package ru.touchin.converter.verifiers

class DecimalVerifier(maxDecimalNumber: Int) : Verifier {

    private val pattern = "(\\d{$maxDecimalNumber})\\.".toRegex()

    override fun verify(text: String): Boolean = pattern.matches(text)

}
