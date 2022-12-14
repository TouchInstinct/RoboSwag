package ru.touchin.roboswag.textprocessing.validators

import ru.tinkoff.decoro.slots.Slot

class EngUpperCharsValidator : Slot.SlotValidator {

    companion object {
        private const val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        fun engCharSlot() = Slot(null, EngUpperCharsValidator())
    }

    override fun validate(value: Char): Boolean {
        return chars.contains(value)
    }
}