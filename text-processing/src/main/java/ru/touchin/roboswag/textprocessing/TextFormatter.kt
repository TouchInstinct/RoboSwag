package ru.touchin.roboswag.textprocessing

import android.widget.TextView
import com.mifmif.common.regex.Generex
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import ru.touchin.roboswag.textprocessing.pcre.parser.PCREBaseListener
import ru.touchin.roboswag.textprocessing.pcre.parser.PCRELexer
import ru.touchin.roboswag.textprocessing.pcre.parser.PCREParser
import ru.touchin.roboswag.textprocessing.validators.DigitsValidator
import ru.touchin.roboswag.textprocessing.validators.EngLowerCharsValidator
import ru.touchin.roboswag.textprocessing.validators.EngUpperCharsValidator
import ru.touchin.roboswag.textprocessing.validators.RuLowerCharsValidator
import ru.touchin.roboswag.textprocessing.validators.RuUpperCharsValidator

class TextFormatter(private val regex: String) {

    private companion object {
        const val digits = "1234567890"

        const val charsRuUpper = "АБВГДЕЖЗИЙКЛМНОПРСТУФЧЦЧЭЮЯЪЬЫШ"
        const val charsEngUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        const val charsRuLower = "абвгдежзийклмнопрстуфчцэюяъьыш"
        const val charsEngLower = "abcdefghijklmnopqrstuvwxyz"
    }

    private var currentGroupIndex = 1
    private var regexReplaceString = ""
    private var generatedRegex: String? = null
    private var placeHolder: String? = null

    init {
        regexToRegexReplace(regex)
    }

    fun getFormatText(inputText: String): String {
        return inputText.replace(Regex(regex), regexReplaceString)
    }

    fun getPlaceHolder(): String {
        if (placeHolder == null) {
            val inputText = getGeneratedRegex()
            var indexRuChar = 0
            var indexEngChar = 0
            var indexDigit = 0
            var replacementStr = ""
            for (s in inputText) {
                when {
                    digits.contains(s) -> {
                        if (digits.length <= indexDigit) indexDigit = 0
                        replacementStr += digits[indexDigit]
                        indexDigit++
                    }
                    charsEngUpper.contains(s) -> {
                        if (charsEngUpper.length <= indexEngChar) indexEngChar = 0
                        replacementStr += charsEngUpper[indexEngChar]
                        indexEngChar++
                    }
                    charsRuUpper.contains(s) -> {
                        if (charsRuUpper.length <= indexRuChar) indexRuChar = 0
                        replacementStr += charsRuUpper[indexRuChar]
                        indexRuChar++
                    }
                    charsEngLower.contains(s) -> {
                        if (charsEngLower.length <= indexEngChar) indexEngChar = 0
                        replacementStr += charsEngLower[indexEngChar]
                        indexEngChar++
                    }
                    charsRuLower.contains(s) -> {
                        if (charsRuLower.length <= indexRuChar) indexRuChar = 0
                        replacementStr += charsRuLower[indexRuChar]
                        indexRuChar++
                    }
                    else -> {
                        replacementStr += s
                    }
                }
            }
            placeHolder = if (regexReplaceString.isNotEmpty())
                replacementStr.replace(Regex(regex), regexReplaceString)
            else
                replacementStr
        }
        return placeHolder!!
    }

    fun getRegexReplace() = regexReplaceString

    private fun regexToRegexReplace(regex: String) {
        val stringStream = CharStreams.fromString(regex)
        val lexer = PCRELexer(stringStream)
        val parser = PCREParser(CommonTokenStream(lexer))
        val parseContext = parser.parse()
        val walker = ParseTreeWalker()
        walker.walk(
            object : PCREBaseListener() {
                override fun enterCapture(ctx: PCREParser.CaptureContext) {
                    super.enterCapture(ctx)
                    regexReplaceString += "\$$currentGroupIndex"
                    currentGroupIndex++
                }

                override fun enterLiteral(ctx: PCREParser.LiteralContext) {
                    super.enterLiteral(ctx)
                    regexReplaceString += ctx.shared_literal().text
                }
            },
            parseContext
        )
    }

    fun mask(textView: TextView) {
        val inputText = getPlaceHolder()
        val slots = mutableListOf<Slot>()
        for (s in inputText) {
            slots.add(
                when {
                    digits.contains(s) -> DigitsValidator.digitSlot()
                    charsEngUpper.contains(s) -> EngUpperCharsValidator.engCharSlot()
                    charsRuUpper.contains(s) -> RuUpperCharsValidator.ruCharSlot()
                    charsEngLower.contains(s) -> EngLowerCharsValidator.engCharSlot()
                    charsRuLower.contains(s) -> RuLowerCharsValidator.ruCharSlot()
                    else -> PredefinedSlots.hardcodedSlot(s)
                }
            )
        }
        val formatWatcher =
            MaskFormatWatcher(MaskImpl.createTerminated(slots.toTypedArray()))
        formatWatcher.installOn(textView)
    }

    private fun getGeneratedRegex(): String {
        if (generatedRegex == null) {
            generatedRegex = Generex(regex).random(5)
            if (generatedRegex!!.contains("?:")) {
                generatedRegex = null
                getGeneratedRegex()
            }
        }
        return generatedRegex!!
    }
}