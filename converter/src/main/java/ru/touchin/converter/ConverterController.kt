package ru.touchin.converter

import android.widget.EditText
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
        viewBase: EditText,
        viewTarget: EditText,
        convertRate: BigDecimal?,
        onTextInputConvert: (baseValue: BigDecimal, targetValue: BigDecimal) -> Unit,
        viewColors: ViewColors? = null
) : AbstractConverterController(
        convertRate,
        viewColors,
        onTextInputConvert
) {

    override val baseAmountChangedListener = BaseAmountChangedListener()
    override val targetAmountChangedListener = TargetAmountChangedListener()

    override val views: ConverterViews = ConverterViews(
            amountBase = viewBase,
            amountTarget = viewTarget
    )

    init {
        setCrossUpdateListenersToEditTexts()
    }

}
