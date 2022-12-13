package ru.touchin.roboswag.textprocessing

import nl.bigo.pcreparser.PCREBaseListener
import nl.bigo.pcreparser.PCRELexer
import nl.bigo.pcreparser.PCREParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker

class TextProcessingBuilder(regex: String, inputText: String) {

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
    }

    private fun format(inputText: String, regex: String) {
        _formatString = inputText.replace(Regex(regex), _regexReplaceString)
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