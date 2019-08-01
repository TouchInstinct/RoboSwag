package ru.touchin.converter

import org.junit.Test
import ru.touchin.converter.verifiers.DecimalVerifier
import ru.touchin.converter.verifiers.IntegerVerifier
import ru.touchin.converter.verifiers.LengthVerifier
import ru.touchin.converter.verifiers.MaximumValueVerifier
import ru.touchin.converter.wrap.InputConvertable
import java.math.BigDecimal

class ConverterControllerTest {

    @Test
    fun verifier_fallback() {
        val baseInput = InputConvertable(MockConvertable()).also {
            it.suffix = " Л"
            it.maxFractionNumber = 5
        }
        val targetInput = InputConvertable(MockConvertable()).also {
            it.suffix = " Р"
            it.maxFractionNumber = 5
        }
        val verifiers = listOf(
                LengthVerifier(12),
                IntegerVerifier(4),
                MaximumValueVerifier(8000.toBigDecimal())
        )
        val baseVerifier = VerifierController(
                inputConvertable = baseInput,
                verifiers = verifiers + listOf(DecimalVerifier(5)),
                isCorrectionMode = true
        )
        val targetVerifier = VerifierController(
                inputConvertable = targetInput,
                verifiers = verifiers + listOf(DecimalVerifier(5)),
                isCorrectionMode = true
        )
        val converterController = ConverterController(
                baseInput,
                targetInput,
                convertRate = BigDecimal(2.15),
                baseVerifierController = baseVerifier,
                targetVerifierController = targetVerifier
        ) { volume, price ->
        }
    }

}
