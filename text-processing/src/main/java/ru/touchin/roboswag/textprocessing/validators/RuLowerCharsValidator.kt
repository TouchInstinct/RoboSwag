package ru.touchin.roboswag.textprocessing.validators

import ru.tinkoff.decoro.slots.Slot

class RuLowerCharsValidator : Slot.SlotValidator {

    companion object {
        private const val chars = "абвгдежзийклмнопрстуфчцэюяъьыш"

        fun ruCharSlot() = Slot(null, RuLowerCharsValidator())
    }

    override fun validate(value: Char): Boolean {
        return chars.contains(value)
    }
}