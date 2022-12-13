package ru.touchin.roboswag.textprocessing

import nl.bigo.pcreparser.PCREBaseListener
import nl.bigo.pcreparser.PCRELexer
import nl.bigo.pcreparser.PCREParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker

class TextProcessingBuilder(regex: String, inputText: String) {

    private companion object {
        const val digits = "1234567890"
        const val charsRu = "АБВГДЕЖЗИЙКЛМНОПРСТУФЧЦЧЭЮЯ"
        const val charsEng = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    }

    private var currentGroupIndex = 1
    private var _regexReplaceString = ""
    private var _formatString = ""
    private var _placeholder = ""

    val regexReplaceString by ::_regexReplaceString
    val formatString by ::_formatString
    val placeholder by ::_placeholder

    init {
        regexToRegexReplace(regex)
        format(inputText, regex)
        placeHolder(inputText, regex)
    }

    private fun format(inputText: String, regex: String) {
        _formatString = inputText.replace(Regex(regex), _regexReplaceString)
    }

    private fun placeHolder(inputText: String, regex: String) {
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
                charsEng.contains(s) -> {
                    if (charsEng.length <= indexEngChar) indexEngChar = 0
                    replacementStr += charsEng[indexEngChar]
                    indexEngChar++
                }
                charsRu.contains(s) -> {
                    if (charsRu.length <= indexRuChar) indexRuChar = 0
                    replacementStr += charsRu[indexRuChar]
                    indexRuChar++
                }
                else -> {
                    replacementStr += s
                }
            }
        }
        _placeholder = replacementStr.replace(Regex(regex), _regexReplaceString)
    }

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
                    _regexReplaceString += "\$$currentGroupIndex"
                    currentGroupIndex++
                }
                override fun enterLiteral(ctx: PCREParser.LiteralContext) {
                    super.enterLiteral(ctx)
                    _regexReplaceString += ctx.shared_literal().text
                }
            },
            parseContext
        )
    }
}