package ru.touchin.converter.wrap

import ru.touchin.converter.toBigDecimalOrZeroWithoutGrouping
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class InputConvertable(val input: Convertable) {

    var storedValue: BigDecimal = BigDecimal.ZERO
    var formatPattern: String = "#,##0.00######"
    var groupingSeparator = ' '

    private var suffix: String = ""
    private var format = buildFormat()

    fun setSuffix(suffix: String) {
        this.suffix = suffix
    }

    fun addSuffixToText() {
        input.setText("${input.getText()}$suffix")
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

    fun setNumber(number: BigDecimal) {
        input.setText(format(number))
    }

    fun getNumber(): BigDecimal = format(input.getText())

    fun format(valueToFormat: BigDecimal): String = valueToFormat.formatToStringWithoutGroupingSeparator()

    fun format(charSequenceToFormat: CharSequence): BigDecimal = charSequenceToFormat.toString()
            .toBigDecimalOrZeroWithoutGrouping()

    fun BigDecimal.formatToStringWithoutGroupingSeparator(): String {
        val groupingSeparator = format.decimalFormatSymbols.groupingSeparator.toString()
        return format.format(this)
                .replace(groupingSeparator, "")
    }

    /**
     * Re-initialize [format]
     */
    fun rebuildFormat() {
        format = buildFormat()
    }

    private fun buildFormat(): DecimalFormat = DecimalFormat(
            formatPattern,
            DecimalFormatSymbols(Locale.US
            ).also { it.groupingSeparator = groupingSeparator })

}
