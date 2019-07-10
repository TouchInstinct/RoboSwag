package ru.touchin.converter

import android.text.Editable
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import ru.touchin.converter.wrap.InputConvertable
import ru.touchin.defaults.DefaultTextWatcher
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Abstract controller class to convert value from one input to other using modifier
 * @param convertRate applied conversion rate between base and target inputs of [views]
 * @param onTextInputConvert callback applied at text change
 * @param viewColors colors to set at state change
 */
abstract class AbstractConverterController(
        val views: ConverterViews,
        protected var convertRate: BigDecimal?,
        private val baseVerifier: VerifierController,
        private val targetVerifier: VerifierController,
        private val onTextInputConvert: (baseValue: BigDecimal, targetValue: BigDecimal) -> Unit
) {

    companion object {
        private const val OPERATION_SCALE = 8
    }

    protected abstract val baseAmountChangedListener: TextWatcher
    protected abstract val targetAmountChangedListener: TextWatcher

    var baseValue: BigDecimal
        get() = views.amountBase.storedValue
        set(value) {
            views.amountBase.storedValue = value
        }

    var targetValue: BigDecimal
        get() = views.amountTarget.storedValue
        set(value) {
            views.amountTarget.storedValue = value
        }

    /**
     * maximum number length
     */
    var maxLength: Int = 12
    /**
     * Set text to the corresponding input after each invocation of conversion in [baseAmountChangedListener] and [targetAmountChangedListener]
     */
    var autoTextSet = true
    var roundingMode: RoundingMode = RoundingMode.HALF_UP

    init {
        setStateReadyIfCompletelyInitialized()
    }

    fun getBaseAmount(): BigDecimal = views.amountBase.storedValue

    fun getTargetAmount(): BigDecimal = views.amountTarget.storedValue

    fun addToBaseValue(value: BigDecimal) {
        baseValue = baseValue.plus(value)
        views.amountBase.setNumber(baseValue)
        val newTargetValue = views.amountBase.baseOperation(baseValue, convertRate!!, OPERATION_SCALE, roundingMode)
        baseListenerOperation(baseValue, newTargetValue)
    }

    fun addToTargetValue(value: BigDecimal) {
        targetValue = targetValue.plus(value)
        views.amountTarget.setNumber(targetValue)
        val newBaseValue = views.amountTarget.targetOperation(targetValue, convertRate!!, OPERATION_SCALE, roundingMode)
        targetListenerOperation(targetValue, newBaseValue)
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
     * @param rate factor for [baseValue] and [targetValue] calculations
     */
    fun setRate(rate: BigDecimal) {
        convertRate = rate
        setStateReadyIfCompletelyInitialized()
    }

    protected fun setCrossUpdateListenersToEditTexts() {
        views.amountBase.input.addTextChangedListener(baseAmountChangedListener)
        views.amountTarget.input.addTextChangedListener(targetAmountChangedListener)
    }

    protected inner class BaseAmountChangedListener : DefaultTextWatcher() {
        override fun afterTextChanged(editable: Editable) {
            convertRate?.let { convertRate ->
                val inputWrapper = views.amountBase
                val newBaseValue = inputWrapper.format(editable)
                val newTargetValue = inputWrapper.baseOperation(newBaseValue, convertRate, OPERATION_SCALE, roundingMode)
                if (inputWrapper.input.isFocused() && newBaseValue != baseValue) {
                    val isValid = baseVerifier.verifyAll(editable.toString())
                    if (isValid == true) {
                        baseListenerOperation(newBaseValue, newTargetValue)
                    }
                }
            }
        }
    }

    private fun baseListenerOperation(newBaseValue: BigDecimal, newTargetValue: BigDecimal) {
        targetValue = newTargetValue
        baseValue = newBaseValue
        if (autoTextSet == true) views.amountTarget.setNumber(targetValue)
        onTextInputConvert(baseValue, targetValue)
    }

    protected inner class TargetAmountChangedListener : DefaultTextWatcher() {
        override fun afterTextChanged(editable: Editable) {
            convertRate?.let { convertRate ->
                val inputWrapper = views.amountTarget
                val newTargetValue = inputWrapper.format(editable)
                val newBaseValue = inputWrapper.targetOperation(newTargetValue, convertRate, OPERATION_SCALE, roundingMode)
                if (inputWrapper.input.isFocused() && newTargetValue != targetValue) {
                    val isValid = targetVerifier.verifyAll(editable.toString())
                    if (isValid == true) {
                        targetListenerOperation(newTargetValue, newBaseValue)
                    }
                }
            }
        }
    }

    private fun targetListenerOperation(newTargetValue: BigDecimal, newBaseValue: BigDecimal) {
        targetValue = newTargetValue
        baseValue = newBaseValue
        if (autoTextSet == true) views.amountBase.setNumber(baseValue)
        onTextInputConvert(baseValue, targetValue)
    }

    protected fun setStateReadyIfCompletelyInitialized() {
        if (convertRate != null) {
            views.amountTarget.input.addOnFocusChangedListener { isFocused ->
                with(views.amountTarget) {
                    if (withSuffix == true) {
                        if (isFocused == true) removeSuffixFromText() else addSuffixToText()
                    }
                }
            }
            views.amountBase.input.addOnFocusChangedListener { isFocused ->
                with(views.amountBase) {
                    if (withSuffix == true) {
                        if (isFocused == true) removeSuffixFromText() else addSuffixToText()
                    }
                }
            }
        }
    }

    data class ConverterViews(val amountBase: InputConvertable, val amountTarget: InputConvertable) {
        init {
            amountBase.input.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"))
            amountTarget.input.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"))
        }
    }

}
