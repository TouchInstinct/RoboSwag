package ru.touchin.edittextutils.converter.wrap

import ru.touchin.edittextutils.converter.toBigDecimalOrZeroWithoutGrouping
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

open class InputConvertible(val input: Convertible) {

    var storedValue: BigDecimal = format(input.getText())
    var formatPattern: String = "#,##0.########"
    var groupingSeparator = ' '
    var roundingMode: RoundingMode = RoundingMode.DOWN
    var maxFractionNumber = 2
    var maxIntegerNumber = 8

    private var format = buildFormat()

    /**
     * Multiply given [newBaseValue] with [convertRate]
     * @param scaleValue output value scale
     * @param roundingMode rounding mode for output value
     */
    fun baseOperation(
            newBaseValue: BigDecimal,
            convertRate: BigDecimal,
            scaleValue: Int,
            roundingMode: RoundingMode
    ): BigDecimal = newBaseValue
            .multiply(convertRate)
            .setScale(scaleValue, roundingMode)
            .stripTrailingZeros()

    /**
     * Divide given [newTargetValue] with [convertRate]
     * @param scaleValue output value scale
     * @param roundingMode rounding mode for output value
     */
    fun targetOperation(
            newTargetValue: BigDecimal,
            convertRate: BigDecimal,
            scaleValue: Int,
            roundingMode: RoundingMode
    ): BigDecimal = newTargetValue
            .divide(convertRate, scaleValue, roundingMode)
            .stripTrailingZeros()

    fun setNumber(
            number: BigDecimal,
            placeCursorToTheEnd: Boolean = true,
            incrementCursorPosition: Boolean = false
    ) {
        val text = format(number)
        input.setText(text, placeCursorToTheEnd, incrementCursorPosition)
    }

    fun format(valueToFormat: BigDecimal): String = valueToFormat.formatToStringWithoutGroupingSeparator()

    fun format(charSequenceToFormat: CharSequence): BigDecimal = charSequenceToFormat.toString()
            .toBigDecimalOrZeroWithoutGrouping()
            .stripTrailingZeros()

    fun BigDecimal.formatToStringWithoutGroupingSeparator(): String {
        val groupingSeparator = format.decimalFormatSymbols.groupingSeparator.toString()
        return format.format(this).replace(groupingSeparator, "")
    }

    /**
     * Re-initialize [format]
     */
    fun rebuildFormat() {
        format = buildFormat()
    }

    open fun validate(): Boolean {
        return true // todo implement or delete
    }

    /**
     * Build decimalFormat by using all class parameters
     * @param separator separatior character
     */
    private fun buildFormat(separator: Char = groupingSeparator): DecimalFormat = DecimalFormat(
            formatPattern,
            DecimalFormatSymbols(Locale.US).also { it.groupingSeparator = separator }
    ).also {
        it.roundingMode = roundingMode
        it.maximumFractionDigits = maxFractionNumber
        it.maximumIntegerDigits = maxIntegerNumber
    }

}
