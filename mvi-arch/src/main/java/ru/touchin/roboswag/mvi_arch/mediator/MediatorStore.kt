package ru.touchin.roboswag.mvi_arch.mediator

import ru.touchin.roboswag.mvi_arch.marker.SideEffect
import ru.touchin.roboswag.mvi_arch.marker.StateChange
import ru.touchin.roboswag.mvi_arch.marker.ViewAction
import ru.touchin.roboswag.mvi_arch.marker.ViewState

class MediatorStore(private val mediators: List<Mediator>) : Mediator {

    override fun onAction(action: ViewAction) {
        mediators.forEach { it.onAction(action) }
    }

    override fun onEffect(effect: SideEffect) {
        mediators.forEach { it.onEffect(effect) }
    }

    override fun onNewState(state: ViewState) {
        mediators.forEach { it.onNewState(state) }
    }

    override fun onStateChange(change: StateChange) {
        mediators.forEach { it.onStateChange(change) }
    }
}
