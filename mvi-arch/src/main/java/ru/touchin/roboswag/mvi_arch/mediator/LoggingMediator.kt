package ru.touchin.roboswag.mvi_arch.mediator

import com.tylerthrailkill.helpers.prettyprint.pp
import ru.touchin.roboswag.core.log.Lc
import ru.touchin.roboswag.mvi_arch.marker.SideEffect
import ru.touchin.roboswag.mvi_arch.marker.StateChange
import ru.touchin.roboswag.mvi_arch.marker.ViewAction
import ru.touchin.roboswag.mvi_arch.marker.ViewState

class LoggingMediator(private val objectName: String) : Mediator {
    override fun onEffect(effect: SideEffect) {
        logObject(
                prefix = "New Effect:\n",
                obj = effect
        )
    }

    override fun onAction(action: ViewAction) {
        logObject(
                prefix = "New Action:\n",
                obj = action
        )
    }

    override fun onNewState(state: ViewState) {
        logObject(
                prefix = "New State:\n",
                obj = state
        )
    }

    override fun onStateChange(change: StateChange) {
        logObject(
                prefix = "New State change:\n",
                obj = change
        )
    }

    private fun <T> logObject(
            prefix: String,
            obj: T
    ) {
        val builder = StringBuilder()
        pp(obj = obj, writeTo = builder)

        val prettyOutput = builder.toString()
        Lc.d("$objectName: $prefix$prettyOutput\n")
    }
}
