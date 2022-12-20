package ru.touchin.roboswag.textprocessing.generators

class PlaceholderGenerator(
    listForPlaceholder: List<List<Char>>
) {

    private var placeholder: String = ""

    init {
        val hashMap = hashMapOf<List<Char>, Int>()
        for (listOfChar in listForPlaceholder) {
            hashMap[listOfChar] = 0
        }
        for (listOfChar in listForPlaceholder) {
            if (listOfChar.isEmpty()) continue
            /** Если элемент без повторений **/
            if (listOfChar.size == 1) {
                placeholder += listOfChar[0]
                continue
            }
            hashMap[listOfChar]?.let { j ->
                var i = j
                if (listOfChar.size <= i) i = 0
                placeholder += listOfChar[i]
                i++
                hashMap[listOfChar] = i
            }
        }
    }

    fun getPlaceholder() = placeholder
}