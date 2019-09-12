package ru.touchin.edittextutils.converter

import android.text.Editable
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import ru.touchin.edittextutils.verifier.verifiers.IntegerVerifier
import ru.touchin.edittextutils.converter.wrap.InputConvertible
import ru.touchin.defaults.DefaultTextWatcher
import ru.touchin.edittextutils.verifier.VerifierController
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Controller class to convert value from one input to other using modifier
 * @param viewBase base input
 * @param viewTarget target input calculated using [convertRate]
 * @param convertRate applied conversion rate between base and target inputs of [views]
 * @param onTextInputConvert callback applied at text change
 * @param baseVerifier input verifier of base input
 * @param targetVerifier input verifier of target input
 */
class ConverterController(
        viewBase: InputConvertible,
        viewTarget: InputConvertible,
        private var convertRate: BigDecimal,
        private val onTextInputConvert: (baseValue: BigDecimal, targetValue: BigDecimal) -> Unit,
        private val baseVerifier: VerifierController = VerifierController(
            viewBase,
            listOf(IntegerVerifier())
        ),
        private val targetVerifier: VerifierController = VerifierController(
            viewTarget,
            listOf(IntegerVerifier())
        )
) {

    private val baseAmountChangedListener: TextWatcher = BaseAmountChangedListener()
    private val targetAmountChangedListener: TextWatcher = TargetAmountChangedListener()

    val views: ConverterViews = ConverterViews(viewBase, viewTarget)

    var storedBaseValue: BigDecimal
        get() = views.amountBase.storedValue
        set(value) {
            views.amountBase.storedValue = value
        }

    var storedTargetValue: BigDecimal
        get() = views.amountTarget.storedValue
        set(value) {
            views.amountTarget.storedValue = value
        }

    /**
     * Set text to the corresponding input after each invocation of conversion in [baseAmountChangedListener] and [targetAmountChangedListener]
     */
    var autoTextSet = true
    var roundingMode: RoundingMode = RoundingMode.HALF_DOWN

    init {
        views.amountBase.input.addTextChangedListener(baseAmountChangedListener)

        views.amountTarget.input.addTextChangedListener(targetAmountChangedListener)
    }

    //TODO: clarify is it necessary
    fun addToBaseValue(value: BigDecimal) {
        storedBaseValue = storedBaseValue.plus(value)
        views.amountBase.setNumber(storedBaseValue)
        val newTargetValue = views.amountBase.baseOperation(storedBaseValue, convertRate, views.amountBase.maxFractionNumber, roundingMode)
        baseListenerOperation(storedBaseValue, newTargetValue)
    }

    //TODO: clarify is it necessary
    fun addToTargetValue(value: BigDecimal) {
        storedTargetValue = storedTargetValue.plus(value)
        views.amountTarget.setNumber(storedTargetValue)
        val newBaseValue = views.amountTarget.targetOperation(storedTargetValue, convertRate, views.amountTarget.maxFractionNumber, roundingMode)
        targetListenerOperation(storedTargetValue, newBaseValue)
    }

    fun setFormatPatter(formatPatter: String) {
        views.amountBase.formatPattern = formatPatter
        views.amountBase.rebuildFormat()
        views.amountTarget.formatPattern = formatPatter
        views.amountTarget.rebuildFormat()
    }

    fun setGroupingSeparator(groupingSeparator: Char) {
        views.amountBase.groupingSeparator = groupingSeparator
        views.amountBase.rebuildFormat()
        views.amountTarget.groupingSeparator = groupingSeparator
        views.amountTarget.rebuildFormat()
    }

    /**
     * @param rate factor for [storedBaseValue] and [storedTargetValue] calculations
     */
    fun setRate(rate: BigDecimal) {
        convertRate = rate
    }

    private inner class BaseAmountChangedListener : DefaultTextWatcher() {
        override fun afterTextChanged(editable: Editable) {
            val inputWrapper = views.amountBase
            val newBaseValue = inputWrapper.format(editable)
            val newTargetValue = inputWrapper.baseOperation(newBaseValue, convertRate, views.amountTarget.maxFractionNumber, roundingMode)

            if (newBaseValue != storedBaseValue) {
                if (baseVerifier.verifyAll(newBaseValue.toString())
                    && targetVerifier.verifyAll(newTargetValue.toString())) {
                    baseListenerOperation(newBaseValue, newTargetValue)
                } else {
                    inputWrapper.setNumber(storedBaseValue)
                }
            }
        }
    }

    /**
     * Operation for base input at every text change.
     * Save [newBaseValue] and [newTargetValue]
     * Set new converted number to base input if [autoTextSet] is true
     * Invoke controller callback [onTextInputConvert]
     */
    private fun baseListenerOperation(newBaseValue: BigDecimal, newTargetValue: BigDecimal) {
        storedTargetValue = newTargetValue
        storedBaseValue = newBaseValue
        if (autoTextSet) views.amountTarget.setNumber(storedTargetValue)
        onTextInputConvert(storedBaseValue, storedTargetValue)
    }

    private inner class TargetAmountChangedListener : DefaultTextWatcher() {
        override fun afterTextChanged(editable: Editable) {
            val inputWrapper = views.amountTarget
            val newTargetValue = inputWrapper.format(editable)
            val newBaseValue = inputWrapper.targetOperation(newTargetValue, convertRate, views.amountBase.maxFractionNumber, roundingMode)

            if (newTargetValue != storedTargetValue) {
                if (targetVerifier.verifyAll(newTargetValue.toString())
                    && baseVerifier.verifyAll(newBaseValue.toString())) {
                    targetListenerOperation(newTargetValue, newBaseValue)
                } else {
                    inputWrapper.setNumber(storedTargetValue)
                }
            }
        }
    }

    /**
     * Operation for target input at every text change
     * Save [newBaseValue] and [newTargetValue]
     * Set new converted number to target input if [autoTextSet] is true
     * Invoke controller callback [onTextInputConvert]
     */
    private fun targetListenerOperation(newTargetValue: BigDecimal, newBaseValue: BigDecimal) {
        storedTargetValue = newTargetValue
        storedBaseValue = newBaseValue
        if (autoTextSet) views.amountBase.setNumber(storedBaseValue)
        onTextInputConvert(storedBaseValue, storedTargetValue)
    }

    /**
     * Wrapper for views.
     * At the moment is redundant because it holds only convertable views
     */
    data class ConverterViews(val amountBase: InputConvertible, val amountTarget: InputConvertible) {
        init {
            amountBase.input.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"))
            amountTarget.input.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"))
        }
    }

}
