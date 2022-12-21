package ru.touchin.roboswag.textprocessing.generators

class PlaceholderGenerator(matrixOfSymbols: Matrix<Char>) {

    val placeholder = generatePlaceholder(matrixOfSymbols)

    private fun generatePlaceholder(matrixOfSymbols: Matrix<Char>): String {
        val placeholderStringBuilder = StringBuilder()
        val indexes = hashMapOf<List<Char>, Int>()
        for (listOfSymbols in matrixOfSymbols) {
            indexes[listOfSymbols] = 0
        }
        for (listOfSymbols in matrixOfSymbols) {
            if (listOfSymbols.isEmpty()) continue
            /** Если элемент без повторений **/
            if (listOfSymbols.size == 1) {
                placeholderStringBuilder.append(listOfSymbols[0])
                continue
            }
            indexes[listOfSymbols]?.let {
                var index = it
                if (listOfSymbols.size <= index) index = 0
                placeholderStringBuilder.append(listOfSymbols[index])
                index++
                indexes[listOfSymbols] = index
            }
        }
        return placeholderStringBuilder.toString()
    }
}
