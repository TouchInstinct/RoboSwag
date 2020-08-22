package ru.touchin.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import ru.touchin.roboswag.components.views.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

class AmountWithDecimalEditText @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    companion object {

        private const val COMMON_MONEY_MASK = "###,##0"
        private const val DEFAULT_DECIMAL_SEPARATOR = ","
        private const val GROUPING_SEPARATOR = ' '
        private const val DEFAULT_DECIMAL_PART_LENGTH = 2
        private val hardcodedSymbols = listOf(GROUPING_SEPARATOR)
        private val possibleDecimalSeparators = listOf(",", ".")
    }

    private var textBefore = ""
    private var isTextWasArtificiallyChanged = true

    var decimalSeparator = DEFAULT_DECIMAL_SEPARATOR
        set(value) {
            if (!possibleDecimalSeparators.contains(value))
                throw Exception("Not allowed decimal separator. Supports only: $possibleDecimalSeparators")
            field = value
        }


    var decimalPartLength = DEFAULT_DECIMAL_PART_LENGTH
    var isSeparatorCutInvalidDecimalLength = false

    init {
        doOnTextChanged { text, _, _, _ ->
            if (isTextWasArtificiallyChanged) {
                isTextWasArtificiallyChanged = false
                val cursorPos = selectionStart
                var text = text.toString()
                possibleDecimalSeparators.forEach {
                    text = text.replace(it, decimalSeparator)
                }

                if (text == decimalSeparator || text.count { it == decimalSeparator[0] } > 1) {
                    if (abs(textBefore.length - text.length) > 1) {
                        setTextWhichWasPasted(text)
                    } else {
                        setText(textBefore)
                        setSelection(max(cursorPos - 1, 0))
                    }
                    return@doOnTextChanged
                }

                if (text.take(2) == "00") {
                    if (abs(textBefore.length - text.length) > 1) {
                        setTextWhichWasPasted(text)
                    } else {
                        setText(textBefore)
                        setSelection(max(cursorPos - 1, 0))
                    }
                    return@doOnTextChanged
                }

                if (text.length >= 2 && text[0] == '0' && text[1] != decimalSeparator[0]) {
                    if (abs(textBefore.length - text.length) > 1) {
                        setTextWhichWasPasted(text)
                    } else {
                        setTextWhichWasPasted(text)
                        setSelection(max(cursorPos - 1, 0))
                    }
                    return@doOnTextChanged
                }

                val decimalPartLength_ = text.split(decimalSeparator).getOrNull(1)?.length
                if (!isSeparatorCutInvalidDecimalLength && decimalPartLength_ != null && decimalPartLength_ > decimalPartLength) {
                    if (abs(textBefore.length - text.length) > 1) {
                        setTextWhichWasPasted(text)
                    } else {
                        setText(textBefore)
                        setSelection(max(cursorPos - 1, 0))
                    }
                    return@doOnTextChanged
                }

                val textAfter = if (text.isNotEmpty()) {
                    text.withoutFormatting().formatMoney(decimalPartLength_)
                } else ""

                if (!isTextErased(textBefore, textAfter)) {
                    val diff = textAfter.length - textBefore.length - 1
                    setText(textAfter)
                    setSelection(min(cursorPos + diff, textAfter.length))
                } else {
                    if (!textBefore.contains(decimalSeparator)
                            && textAfter.contains(decimalSeparator)
                    ) {
                        setText(textAfter)
                        setSelection(min(textAfter.length, textAfter.indexOf(decimalSeparator) + 1))
                        return@doOnTextChanged
                    }
                    val diff = textBefore.length - textAfter.length
                    if (diff == 0) {
                        setText(textAfter)
                        setSelection(min(cursorPos, textAfter.length))
                    } else {
                        setText(textAfter)
                        setSelection(max(cursorPos - diff + 1, 0))
                    }

                }
            } else {
                textBefore = text.toString()
                isTextWasArtificiallyChanged = true
            }
        }
    }

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
        setText(result)
        setSelection(result.length)
    }


    fun getTextWithoutFormatting() = text.toString().withoutFormatting()

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

    private fun String.formatMoney(decimalPartLength_: Int?): String {
        var mask = COMMON_MONEY_MASK
        if (decimalPartLength_ != null && decimalPartLength != 0) {
            mask += "." + "0".repeat(min(decimalPartLength_, decimalPartLength))
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