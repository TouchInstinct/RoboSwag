package ru.touchin.roboswag.textprocessing

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import ru.touchin.roboswag.textprocessing.pcre.parser.PCREBaseListener
import ru.touchin.roboswag.textprocessing.pcre.parser.PCRELexer
import ru.touchin.roboswag.textprocessing.pcre.parser.PCREParser

class TextFormatter(private val regex: String) {

    private var currentGroupIndex = 1
    private var regexReplaceString = ""

    init {
        regexToRegexReplace(regex)
    }

    fun getFormatText(inputText: String): String {
        return inputText.replace(Regex(regex), regexReplaceString)
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
}