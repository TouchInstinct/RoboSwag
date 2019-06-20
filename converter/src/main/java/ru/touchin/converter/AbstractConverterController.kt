package ru.touchin.converter

import android.text.Editable
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import androidx.annotation.ColorInt
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
        private val viewColors: ViewColors?,
        private val onTextInputConvert: (baseValue: BigDecimal, targetValue: BigDecimal) -> Unit
) {

    companion object {
        private const val PLACEHOLDER_VALUE = ""
    }

    protected abstract val baseAmountChangedListener: TextWatcher
    protected abstract val targetAmountChangedListener: TextWatcher

    protected var state: State = State.LOADING

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
     * maximum scale for calculation operations
     */
    var scaleValue: Int = 8
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

    fun setStateLoading() {
        setInputState(State.LOADING)
    }

    fun handleError(exception: Throwable) {
        setInputState(State.ERROR, exception.message)
    }

    fun addToBaseValue(value: BigDecimal) {
        baseValue = baseValue.plus(value)
        views.amountBase.setNumber(baseValue)
    }

    fun addToTargetValue(value: BigDecimal) {
        targetValue = targetValue.add(value)
        views.amountTarget.setNumber(targetValue)
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

    protected open fun onStateChange(errorMessage: String? = null) {
        setupAmounts()
    }

    /**
     * @param rate factor for [baseValue] and [targetValue] calculations
     */
    fun setRate(rate: BigDecimal) {
        convertRate = rate
        setStateReadyIfCompletelyInitialized()
    }

    private fun setInputState(inputState: State, errorMessage: String? = null) {
        state = inputState
        onStateChange(errorMessage)
    }

    protected fun setCrossUpdateListenersToEditTexts() {
        views.amountBase.input.addTextChangedListener(baseAmountChangedListener)
        views.amountTarget.input.addTextChangedListener(targetAmountChangedListener)
    }

    protected inner class BaseAmountChangedListener : DefaultTextWatcher() {
        override fun afterTextChanged(editable: Editable) {
            convertRate?.let { convertRate ->
                val newBaseValue = views.amountBase.format(editable)
                val newTargetValue = views.amountBase.baseOperation(newBaseValue, convertRate, scaleValue, roundingMode)
                if (state == State.READY && !views.amountTarget.input.isFocused() && newBaseValue != baseValue) {
                    targetValue = newTargetValue
                    baseValue = newBaseValue
                    if (autoTextSet == true) views.amountTarget.setNumber(targetValue)
                    onTextInputConvert(baseValue, targetValue)
                }
            }
        }
    }

    protected inner class TargetAmountChangedListener : DefaultTextWatcher() {
        override fun afterTextChanged(editable: Editable) {
            convertRate?.let { convertRate ->
                val newTargetValue = views.amountTarget.format(editable)
                val newBaseValue = views.amountTarget.targetOperation(newTargetValue, convertRate, scaleValue, roundingMode)
                if (state == State.READY && views.amountTarget.input.isFocused() && newTargetValue != targetValue) {
                    targetValue = newTargetValue
                    baseValue = newBaseValue
                    if (autoTextSet == true) views.amountBase.setNumber(baseValue)
                    onTextInputConvert(baseValue, targetValue)
                }
            }
        }
    }

    protected fun setStateReadyIfCompletelyInitialized() {
        if (convertRate != null) {
            setInputState(State.READY)
            views.amountTarget.input.addOnFocusChangedListener {
                with(views.amountTarget) {
                    if (withSuffix == true && it == true) removeSuffixFromText() else addSuffixToText()
                }
            }
            views.amountBase.input.addOnFocusChangedListener {
                with(views.amountBase) {
                    if (withSuffix == true && it == true) removeSuffixFromText() else addSuffixToText()
                }
            }
        }
    }

    /**
     * Update input according to [state]
     * Invokes at each state change @see [onStateChange]
     */
    @Suppress("ComplexMethod")
    private fun setupAmounts() {
        views.amountBase.input.apply {
            setText(if (getText().isBlank()) PLACEHOLDER_VALUE else getText()) // It's needed to trigger text update listener on each state change
            setEnabled(state == State.READY)
            if (viewColors != null) setTextColor(if (state == State.READY) viewColors.active else viewColors.inactive)
        }
        views.amountTarget.input.apply {
            if (state != State.READY && getText().isBlank()) setText(PLACEHOLDER_VALUE)
            setEnabled(state == State.READY)
            if (viewColors != null) setTextColor(if (state == State.READY) viewColors.active else viewColors.inactive)
        }
    }

    data class ConverterViews(val amountBase: InputConvertable, val amountTarget: InputConvertable) {
        init {
            amountBase.input.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"))
            amountTarget.input.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"))
        }
    }

    data class ViewColors(@ColorInt val active: Int, @ColorInt val inactive: Int)

    protected enum class State { LOADING, READY, ERROR }

}
