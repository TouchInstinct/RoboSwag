package ru.touchin.converter.verifiers

class IntegerVerifier(maxIntegerNumbers: Int) : Verifier {

    private val pattern = "\\.(\\d{$maxIntegerNumbers})".toRegex()

    override fun verify(text: String): Boolean = pattern.matches(text)

}
