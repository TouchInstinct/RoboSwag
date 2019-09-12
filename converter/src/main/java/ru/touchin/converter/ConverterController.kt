package ru.touchin.converter

import android.text.Editable
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import ru.touchin.converter.verifiers.IntegerVerifier
import ru.touchin.converter.wrap.InputConvertable
import ru.touchin.defaults.DefaultTextWatcher
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
open class ConverterController(
        viewBase: InputConvertable,
        viewTarget: InputConvertable,
        private var convertRate: BigDecimal,
        private val onTextInputConvert: (baseValue: BigDecimal, targetValue: BigDecimal) -> Unit,
        private val baseVerifier: VerifierController = VerifierController(
                viewBase,
                listOf(IntegerVerifier()),
                true
        ),
        private val targetVerifier: VerifierController = VerifierController(
                viewTarget,
                listOf(IntegerVerifier()),
                true
        )
) {

    protected open val baseAmountChangedListener: TextWatcher = BaseAmountChangedListener()
    protected open val targetAmountChangedListener: TextWatcher = TargetAmountChangedListener()

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
        with(views.amountBase) {

            input.addTextChangedListener(baseAmountChangedListener)

            input.addOnFocusChangedListener { isFocused ->
                if (withSuffix) {
                    if (isFocused) removeSuffixFromText() else addSuffixToText()
                }
            }
        }

        with(views.amountTarget) {

            input.addTextChangedListener(targetAmountChangedListener)

            input.addOnFocusChangedListener { isFocused ->
                if (withSuffix) {
                    if (isFocused) removeSuffixFromText() else addSuffixToText()
                }
            }
        }
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

    protected inner class BaseAmountChangedListener : DefaultTextWatcher() {
        override fun afterTextChanged(editable: Editable) {
            val inputWrapper = views.amountBase
            val newBaseValue = inputWrapper.format(editable)
            val newTargetValue = inputWrapper.baseOperation(newBaseValue, convertRate, views.amountTarget.maxFractionNumber, roundingMode)

            //val isInputValid = baseVerifier.verifyAll(editable.toString())
            if (inputWrapper.input.isFocused() && newBaseValue != storedBaseValue) {
                baseListenerOperation(newBaseValue, newTargetValue)
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

    protected inner class TargetAmountChangedListener : DefaultTextWatcher() {
        override fun afterTextChanged(editable: Editable) {
            val inputWrapper = views.amountTarget
            val newTargetValue = inputWrapper.format(editable)
            val newBaseValue = inputWrapper.targetOperation(newTargetValue, convertRate, views.amountBase.maxFractionNumber, roundingMode)

            //val isInputValid = targetVerifier.verifyAll(editable.toString())
            if (inputWrapper.input.isFocused() && newTargetValue != storedTargetValue) {
                targetListenerOperation(newTargetValue, newBaseValue)
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
    data class ConverterViews(val amountBase: InputConvertable, val amountTarget: InputConvertable) {
        init {
            amountBase.input.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"))
            amountTarget.input.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"))
        }
    }

}
