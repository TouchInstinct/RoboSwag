package ru.touchin.roboswag.textprocessing.validators

import ru.tinkoff.decoro.slots.Slot

class DigitsValidator : Slot.SlotValidator {

    companion object {
        private const val digits = "1234567890"

        fun digitSlot() = Slot(null, DigitsValidator())
    }

    override fun validate(value: Char): Boolean {
        return digits.contains(value)
    }
}