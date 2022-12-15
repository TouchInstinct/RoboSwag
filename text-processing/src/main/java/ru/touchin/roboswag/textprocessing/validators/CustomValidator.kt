package ru.touchin.roboswag.textprocessing.validators

import ru.tinkoff.decoro.slots.Slot

class CustomValidator private constructor(
    private val slotItems: String
) : Slot.SlotValidator {

    companion object {
        fun customSlot(slotItems: String) = Slot(null, CustomValidator(slotItems))
    }

    override fun validate(value: Char): Boolean {
        return slotItems.contains(value)
    }
}