package ru.touchin.converter

import ru.touchin.converter.commands.Command
import ru.touchin.converter.verifiers.Verifier
import ru.touchin.converter.wrap.InputConvertable
import java.lang.IllegalStateException
import java.math.BigDecimal

class VerifierController(
        private val inputConvertable: InputConvertable,
        private val verifiers: List<Verifier<BigDecimal>>
) {

    fun verifyAll(inputString: String): Boolean {
        return verifiers.all { verifier ->
            val result = verifier.verify(inputString)
            execute(result)

            result is Command.Success
        }
    }

    fun execute(command: Command<BigDecimal>) {
        when (command) {
            is Command.Fallback -> {
                with(inputConvertable) {
                    setNumber(storedValue)
                }
            }
            is Command.Set -> {
                inputConvertable.setNumber(command.data)
            }
            is Command.Remove -> {
                with(inputConvertable) {
                    val changedString = format(storedValue).dropLast(command.data.toInt())
                    inputConvertable.setNumber(format(changedString))
                }
            }
            is Command.Success -> { // do nothing
            }
            is Command.Error -> {
                throw IllegalStateException("Can't resolve error")
            }
        }
    }

}
