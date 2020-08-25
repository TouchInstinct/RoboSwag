package ru.touchin.widget

import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

@Suppress("detekt.LabeledExpression")
class AmountWithDecimalDecorator(
        val editText: AppCompatEditText
) {

    companion object {

        private const val COMMON_MONEY_MASK = "###,##0"
        private const val DEFAULT_DECIMAL_SEPARATOR = ","
        private const val GROUPING_SEPARATOR = ' '
        private const val DEFAULT_DECIMAL_PART_LENGTH = 2
        private val hardcodedSymbols = listOf(GROUPING_SEPARATOR)
        private val possibleDecimalSeparators = listOf(",", ".")

    }

    var decimalSeparator = DEFAULT_DECIMAL_SEPARATOR
        set(value) {
            if (!possibleDecimalSeparators.contains(value)) {
                throw IllegalArgumentException("Not allowed decimal separator. Supports only: $possibleDecimalSeparators")
            }
            field = value
        }
    var decimalPartLength = DEFAULT_DECIMAL_PART_LENGTH
    var isSeparatorCutInvalidDecimalLength = false

    private var textBefore = ""
    private var isTextWasArtificiallyChanged = true

    init {
        @Suppress("detekt.TooGenericExceptionCaught")
        editText.doOnTextChanged { text, _, _, _ ->
            if (isTextWasArtificiallyChanged) {
                isTextWasArtificiallyChanged = false
                val cursorPos = editText.selectionStart
                try {
                    var text = text.toString()
                    possibleDecimalSeparators.forEach {
                        text = text.replace(it, decimalSeparator)
                    }

                    if (text == decimalSeparator || text.count { it == decimalSeparator[0] } > 1) {
                        if (abs(textBefore.length - text.length) > 1) {
                            setTextWhichWasPasted(text)
                        } else {
                            editText.setText(textBefore)
                            editText.setSelection(max(cursorPos - 1, 0))
                        }
                        return@doOnTextChanged
                    }

                    if (text.take(2) == "00") {
                        if (abs(textBefore.length - text.length) > 1) {
                            setTextWhichWasPasted(text)
                        } else {
                            editText.setText(textBefore)
                            editText.setSelection(max(cursorPos - 1, 0))
                        }
                        return@doOnTextChanged
                    }

                    if (text.length >= 2 && text[0] == '0' && text[1] != decimalSeparator[0]) {
                        if (abs(textBefore.length - text.length) > 1) {
                            setTextWhichWasPasted(text)
                        } else {
                            setTextWhichWasPasted(text)
                            editText.setSelection(max(cursorPos - 1, 0))
                        }
                        return@doOnTextChanged
                    }

                    val currentDecimalPartLength = text.split(decimalSeparator).getOrNull(1)?.length
                    if (!isSeparatorCutInvalidDecimalLength && currentDecimalPartLength != null
                            && currentDecimalPartLength > decimalPartLength) {
                        if (abs(textBefore.length - text.length) > 1) {
                            setTextWhichWasPasted(text)
                        } else {
                            editText.setText(textBefore)
                            editText.setSelection(max(cursorPos - 1, 0))
                        }
                        return@doOnTextChanged
                    }

                    val textAfter = if (text.isNotEmpty()) {
                        text.withoutFormatting().formatMoney(currentDecimalPartLength)
                    } else ""

                    if (!isTextErased(textBefore, textAfter)) {
                        val diff = textAfter.length - textBefore.length - 1
                        editText.setText(textAfter)
                        editText.setSelection(min(cursorPos + diff, textAfter.length))
                    } else {
                        if (!textBefore.contains(decimalSeparator)
                                && textAfter.contains(decimalSeparator)
                        ) {
                            editText.setText(textAfter)
                            editText.setSelection(min(textAfter.length, textAfter.indexOf(decimalSeparator) + 1))
                            return@doOnTextChanged
                        }
                        val diff = textBefore.length - textAfter.length
                        if (diff == 0) {
                            editText.setText(textAfter)
                            editText.setSelection(min(cursorPos, textAfter.length))
                        } else {
                            editText.setText(textAfter)
                            editText.setSelection(max(cursorPos - diff + 1, 0))
                        }

                    }
                } catch (e: Throwable) {
                    editText.setText("")
                }
            } else {
                textBefore = text.toString()
                isTextWasArtificiallyChanged = true
            }
        }
    }

    fun getTextWithoutFormatting() = editText.text.toString().withoutFormatting()

    private fun setTextWhichWasPasted(text: String) {
        var result = ""
        var decimalLength = -1
        var index = 0
        while (decimalLength < decimalPartLength && index < text.length) {
            if (text[index] == decimalSeparator[0]) {
                if (decimalLength == -1 && decimalPartLength != 0) {
                    decimalLength = 0
                    result += text[index]
                } else {
                    break
                }
            } else {
                result += text[index]
            }
            index++
        }
        result = result.formatMoney(decimalPartLength)
        editText.setText(result)
        editText.setSelection(result.length)
    }

    private fun String.withoutFormatting(): String {
        var result = this
        hardcodedSymbols.forEach { result = this.replace(it.toString(), "") }
        return result
    }

    private fun String.prepareForDoubleCast(): String {
        var result = this
        possibleDecimalSeparators.forEach {
            result = result.replace(it, ".")
        }
        return result.withoutFormatting()
    }

    private fun isTextErased(textBefore: String, textAfter: String) =
            textAfter.length <= textBefore.length

    private fun String.formatMoney(currentDecimalPartLength: Int?): String {
        var mask = COMMON_MONEY_MASK
        if (currentDecimalPartLength != null && decimalPartLength != 0) {
            mask += "." + "0".repeat(min(currentDecimalPartLength, decimalPartLength))
        }

        val formatter = DecimalFormat(mask)
        formatter.decimalFormatSymbols = DecimalFormatSymbols().also {
            it.decimalSeparator = decimalSeparator[0]
            it.groupingSeparator = GROUPING_SEPARATOR
        }
        return formatter.format(this.prepareForDoubleCast().toDouble().floor())
    }

    private fun Double.floor() =
            (this * 10.toDouble().pow(decimalPartLength)).toLong() / 10.toDouble()
                    .pow(decimalPartLength)

}
