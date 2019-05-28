package ru.touchin.converter

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

private val format = DecimalFormat("#,##0.00######", DecimalFormatSymbols(Locale.US).also { it.groupingSeparator = ' ' })

fun BigDecimal.formatToStringWithoutGroupingSeparator(): String {
    val groupingSeparator = format.decimalFormatSymbols.groupingSeparator.toString()
    return format.format(this)
            .replace(groupingSeparator, "")
}

fun String.toBigDecimalOrZeroWithoutGrouping(): BigDecimal = this.toBigDecimalOrNullWithoutGrouping() ?: BigDecimal.ZERO

private fun String.toBigDecimalOrNullWithoutGrouping(): BigDecimal? = try {
    BigDecimal(this.replace("\\s".toRegex(), ""))
} catch (_: NumberFormatException) {
    null
}
