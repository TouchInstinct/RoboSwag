package ru.touchin.roboswag.textprocessing.validators

import ru.tinkoff.decoro.slots.Slot

class EngLowerCharsValidator : Slot.SlotValidator {

    companion object {
        private const val chars = "abcdefghijklmnopqrstuvwxyz"

        fun engCharSlot() = Slot(null, EngLowerCharsValidator())
    }

    override fun validate(value: Char): Boolean {
        return chars.contains(value)
    }
}