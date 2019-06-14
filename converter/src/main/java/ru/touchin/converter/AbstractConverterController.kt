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

    protected var baseValue: BigDecimal = BigDecimal.ZERO
    protected var targetValue: BigDecimal = BigDecimal.ZERO
    protected var state: State = State.LOADING
    /**
     * maximum scale for calculation operations
     */
    var scaleValue = 8
    /**
     * Set text to the corresponding input after each invocation of conversion in [baseAmountChangedListener] and [targetAmountChangedListener]
     */
    var autoTextSet = true
    var roundingMode = RoundingMode.HALF_UP

    init {
        setStateReadyIfCompletelyInitialized()
    }

    fun getBaseAmount(): BigDecimal = views.amountBase.getNumber()

    fun getTargetAmount(): BigDecimal = views.amountTarget.getNumber()

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
            if (convertRate != null) {
                val newBaseValue = format(editable) // todo move to inputcovertable
                val newTargetValue = newBaseValue
                        .multiply(convertRate)
                        .setScale(scaleValue, roundingMode)
                        .stripTrailingZeros()
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
            if (convertRate != null) {
                val newTargetValue = format(editable) // todo move to inputcovertable
                val newBaseValue = newTargetValue
                        .divide(convertRate, scaleValue, roundingMode)
                        .stripTrailingZeros()
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
