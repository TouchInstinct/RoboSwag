package ru.touchin.converter

import ru.touchin.converter.wrap.InputConvertable
import java.math.BigDecimal

/**
 * Controller class to convert value from one input to other using modifier
 * @param viewBase base input
 * @param convertRate applied conversion rate between base and target inputs of [views]
 * @param viewTarget target input calculated using [convertRate]
 * @param onTextInputConvert callback applied at text change
 * @param viewColors colors to set at state change
 */
class ConverterController(
        viewBase: InputConvertable,
        viewTarget: InputConvertable,
        convertRate: BigDecimal? = null,
        baseVerifierController: VerifierController,
        targetVerifierController: VerifierController,
        onTextInputConvert: (baseValue: BigDecimal, targetValue: BigDecimal) -> Unit
) : AbstractConverterController(
        ConverterViews(
                amountBase = viewBase,
                amountTarget = viewTarget
        ),
        convertRate,
        baseVerifierController,
        targetVerifierController,
        onTextInputConvert
) {

    override val baseAmountChangedListener = BaseAmountChangedListener()
    override val targetAmountChangedListener = TargetAmountChangedListener()

    init {
        setCrossUpdateListenersToEditTexts()
    }

}
