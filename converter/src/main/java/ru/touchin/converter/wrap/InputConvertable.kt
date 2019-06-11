package ru.touchin.converter.wrap

import ru.touchin.converter.formatToStringWithoutGroupingSeparator
import ru.touchin.converter.toBigDecimalOrZeroWithoutGrouping
import java.math.BigDecimal

class InputConvertable(val input: Convertable) {

    private var suffix: String = ""

    fun setSuffix(suffix: String) {
        this.suffix = suffix
    }

    fun addSuffixToText() {
        input.setText("${input.getText()}$suffix")
    }

    fun setNumber(number: BigDecimal) {
        input.setText(format(number))
    }

    fun getNumber(): BigDecimal = format(input.getText())

    fun format(valueToFormat: BigDecimal): String = valueToFormat.formatToStringWithoutGroupingSeparator()

    fun format(charSequenceToFormat: CharSequence): BigDecimal = charSequenceToFormat.toString()
            .toBigDecimalOrZeroWithoutGrouping()


}