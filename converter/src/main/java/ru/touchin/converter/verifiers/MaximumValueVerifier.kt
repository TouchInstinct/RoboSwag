package ru.touchin.converter.verifiers

import java.math.BigDecimal

class MaximumValueVerifier(val maxValue: BigDecimal) : Verifier {

    override fun verify(text: String): Boolean = text.toBigDecimal() <= maxValue

}
