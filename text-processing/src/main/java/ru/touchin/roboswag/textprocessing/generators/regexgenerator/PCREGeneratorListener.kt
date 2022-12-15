package ru.touchin.roboswag.textprocessing.generators.regexgenerator

import ru.touchin.roboswag.textprocessing.Constants
import ru.touchin.roboswag.textprocessing.pcre.parser.PCREBaseListener
import ru.touchin.roboswag.textprocessing.pcre.parser.PCREParser

class PCREGeneratorListener : PCREBaseListener() {
    /**
     *  Лист для placeholder, где индекс - номер буквы для placeholder
     *  значение - возможные символы для placeholder
     *  **/
    private var mutableList = mutableListOf<String>()
    private var currentGroupIndex = 1
    private var regexReplaceString = ""

    /** Элемент поиска с регулярного выражения
     * В себе может содержать возможные элементы регулярного выражения,
     * например:
     * [1-2], \\d, [A-B], а так же элементы не относящиеся к регулярным выражениям
     * или экранизированые
     * **/
    private var charsAtom = ""

    override fun enterCapture(ctx: PCREParser.CaptureContext) {
        super.enterCapture(ctx)
        regexReplaceString += "\$$currentGroupIndex"
        currentGroupIndex++
    }

    override fun enterShared_atom(ctx: PCREParser.Shared_atomContext) {
        super.enterShared_atom(ctx)
        /** Найдено соответствие цифр \\d **/
        charsAtom = Constants.digits
        mutableList.add(charsAtom)
    }

    override fun enterCharacter_class(ctx: PCREParser.Character_classContext) {
        super.enterCharacter_class(ctx)
        /** Проверка на количество диапазонов
         * true - если, например [А-дD-f]
         * false - если, например [А-д]
         * **/
        if (ctx.cc_atom().size > 1) {
            charsAtom = ""
            val firstChar = ctx.CharacterClassStart().text
            val endChar = ctx.CharacterClassEnd()[0].text
            for (i in 0 until ctx.cc_atom().size) {
                charsAtom += checkRules(firstChar + ctx.cc_atom()[i].text + endChar)
            }
        } else {
            charsAtom = checkRules(ctx.text)
        }
        mutableList.add(charsAtom)
    }

    /** Дублирование повторений для placeholder при их наличии, например [A-B]{6}, где 6 - повторения **/
    override fun enterDigits(ctx: PCREParser.DigitsContext) {
        super.enterDigits(ctx)
        repeat(ctx.text.toInt() - 1) {
            mutableList.add(charsAtom)
        }
    }

    override fun enterLiteral(ctx: PCREParser.LiteralContext) {
        super.enterLiteral(ctx)
        regexReplaceString += ctx.shared_literal().text
        charsAtom = ctx.text
        mutableList.add(charsAtom)
    }

    fun toPCREGeneratorItem() = PCREGeneratorItem(
        regexReplaceString,
        mutableList.map {
            it.replace("\\", "")
        }.toMutableList()
    )
}