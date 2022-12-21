package ru.touchin.roboswag.textprocessing.generators

import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import ru.touchin.roboswag.textprocessing.validators.CustomValidator

class DecoroMaskGenerator {

    /** Генерация маски и слотов на основе возможных символов для placeholder,
     * если возможный символ всего один, то символ хардкодится в слот
     * **/
    fun mask(placeholder: String, matrixOfSymbols: Matrix<Char>): MaskFormatWatcher {
        val slots = mutableListOf<Slot>()
        for (i in placeholder.indices) {
            slots.add(
                if (matrixOfSymbols[i].size == 1) {
                    PredefinedSlots.hardcodedSlot(placeholder[i])
                } else {
                    CustomValidator.customSlot(matrixOfSymbols[i])
                }
            )
        }
        return MaskFormatWatcher(MaskImpl.createTerminated(slots.toTypedArray()))
    }
}