package ru.touchin.roboswag.textprocessing

import android.widget.EditText
import ru.touchin.roboswag.textprocessing.generators.DecoroMaskGenerator
import ru.touchin.roboswag.textprocessing.generators.PlaceholderGenerator
import ru.touchin.roboswag.textprocessing.generators.regexgenerator.RegexReplaceGenerator

class TextFormatter(private val regex: String) {

    private val regexReplaceGenerator = RegexReplaceGenerator()

    private val decoroMaskGenerator = DecoroMaskGenerator()

    private val pcreGeneratorItem = regexReplaceGenerator.regexToRegexReplace(regex)

    private val regexReplaceString = pcreGeneratorItem.regexReplaceString

    private val matrixOfSymbols = pcreGeneratorItem.matrixOfSymbols

    private val placeholderGenerator = PlaceholderGenerator(matrixOfSymbols)

    fun getFormattedText(inputText: String) = inputText.replace(Regex(regex), regexReplaceString)

    fun getPlaceholder() = placeholderGenerator.placeholder

    fun getRegexReplace() = regexReplaceString

    fun mask(editText: EditText) {
        val formatWatcher = decoroMaskGenerator.mask(
            placeholderGenerator.placeholder,
            matrixOfSymbols
        )
        formatWatcher.installOn(editText)
    }
}
