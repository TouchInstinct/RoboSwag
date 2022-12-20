package ru.touchin.roboswag.textprocessing.validators

import ru.tinkoff.decoro.slots.Slot

class CustomValidator private constructor(
    private val slotItems: List<Char>
) : Slot.SlotValidator {

    companion object {
        fun customSlot(slotItems: List<Char>) = Slot(null, CustomValidator(slotItems))
    }

    override fun validate(value: Char): Boolean {
        return slotItems.contains(value)
    }
}