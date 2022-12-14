package ru.touchin.roboswag.textprocessing.validators

import ru.tinkoff.decoro.slots.Slot

class RuUpperCharsValidator : Slot.SlotValidator {

    companion object {
        private const val chars = "АБВГДЕЖЗИЙКЛМНОПРСТУФЧЦЧЭЮЯЪЬШ"

        fun ruCharSlot() = Slot(null, RuUpperCharsValidator())
    }

    override fun validate(value: Char): Boolean {
        return chars.contains(value)
    }
}