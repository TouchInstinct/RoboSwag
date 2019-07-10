package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command

class IntegerVerifier(maxIntegerNumbers: Int) : Verifier<Int> {

    private val pattern = "\\.(\\d{$maxIntegerNumbers})".toRegex()

    override fun verify(text: String): Command<Int> = if (pattern.matches(text) == true) {
        Command.Success()
    } else {
        Command.Remove(1)
    }

}
