package ru.touchin.roboswag.textprocessing.generators.regexgenerator

import ru.touchin.roboswag.textprocessing.Constants

data class IndexesItem(val firstIndex: Int, val lastIndex: Int)

internal fun PCREGeneratorListener.checkRules(ctxText: String): String {
    var atomStr = ctxText
    var resultCharsAtom = ""
    /** endAtomStr index of atomStr.length - 2 вычисляется потому что с поиском, например,
     * в [A-B] должен проверяться последний допуск для строки в данном случае это В
     * startAtomStr = atomStr[1] - потому что должен проверяться первый допуск для строки
     * в [A-B] это будет А
     * **/
    val endAtomStr = atomStr[atomStr.length - 2]
    val startAtomStr = atomStr[1]
    when {
        atomStr.contains('-') -> {
            when {
                /** Проверка соответствия начала диапазона Русских букв на верхний регистр **/
                Constants.charsRuUpper.contains(startAtomStr) -> {
                    when {
                        /** Проверка соответствия диапазона Русских букв с верхним и нижнем регистром **/
                        Constants.charsRuLower.contains(endAtomStr) -> {
                            atomStr = atomStr.uppercase()
                            val (firstIndex, lastIndex) = findFirstAndLastIndex(atomStr, Constants.charsRuUpper)
                            resultCharsAtom = Constants.charsRuUpper.substring(firstIndex, lastIndex) +
                                    Constants.charsRuLower.substring(firstIndex, lastIndex)
                        }
                        /** Проверка соответствия диапазона Русских букв с верхним регистром **/
                        else -> {
                            atomStr = atomStr.uppercase()
                            val (firstIndex, lastIndex) = findFirstAndLastIndex(atomStr, Constants.charsRuUpper)
                            resultCharsAtom = Constants.charsRuUpper.substring(firstIndex, lastIndex)
                        }
                    }
                }
                /** Проверка соответствия начала диапазона Русских букв на нижний регистр **/
                Constants.charsRuLower.contains(startAtomStr) -> {
                    when {
                        /** Проверка соответствия диапазона Русских букв с нижним и верхнем регистром **/
                        Constants.charsRuUpper.contains(endAtomStr) -> {
                            atomStr = atomStr.lowercase()
                            val (firstIndex, lastIndex) = findFirstAndLastIndex(atomStr, Constants.charsRuLower)
                            resultCharsAtom = Constants.charsRuLower.substring(firstIndex, lastIndex) +
                                    Constants.charsRuUpper.substring(firstIndex, lastIndex)
                        }
                        /** Проверка соответствия диапазона Русских букв с нижним регистром **/
                        else -> {
                            atomStr = atomStr.lowercase()
                            val (firstIndex, lastIndex) = findFirstAndLastIndex(atomStr, Constants.charsRuLower)
                            resultCharsAtom = Constants.charsRuLower.substring(firstIndex, lastIndex)
                        }
                    }
                }
                /** Проверка соответствия начала диапазона Английских букв на верхний регистр **/
                Constants.charsEngUpper.contains(startAtomStr) -> {
                    when {
                        /** Проверка соответствия диапазона Английских букв с верхним и нижнем регистром **/
                        Constants.charsEngLower.contains(endAtomStr) -> {
                            atomStr = atomStr.uppercase()
                            val (firstIndex, lastIndex) = findFirstAndLastIndex(atomStr, Constants.charsEngUpper)
                            resultCharsAtom = Constants.charsEngUpper.substring(firstIndex, lastIndex) +
                                    Constants.charsEngLower.substring(firstIndex, lastIndex)
                        }
                        /** Проверка соответствия диапазона Аглийских букв с верхним регистром **/
                        else -> {
                            atomStr = atomStr.uppercase()
                            val (firstIndex, lastIndex) = findFirstAndLastIndex(atomStr, Constants.charsEngUpper)
                            resultCharsAtom = Constants.charsEngUpper.substring(firstIndex, lastIndex)
                        }
                    }
                }
                /** Проверка соответствия начала диапазона Английских букв на нижний регистр **/
                Constants.charsEngLower.contains(startAtomStr) -> {
                    when {
                        /** Проверка соответствия диапазона Английских букв с нижним и верхнем регистром **/
                        Constants.charsEngUpper.contains(endAtomStr) -> {
                            atomStr = atomStr.lowercase()
                            val (firstIndex, lastIndex) = findFirstAndLastIndex(atomStr, Constants.charsEngLower)
                            resultCharsAtom = Constants.charsEngLower.substring(firstIndex, lastIndex) +
                                    Constants.charsEngUpper.substring(firstIndex, lastIndex)
                        }
                        /** Проверка соответствия диапазона Аглийских букв с нижним регистром **/
                        else -> {
                            atomStr = atomStr.lowercase()
                            val (firstIndex, lastIndex) = findFirstAndLastIndex(atomStr, Constants.charsEngLower)
                            resultCharsAtom = Constants.charsEngLower.substring(firstIndex, lastIndex)
                        }
                    }
                }
                /** Проверка соответствия диапазона Цифр букв с нижним регистром **/
                Constants.digits.contains(startAtomStr) -> {
                    /** Регулярки проверяют с 0, а placeholder с 1 **/
                    val digitsRegex = "0123456789"
                    val (firstIndex, lastIndex) = findFirstAndLastIndex(atomStr, digitsRegex)
                    resultCharsAtom = digitsRegex.substring(firstIndex, lastIndex)
                }
            }
        }
        /** Результат при остальных соответствиях **/
        else -> {
            resultCharsAtom = atomStr.substring(1, atomStr.length - 1)
        }
    }
    return resultCharsAtom
}

/** atomStr в себе должен содержать atomStr(описание выше)
 * chars в себе должен содержать элементы по порядку алфавитному или возрастанию
 * тех символов, которые находятся в atomStr
 * **/
internal fun PCREGeneratorListener.findFirstAndLastIndex(atomStr: String, chars: String): IndexesItem {
    var firstIndex = 0
    var lastIndex = chars.length - 1
    for (i in chars.indices) {
        if (chars[i] == atomStr[1]) firstIndex = i
        if (chars[i] == atomStr[atomStr.length - 2]) lastIndex = i + 1
    }
    return IndexesItem(firstIndex, lastIndex)
}