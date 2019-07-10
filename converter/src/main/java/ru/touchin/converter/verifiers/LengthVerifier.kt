package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command

class LengthVerifier(val maxLength: Int) : Verifier<Int> {

    override fun verify(text: String): Command<Int> = if (text.length <= maxLength) {
        Command.Success()
    } else {
        Command.Remove(1)
    }

}
