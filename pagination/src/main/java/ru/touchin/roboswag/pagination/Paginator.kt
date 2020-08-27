package ru.touchin.roboswag.pagination

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import ru.touchin.roboswag.mvi_arch.core.Store
import ru.touchin.roboswag.mvi_arch.marker.SideEffect
import ru.touchin.roboswag.mvi_arch.marker.StateChange
import ru.touchin.roboswag.mvi_arch.marker.ViewState

class Paginator<Item>(
        private val errorHandleMod: ErrorHandleMod,
        private val loadPage: suspend (Int) -> List<Item>
) : Store<Paginator.Change, Paginator.Effect, Paginator.State>(State.Empty) {

    sealed class Change : StateChange {
        object Refresh : Change()
        object Restart : Change()
        object LoadMore : Change()
        object Reset : Change()
        data class NewPageLoaded<T>(val pageNumber: Int, val items: List<T>) : Change()
        data class PageLoadError(val error: Throwable) : Change()
    }

    sealed class Effect : SideEffect {
        data class LoadPage(val page: Int = 0) : Effect()
    }

    sealed class State : ViewState {
        object Empty : State()
        object EmptyProgress : State()
        data class EmptyError(val error: Throwable) : State()
        data class Data<T>(val pageCount: Int = 0, val data: List<T>, val error: Throwable? = null) : State()
        data class Refresh<T>(val pageCount: Int, val data: List<T>) : State()
        data class NewPageProgress<T>(val pageCount: Int, val data: List<T>) : State()
        data class FullData<T>(val pageCount: Int, val data: List<T>) : State()
    }

    sealed class Error {
        object NewPageFailed : Error()
        object RefreshFailed : Error()
    }

    sealed class ErrorHandleMod {
        data class Alert(val showError: (Error) -> Unit) : ErrorHandleMod()
        object ErrorItem : ErrorHandleMod()
    }

    override fun reduce(currentState: State, change: Change): Pair<State, Effect?> = when (change) {
        Change.Refresh -> {
            when (currentState) {
                State.Empty -> State.EmptyProgress
                is State.EmptyError -> State.EmptyProgress
                is State.Data<*> -> State.Refresh(currentState.pageCount, currentState.data)
                is State.NewPageProgress<*> -> State.Refresh(currentState.pageCount, currentState.data)
                is State.FullData<*> -> State.Refresh(currentState.pageCount, currentState.data)
                else -> currentState
            } to Effect.LoadPage()
        }
        Change.Restart -> {
            State.EmptyProgress to Effect.LoadPage()
        }
        Change.LoadMore -> {
            when (currentState) {
                is State.Data<*> -> {
                    State.NewPageProgress(currentState.pageCount, currentState.data) to Effect.LoadPage(currentState.pageCount + 1)
                }
                else -> currentState.only()
            }
        }
        Change.Reset -> {
            State.Empty.only()
        }
        is Change.NewPageLoaded<*> -> {
            val items = change.items
            when (currentState) {
                is State.EmptyProgress -> {
                    if (items.isEmpty()) {
                        State.Empty
                    } else {
                        State.Data(0, items)
                    }
                }
                is State.Refresh<*> -> {
                    if (items.isEmpty()) {
                        State.Empty
                    } else {
                        State.Data(0, items)
                    }
                }
                is State.NewPageProgress<*> -> {
                    if (items.isEmpty()) {
                        State.FullData(currentState.pageCount, currentState.data)
                    } else {
                        State.Data(currentState.pageCount + 1, currentState.data + items)
                    }
                }
                else -> currentState
            }.only()
        }
        is Change.PageLoadError -> {
            when (currentState) {
                is State.EmptyProgress -> State.EmptyError(change.error)
                is State.Refresh<*> -> {
                    when (errorHandleMod) {
                        is ErrorHandleMod.Alert -> {
                            errorHandleMod.showError(Error.RefreshFailed)
                            State.Data(currentState.pageCount, currentState.data)
                        }
                        is ErrorHandleMod.ErrorItem -> {
                            State.Data(
                                    pageCount = currentState.pageCount,
                                    data = currentState.data,
                                    error = change.error
                            )
                        }
                    }
                }
                is State.NewPageProgress<*> -> {
                    when (errorHandleMod) {
                        is ErrorHandleMod.Alert -> {
                            errorHandleMod.showError(Error.NewPageFailed)
                            State.Data(currentState.pageCount, currentState.data)
                        }
                        is ErrorHandleMod.ErrorItem -> {
                            State.Data(
                                    pageCount = currentState.pageCount,
                                    data = currentState.data,
                                    error = change.error
                            )
                        }
                    }
                }
                else -> currentState
            }.only()
        }
    }

    override fun Flow<Effect>.handleSideEffect(): Flow<Change> = flatMapLatest { effect ->
        flow {
            when (effect) {
                is Effect.LoadPage -> {
                    try {
                        val items = loadPage(effect.page)
                        emit(Change.NewPageLoaded(effect.page, items))
                    } catch (e: Exception) {
                        emit(Change.PageLoadError(e))
                    }

                }
            }
        }
    }

}
