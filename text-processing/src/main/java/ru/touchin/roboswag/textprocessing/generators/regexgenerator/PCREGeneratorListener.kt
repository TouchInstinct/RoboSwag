package ru.touchin.roboswag.textprocessing.generators.regexgenerator

import ru.touchin.roboswag.textprocessing.pcre.parser.PCREBaseListener
import ru.touchin.roboswag.textprocessing.pcre.parser.PCREParser

class PCREGeneratorListener : PCREBaseListener() {
    /**
     *  Лист для placeholder, где индекс - номер буквы для placeholder
     *  значение - возможные символы для placeholder
     *  **/
    private val matrixOfSymbols = mutableListOf<List<Char>>()
    private var currentGroupIndex = 1
    private var regexReplaceString = ""

    /** Элемент поиска с регулярного выражения
     * В себе может содержать возможные элементы регулярного выражения,
     * например:
     * [1-2], \\d, [A-B], а так же элементы не относящиеся к регулярным выражениям
     * или экранизированые
     * **/
    private var listOfSymbols = mutableListOf<Char>()

    override fun enterCapture(ctx: PCREParser.CaptureContext) {
        super.enterCapture(ctx)
        regexReplaceString += "\$$currentGroupIndex"
        currentGroupIndex++
    }

    override fun enterShared_atom(ctx: PCREParser.Shared_atomContext) {
        super.enterShared_atom(ctx)
        /** Найдено соответствие цифр \\d **/
        listOfSymbols = '1'.rangeTo('9').toMutableList().apply { add('0') }
        matrixOfSymbols.add(listOfSymbols)
    }

    override fun enterCharacter_class(ctx: PCREParser.Character_classContext) {
        super.enterCharacter_class(ctx)
        /** Проверка на количество диапазонов
         * true - если, например [А-дD-f]
         * false - если, например [А-д]
         * **/
        if (ctx.cc_atom().size > 1) {
            listOfSymbols = mutableListOf<Char>()
            val firstChar = ctx.CharacterClassStart().text
            val endChar = ctx.CharacterClassEnd()[0].text
            for (i in 0 until ctx.cc_atom().size) {
                listOfSymbols += availableSymbolsToList(firstChar + ctx.cc_atom()[i].text + endChar)
            }
        } else {
            listOfSymbols = availableSymbolsToList(ctx.text)
        }
        matrixOfSymbols.add(listOfSymbols)
    }

    /** Дублирование повторений для placeholder при их наличии, например [A-B]{6}, где 6 - повторения **/
    override fun enterDigits(ctx: PCREParser.DigitsContext) {
        super.enterDigits(ctx)
        repeat(ctx.text.toInt() - 1) {
            matrixOfSymbols.add(listOfSymbols)
        }
    }

    override fun enterLiteral(ctx: PCREParser.LiteralContext) {
        super.enterLiteral(ctx)
        regexReplaceString += ctx.shared_literal().text
        listOfSymbols = mutableListOf<Char>()
        for (s in ctx.text) {
            listOfSymbols.add(s)
        }
        matrixOfSymbols.add(listOfSymbols)
    }

    fun toPCREGeneratorItem() = PCREGeneratorItem(
        regexReplaceString,
        matrixOfSymbols.map { it ->
            it.filter {
                it != '\\'
            }
        }
    )

    private fun availableSymbolsToList(ctxText: String): MutableList<Char> {
        /** startAtomStr = atomStr[1] - потому что должен проверяться первый допуск для строки
         * endAtomStr index of atomStr.length - 2 вычисляется потому что с поиском,
         * например, [A-B], endAtomStr = "B", startAtomStr = "A"
         * **/
        val endAtomStr = ctxText[ctxText.length - 2]
        val startAtomStr = ctxText[1]
        return if (startAtomStr.isLetterOrDigit() && endAtomStr.isLetterOrDigit()) {
            getListRangeChars(ctxText).filter {
                it.isLetterOrDigit()
            }.toMutableList()
        } else {
            mutableListOf(startAtomStr, endAtomStr)
        }
    }

    private fun getListRangeChars(atomStr: String): MutableList<Char> {
        val startRange = atomStr[1]
        val endRange = atomStr[atomStr.length - 2]
        return startRange.rangeTo(endRange).toMutableList()
    }
}