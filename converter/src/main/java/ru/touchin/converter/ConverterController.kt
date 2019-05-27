package ru.touchin.converter

import android.view.View
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

    fun showRateOrNotFoundHint(rate: BigDecimal?) {
        if (rate != null && rate != BigDecimal.ZERO) {
            setRate(rate)
        }
        showRateNotFound(rate == null || rate == BigDecimal.ZERO)
    }

    private fun showRateNotFound(show: Boolean) {
        views.amountBase.visibility = if (show == true) View.GONE else View.VISIBLE
        views.amountTarget.visibility = if (show == true) View.GONE else View.VISIBLE
    }

}
