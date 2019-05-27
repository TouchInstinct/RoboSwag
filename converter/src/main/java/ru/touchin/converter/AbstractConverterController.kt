package ru.touchin.converter

import android.text.Editable
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.widget.EditText
import androidx.annotation.ColorInt
import ru.touchin.defaults.DefaultTextWatcher
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Abstract controller class to convert value from one input to other using modifier
 * @param convertRate applied conversion rate between base and target inputs of [views]
 * @param onTextInputConvert callback applied at text change
 * @param viewColors colors to set at state change
 */
abstract class AbstractConverterController(
        protected var convertRate: BigDecimal?,
        private val viewColors: ViewColors?,
        private val onTextInputConvert: (baseValue: BigDecimal, targetValue: BigDecimal) -> Unit
) {

    companion object {
        private const val BASE_CURRENCY_DEFAULT_VALUE = ""
    }

    protected abstract val baseAmountChangedListener: TextWatcher
    protected abstract val targetAmountChangedListener: TextWatcher

    protected var baseValue: BigDecimal = BigDecimal.ZERO
    protected var targetValue: BigDecimal = BigDecimal.ZERO
    protected var state: State = State.LOADING
    private var valueScale = 8
    /**
     * Set text to the corresponding input after each invocation of conversion in [baseAmountChangedListener] and [targetAmountChangedListener]
     */
    private var autoTextSet = true
    private var roundingMode = RoundingMode.HALF_UP

    abstract val views: ConverterViews

    fun getBaseAmount() = views.amountBase.text.toString().toBigDecimalOrZeroWithoutGrouping()

    fun getTargetAmount() = views.amountTarget.text.toString().toBigDecimalOrZeroWithoutGrouping()

    fun setStateLoading() {
        setInputState(State.LOADING)
    }

    fun handleError(exception: Throwable) {
        setInputState(State.ERROR, exception.message)
    }

    /**
     * @param [valueScale] maximum scale for calculation operations
     */
    fun setValueScale(valueScale: Int) {
        this.valueScale = valueScale
    }

    /**
     * @param [autoTextSet] text setting mode
     */
    fun setAutoTextSetMode(autoTextSet: Boolean) {
        this.autoTextSet = autoTextSet
    }

    fun setRoundingMode(roundingMode: RoundingMode) {
        this.roundingMode = roundingMode
    }

    protected open fun onStateChange(errorMessage: String? = null) {
        setupAmounts()
    }

    /**
     * @param rate factor for [baseValue] and [targetValue] calculations
     */
    protected fun setRate(rate: BigDecimal) {
        convertRate = rate
        setStateReadyIfCompletelyInitialized()
    }

    private fun setInputState(inputState: State, errorMessage: String? = null) {
        state = inputState
        onStateChange(errorMessage)
    }

    protected fun setCrossUpdateListenersToEditTexts() {
        views.amountBase.addTextChangedListener(baseAmountChangedListener)
        views.amountTarget.addTextChangedListener(targetAmountChangedListener)
    }

    protected inner class BaseAmountChangedListener : DefaultTextWatcher() {
        override fun afterTextChanged(editable: Editable) {
            if (convertRate != null) {
                val newBaseValue = editable.toString()
                        .toBigDecimalOrZeroWithoutGrouping()
                val newTargetValue = newBaseValue
                        .multiply(convertRate)
                        .setScale(valueScale, roundingMode)
                        .stripTrailingZeros()
                if (state == State.READY && !views.amountTarget.isFocused && newBaseValue != baseValue) {
                    targetValue = newTargetValue
                    baseValue = newBaseValue
                    if (autoTextSet == true) views.amountTarget.setText(targetValue.formatToStringWithoutGroupingSeparator())
                    onTextInputConvert(baseValue, targetValue)
                }
            }
        }
    }

    protected inner class TargetAmountChangedListener : DefaultTextWatcher() {
        override fun afterTextChanged(editable: Editable) {
            if (convertRate != null) {
                val newTargetValue = editable.toString()
                        .toBigDecimalOrZeroWithoutGrouping()
                val newBaseValue = newTargetValue
                        .divide(convertRate, valueScale, roundingMode)
                        .stripTrailingZeros()
                if (state == State.READY && views.amountTarget.isFocused && newTargetValue != targetValue) {
                    targetValue = newTargetValue
                    baseValue = newBaseValue
                    if (autoTextSet == true) views.amountBase.setText(baseValue.formatToStringWithoutGroupingSeparator())
                    onTextInputConvert(baseValue, targetValue)
                }
            }
        }
    }

    private fun setStateReadyIfCompletelyInitialized() {
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
        views.amountBase.apply {
            setText(if (text.isBlank()) BASE_CURRENCY_DEFAULT_VALUE else text) // It's needed to trigger text update listener on each state change
            isEnabled = state == State.READY
            if (viewColors != null) setTextColor(if (state == State.READY) viewColors.active else viewColors.inactive)
        }
        views.amountTarget.apply {
            if (state != State.READY && text.isBlank()) setText(BASE_CURRENCY_DEFAULT_VALUE)
            isEnabled = state == State.READY
            if (viewColors != null) setTextColor(if (state == State.READY) viewColors.active else viewColors.inactive)
        }
    }

    private val format = DecimalFormat("#,##0.00######", DecimalFormatSymbols(Locale.US).also { it.groupingSeparator = ' ' })

    private fun BigDecimal.formatToStringWithoutGroupingSeparator(): String {
        val groupingSeparator = format.decimalFormatSymbols.groupingSeparator.toString()
        return format.format(this)
                .replace(groupingSeparator, "")
    }

    private fun String.toBigDecimalOrZeroWithoutGrouping(): BigDecimal = this.toBigDecimalOrNullWithoutGrouping() ?: BigDecimal.ZERO

    private fun String.toBigDecimalOrNullWithoutGrouping(): BigDecimal? = try {
        BigDecimal(this.replace("\\s".toRegex(), ""))
    } catch (_: NumberFormatException) {
        null
    }

    data class ConverterViews(val amountBase: EditText, val amountTarget: EditText) {
        init {
            amountBase.keyListener = DigitsKeyListener.getInstance("0123456789.,")
            amountTarget.keyListener = DigitsKeyListener.getInstance("0123456789.,")
        }
    }

    data class ViewColors(@ColorInt val active: Int, @ColorInt val inactive: Int)

    protected enum class State { LOADING, READY, ERROR }

}
