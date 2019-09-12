package ru.touchin.edittextutils.converter

import java.math.BigDecimal

fun String.toBigDecimalOrZeroWithoutGrouping(): BigDecimal = this.toBigDecimalOrNullWithoutGrouping() ?: BigDecimal.ZERO

private fun String.toBigDecimalOrNullWithoutGrouping(): BigDecimal? = try {
    BigDecimal(this.replace("\\s".toRegex(), ""))
} catch (_: NumberFormatException) {
    null
}
