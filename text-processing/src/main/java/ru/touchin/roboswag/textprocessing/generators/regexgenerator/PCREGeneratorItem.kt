package ru.touchin.roboswag.textprocessing.generators.regexgenerator

import ru.touchin.roboswag.textprocessing.generators.Matrix

class PCREGeneratorItem(
    val regexReplaceString: String,
    val matrixOfSymbols: Matrix<Char>
)
