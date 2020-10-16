package ru.touchin.roboswag.mvi_arch.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.touchin.mvi_arch.BuildConfig
import ru.touchin.roboswag.mvi_arch.marker.SideEffect
import ru.touchin.roboswag.mvi_arch.marker.StateChange
import ru.touchin.roboswag.mvi_arch.marker.ViewState
import ru.touchin.roboswag.mvi_arch.mediator.LoggingMediator
import ru.touchin.roboswag.mvi_arch.mediator.MediatorStore

abstract class Store<Change : StateChange, Effect : SideEffect, State : ViewState>(
        initialState: State
) {

    protected val currentState: State
        get() = state.value

    private val storeScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val effects = Channel<Effect?>(Channel.UNLIMITED)
    private val state = MutableStateFlow(initialState)

    private val childStores: MutableList<ChildStore<*, *, *>> = mutableListOf()

    private val mediatorStore = MediatorStore(
            listOfNotNull(
                    LoggingMediator(this::class.simpleName!!).takeIf { BuildConfig.DEBUG }
            )
    )

    init {
        storeScope.launch {
            effects
                    .consumeAsFlow()
                    .filterNotNull()
                    .handleSideEffect()
                    .collect { newChange -> changeState(newChange) }
        }
    }

    fun changeState(change: Change) {
        mediatorStore.onStateChange(change)

        val (newState, newEffect) = reduce(currentState, change)

        if (currentState != newState) {
            state.value = newState
            mediatorStore.onNewState(newState)
        }

        childStores.forEach { childStore ->
            childStore.change(change)
        }

        newEffect?.let {
            effects.offer(it)
            mediatorStore.onEffect(it)
        }

    }

    fun observeState(): Flow<State> = state

    fun onCleared() {
        storeScope.coroutineContext.cancel()
        childStores.forEach(Store<Change, Effect, State>.ChildStore<*, *, *>::onCleared)
    }

    fun State.only(): Pair<State, Effect?> = this to null

    fun Effect.only(): Pair<State, Effect> = currentState to this

    fun same(): Pair<State, Effect?> = currentState.only()

    protected fun <ChildChange : StateChange, ChildEffect : SideEffect, ChildState : ViewState> addChildStore(
            store: Store<ChildChange, ChildEffect, ChildState>,
            changeMapper: (Change) -> ChildChange?,
            stateMapper: (ChildState) -> State
    ) {
        childStores.add(ChildStore(store, changeMapper))

        store
                .observeState()
                .onEach { state.value = stateMapper(it) }
                .launchIn(storeScope)

    }

    protected open fun Flow<Effect>.handleSideEffect(): Flow<Change> = emptyFlow()

    protected abstract fun reduce(currentState: State, change: Change): Pair<State, Effect?>

    private inner class ChildStore<ChildChange : StateChange, ChildEffect : SideEffect, ChildState : ViewState>(
            val store: Store<ChildChange, ChildEffect, ChildState>,
            val changeMapper: (Change) -> ChildChange?
    ) {
        fun onCleared() {
            store.onCleared()
        }

        fun change(change: Change) {
            changeMapper(change)?.let(store::changeState)
        }
    }

}
