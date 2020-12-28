package ru.touchin.roboswag.views.widget

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToLong

class AmountWithDecimalDecorator(
        val editText: EditText,
        val decimalSeparator: String = DEFAULT_DECIMAL_SEPARATOR,
        val decimalPartLength: Int = DEFAULT_DECIMAL_PART_LENGTH,
        val integerPartLength: Int = DEFAULT_INTEGER_PART_LENGTH,
        val isSeparatorCutInvalidDecimalLength: Boolean = false
) {

    companion object {

        private const val COMMON_MONEY_MASK = "###,##0"
        private const val DOT_SYMBOL = "."
        private const val DEFAULT_DECIMAL_SEPARATOR = DOT_SYMBOL
        private const val GROUPING_SEPARATOR = ' '
        private const val DEFAULT_DECIMAL_PART_LENGTH = 2
        private const val DEFAULT_INTEGER_PART_LENGTH = 13
        private val hardcodedSymbols = listOf(GROUPING_SEPARATOR)
        private val possibleDecimalSeparators = listOf(",", ".")

    }

    var onTextChanged: (text: String) -> Unit = {}

    private var previousInputtedText = ""
    private var isTextWasArtificiallyChanged = true

    init {
        if (!possibleDecimalSeparators.contains(decimalSeparator)) {
            throw IllegalArgumentException("Not allowed decimal separator. Supports only: $possibleDecimalSeparators")
        }

        editText.doOnTextChanged { text, _, _, _ -> doOnTextChanged(text.toString()) }
    }

    fun getTextWithoutFormatting(decimalSeparatorToReplace: String = decimalSeparator): String =
            previousInputtedText.withoutFormatting(decimalSeparatorToReplace)

    @Suppress("detekt.TooGenericExceptionCaught", "detekt.LongMethod", "detekt.ComplexMethod")
    private fun doOnTextChanged(text: String) {
        if (isTextWasArtificiallyChanged) {
            isTextWasArtificiallyChanged = false
            val cursorPosition = editText.selectionStart
            try {
                var currentText = text
                possibleDecimalSeparators.forEach { currentText = currentText.replace(it, decimalSeparator) }
                val currentIntegerPartLength = currentText.withoutFormatting().split(decimalSeparator)[0].length

                if (isIntegerPartToLong(currentIntegerPartLength)) {
                    currentText = trimIntegerPart(currentText)
                }

                if (isTextFormatIncorrect(currentText)) {
                    setTextWhenNewInputIncorrect(currentText, cursorPosition)
                    return
                }

                if (isTextHasHeadZero(currentText)) {
                    setTextWithHeadZero(currentText, cursorPosition)
                    return
                }

                val currentDecimalPartLength = currentText.split(decimalSeparator).getOrNull(1)?.length
                if (isDecimalPartTooLong(currentDecimalPartLength)) {
                    setTextWhenNewInputIncorrect(currentText, cursorPosition)
                    return
                }

                val formattedText = if (currentText.isNotEmpty()) {
                    currentText.withoutFormatting().formatMoney(currentDecimalPartLength)
                } else ""

                if (isTextErased(previousInputtedText, formattedText)) {
                    setTextAfterUserErase(formattedText, cursorPosition)
                } else {
                    setTextAfterUserInput(formattedText, cursorPosition)
                }
            } catch (e: Throwable) {
                editText.setText(previousInputtedText)
                editText.setSelection(previousInputtedText.length)
            }
        } else {
            previousInputtedText = text
            isTextWasArtificiallyChanged = true
            onTextChanged(text)
        }
    }

    private fun isIntegerPartToLong(currentIntegerPartLength: Int) = currentIntegerPartLength > integerPartLength

    private fun trimIntegerPart(currentText: String): String {
        val splittedText = currentText.withoutFormatting().split(decimalSeparator)
        val integerPart = splittedText[0]
        val decimalPart = if (splittedText.size > 1) decimalSeparator + splittedText[1] else ""
        return integerPart.take(integerPartLength) + decimalPart
    }

    private fun isTextFormatIncorrect(currentText: String) =
            currentText == decimalSeparator
                    || currentText.count { it == decimalSeparator[0] } > 1
                    || currentText.take(2) == "00"

    private fun isTextHasHeadZero(currentText: String) =
            currentText.length >= 2 && currentText[0] == '0' && currentText[1] != decimalSeparator[0]

    private fun setTextWithHeadZero(text: String, cursorPosition: Int) {
        if (abs(previousInputtedText.length - text.length) > 1) {
            setTextWhichWasInserted(text)
        } else {
            editText.setText(text.substring(1, text.length))
            editText.setSelection(max(cursorPosition - 1, 0))
        }
    }

    private fun setTextWhenNewInputIncorrect(text: String, cursorPosition: Int) {
        if (abs(previousInputtedText.length - text.length) > 1) {
            setTextWhichWasInserted(text)
        } else {
            editText.setText(previousInputtedText)
            editText.setSelection(max(cursorPosition - 1, 0))
        }
    }

    private fun setTextAfterUserInput(formattedText: String, cursorPosition: Int) {
        val diff = formattedText.length - previousInputtedText.length - 1
        editText.setText(formattedText)
        editText.setSelection(min(cursorPosition + diff, formattedText.length))
    }

    private fun setTextAfterUserErase(formattedText: String, cursorPosition: Int) {
        if (!previousInputtedText.contains(decimalSeparator)
                && formattedText.contains(decimalSeparator)
        ) {
            editText.setText(formattedText)
            editText.setSelection(min(formattedText.length, formattedText.indexOf(decimalSeparator) + 1))
            return
        }
        val diff = previousInputtedText.length - formattedText.length
        if (diff == 0) {
            editText.setText(formattedText)
            editText.setSelection(min(cursorPosition, formattedText.length))
        } else {
            editText.setText(formattedText)
            editText.setSelection(max(cursorPosition - diff + 1, 0))
        }
    }

    private fun isDecimalPartTooLong(currentDecimalPartLength: Int?) =
            !isSeparatorCutInvalidDecimalLength
                    && currentDecimalPartLength != null
                    && currentDecimalPartLength > decimalPartLength

    private fun setTextWhichWasInserted(text: String) {
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

    private fun String.withoutFormatting(decimalSeparatorToReplace: String = decimalSeparator): String {
        var result = this
        hardcodedSymbols.forEach { result = this.replace(it.toString(), "") }
        result = result.replace(decimalSeparator, decimalSeparatorToReplace)
        return result
    }

    private fun String.replaceSeparatorsToDot(): String {
        var result = this
        possibleDecimalSeparators.forEach {
            result = result.replace(it, DOT_SYMBOL)
        }
        return result.withoutFormatting()
    }

    private fun isTextErased(textBefore: String, formattedText: String) =
            formattedText.length <= textBefore.length

    private fun String.formatMoney(currentDecimalPartLength: Int?): String {
        var mask = COMMON_MONEY_MASK
        if (currentDecimalPartLength != null && decimalPartLength != 0) {
            mask += DOT_SYMBOL + "0".repeat(min(currentDecimalPartLength, decimalPartLength))
        }

        val formatter = DecimalFormat(mask)
        formatter.decimalFormatSymbols = DecimalFormatSymbols().also {
            it.decimalSeparator = decimalSeparator[0]
            it.groupingSeparator = GROUPING_SEPARATOR
        }
        return formatter.format(this.replaceSeparatorsToDot().toDouble().floor())
    }

    // TODO make it simple
    private fun Double.floor() = (this * 10.0.pow(decimalPartLength)).roundToLong() / 10.0.pow(decimalPartLength)

}
