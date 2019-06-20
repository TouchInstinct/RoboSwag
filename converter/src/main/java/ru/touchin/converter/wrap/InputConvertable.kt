package ru.touchin.converter.wrap

import ru.touchin.converter.formatToStringWithoutGroupingSeparator
import ru.touchin.converter.toBigDecimalOrZeroWithoutGrouping
import java.math.BigDecimal
import java.math.RoundingMode

class InputConvertable(val input: Convertable) {

    var storedValue: BigDecimal = BigDecimal.ZERO

    private var suffix: String = ""

    fun setSuffix(suffix: String) {
        this.suffix = suffix
    }

    fun addSuffixToText() {
        input.setText("${input.getText()}$suffix")
    }

    fun baseOperation(
            newBaseValue: BigDecimal,
            convertRate: BigDecimal,
            scaleValue: Int,
            roundingMode: RoundingMode
    ): BigDecimal = newBaseValue
            .multiply(convertRate)
            .setScale(scaleValue, roundingMode)
            .stripTrailingZeros()

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


}