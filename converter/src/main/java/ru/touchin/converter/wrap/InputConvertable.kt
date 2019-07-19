package ru.touchin.converter.wrap

import ru.touchin.converter.toBigDecimalOrZeroWithoutGrouping
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

open class InputConvertable(val input: Convertable) {

    var storedValue: BigDecimal = BigDecimal.ZERO
    var formatPattern: String = "#,##0.00######"
    var groupingSeparator = ' '
    var roundingMode: RoundingMode = RoundingMode.DOWN
    var maxFractionNumber = 2
    var maxIntegerNumber = 8

    var withSuffix: Boolean = true
    var suffix: String = ""
    private var format = buildFormat()

    fun addSuffixToText() {
        input.setText("${format(storedValue)}$suffix")
    }

    fun removeSuffixFromText() {
        input.setText(format(storedValue))
    }

    /**
     * Apply base function to base value
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
     * Apply target function to target value
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
            placeCursorToTheEnd: Boolean = false,
            incrementCursorPosition: Boolean = false,
            addSuffix: Boolean = false
    ) {
        val text = if (withSuffix == true || addSuffix == true) "${format(number)}$suffix" else format(number)
        input.setText(text, placeCursorToTheEnd, incrementCursorPosition)
    }

    fun format(valueToFormat: BigDecimal): String = valueToFormat.formatToStringWithoutGroupingSeparator()

    fun format(charSequenceToFormat: CharSequence): BigDecimal = charSequenceToFormat.toString().removeSuffix("$suffix")
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

    private fun buildFormat(separator: Char = groupingSeparator): DecimalFormat = DecimalFormat(
            formatPattern,
            DecimalFormatSymbols(Locale.US).also { it.groupingSeparator = separator }
    ).also {
        it.roundingMode = roundingMode
        it.maximumFractionDigits = maxFractionNumber
        it.maximumIntegerDigits = maxIntegerNumber
        it.isParseBigDecimal
    }

}
