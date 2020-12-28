package ru.touchin.roboswag.mvi_arch.mediator

import ru.touchin.roboswag.mvi_arch.marker.SideEffect
import ru.touchin.roboswag.mvi_arch.marker.StateChange
import ru.touchin.roboswag.mvi_arch.marker.ViewAction
import ru.touchin.roboswag.mvi_arch.marker.ViewState

interface Mediator {

    fun onEffect(effect: SideEffect)

    fun onAction(action: ViewAction)

    fun onNewState(state: ViewState)

    fun onStateChange(change: StateChange)

}
