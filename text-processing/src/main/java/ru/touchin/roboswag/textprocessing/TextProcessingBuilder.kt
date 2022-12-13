package ru.touchin.roboswag.textprocessing

import nl.bigo.pcreparser.PCREBaseListener
import nl.bigo.pcreparser.PCRELexer
import nl.bigo.pcreparser.PCREParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker

class TextProcessingBuilder(regex: String, inputText: String) {

    private var _regexReplaceString = ""
    private var _formatString = ""
    private var _placeholder = ""

    val regexReplaceString by ::_regexReplaceString
    val formatString by ::_formatString
    val placeholder by ::_placeholder
}