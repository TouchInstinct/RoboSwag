package ru.touchin.roboswag.textprocessing.generators

class PlaceholderGenerator(matrixOfSymbols: Matrix<Char>) {

    private var placeholder: String = ""

    init {
        val indexes = hashMapOf<List<Char>, Int>()
        for (listOfSymbols in matrixOfSymbols) {
            indexes[listOfSymbols] = 0
        }
        for (listOfSymbols in matrixOfSymbols) {
            if (listOfSymbols.isEmpty()) continue
            /** Если элемент без повторений **/
            if (listOfSymbols.size == 1) {
                placeholder += listOfSymbols[0]
                continue
            }
            indexes[listOfSymbols]?.let {
                var index = it
                if (listOfSymbols.size <= index) index = 0
                placeholder += listOfSymbols[index]
                index++
                indexes[listOfSymbols] = index
            }
        }
    }

    fun getPlaceholder() = placeholder
}