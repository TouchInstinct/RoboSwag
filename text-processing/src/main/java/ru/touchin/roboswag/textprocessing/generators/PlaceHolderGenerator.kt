package ru.touchin.roboswag.textprocessing.generators

import ru.touchin.roboswag.textprocessing.Constants.charsEngLower
import ru.touchin.roboswag.textprocessing.Constants.charsEngUpper
import ru.touchin.roboswag.textprocessing.Constants.charsRuLower
import ru.touchin.roboswag.textprocessing.Constants.charsRuUpper
import ru.touchin.roboswag.textprocessing.Constants.digits

class PlaceholderGenerator(
    listForPlaceholder: List<String>
) {

    private var placeholder: String = ""

    init {
        /** Индексы для всех возможных вариантов:
         * Цифр, Ангийских букв верхнего регистра,
         * Русских букв верхнего регистра,
         * Английских букв нижнего регистра,
         * Русских букв верхнего и нижнего регистра,
         * Английских букв верхнего и нижнего регистра,
         * На случай остальных повторений
         * **/
        var indexDigits = 0
        var indexCharsEngUpper = 0
        var indexCharsRuUpper = 0
        var indexCharsEngLower = 0
        var indexCharsRuLower = 0
        var indexUpNLowerRu = 0
        var indexUpNLowerEng = 0
        var indexElse = 0
        for (str in listForPlaceholder) {
            if (str.isEmpty()) continue
            /** Если элемент без повторений **/
            if (str.length == 1) {
                placeholder += str
                continue
            }
            when {
                /** Если элемент повторяет цифры **/
                digits.contains(str) -> {
                    if (str.length <= indexDigits) indexDigits = 0
                    placeholder += str[indexDigits]
                    indexDigits++
                }
                /** Если элемент повторяет Английские буквы верхнего регистра **/
                charsEngUpper.contains(str) -> {
                    if (str.length <= indexCharsEngUpper) indexCharsEngUpper = 0
                    placeholder += str[indexCharsEngUpper]
                    indexCharsEngUpper++
                }
                /** Если элемент повторяет Английские буквы нижнего регистра **/
                charsEngLower.contains(str) -> {
                    if (str.length <= indexCharsEngLower) indexCharsEngLower = 0
                    placeholder += str[indexCharsEngLower]
                    indexCharsEngLower++
                }
                /** Если элемент повторяет Русские буквы верхнего регистра **/
                charsRuUpper.contains(str) -> {
                    if (str.length <= indexCharsRuUpper) indexCharsRuUpper = 0
                    placeholder += str[indexCharsRuUpper]
                    indexCharsRuUpper++
                }
                /** Если элемент повторяет Русские буквы нижнего регистра **/
                charsRuLower.contains(str) -> {
                    if (str.length <= indexCharsRuLower) indexCharsRuLower = 0
                    placeholder += str[indexCharsRuLower]
                    indexCharsRuLower++
                }
                /** Если элемент повторяет Русские буквы верхнего и нижнего регистра **/
                charsRuLower.contains(str[str.length - 1]) -> {
                    if (str.length <= indexUpNLowerRu) indexUpNLowerRu = 0
                    placeholder += str[indexUpNLowerRu]
                    indexUpNLowerRu++
                }
                /** Если элемент повторяет Аглийские буквы верхнего и нижнего регистра **/
                charsEngLower.contains(str[str.length - 1]) -> {
                    if (str.length <= indexUpNLowerEng) indexUpNLowerEng = 0
                    placeholder += str[indexUpNLowerEng]
                    indexUpNLowerEng++
                }
                /** Если элемент повторяет Другие символы **/
                else -> {
                    if (str.length <= indexElse) indexElse = 0
                    placeholder += str[indexElse]
                    indexElse++
                }
            }
        }
    }

    fun getPlaceholder() = placeholder
}