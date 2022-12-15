package ru.touchin.roboswag.textprocessing.generators.regexgenerator

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import ru.touchin.roboswag.textprocessing.pcre.parser.PCRELexer
import ru.touchin.roboswag.textprocessing.pcre.parser.PCREParser

class RegexReplaceGenerator {

    fun regexToRegexReplace(regex: String): PCREGeneratorItem {
        val stringStream = CharStreams.fromString(regex)
        val lexer = PCRELexer(stringStream)
        val parser = PCREParser(CommonTokenStream(lexer))
        val parseContext = parser.parse()
        val walker = ParseTreeWalker()
        val pcreGeneratorListener = PCREGeneratorListener()
        walker.walk(pcreGeneratorListener, parseContext)
        return pcreGeneratorListener.toPCREGeneratorItem()
    }
}