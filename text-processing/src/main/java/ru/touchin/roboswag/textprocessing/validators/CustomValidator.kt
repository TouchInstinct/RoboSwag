package ru.touchin.roboswag.textprocessing.validators

import ru.tinkoff.decoro.slots.Slot

class CustomValidator private constructor(
    private val slotSymbols: List<Char>
) : Slot.SlotValidator {

    companion object {
        fun customSlot(slotSymbols: List<Char>) = Slot(null, CustomValidator(slotSymbols))
    }

    override fun validate(value: Char): Boolean {
        return slotSymbols.contains(value)
    }
}