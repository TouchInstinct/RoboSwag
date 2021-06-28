package ru.touchin.roboswag.pagination

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import ru.touchin.roboswag.mvi_arch.core.Store
import ru.touchin.roboswag.mvi_arch.marker.SideEffect
import ru.touchin.roboswag.mvi_arch.marker.StateChange
import ru.touchin.roboswag.mvi_arch.marker.ViewState

/**
 * Class for state changing of list, support page-loading, implements [Store]
 *
 * @param errorHandleMod - error handling method (show Alert or ErrorItem);
 * @param loadPage - method for loading data;
 * @param pageSize - size of one page
 */

class Paginator<Item>(
        private val errorHandleMod: ErrorHandleMod,
        private val loadPage: suspend (Int) -> List<Item>,
        private val pageSize: Int
) : Store<Paginator.Change, Paginator.Effect, Paginator.State>(State.Empty) {

    sealed class Change : StateChange {
        // call pull-to-refresh
        object Refresh : Change()

        // Full reloading data when changing external parameters: filters, sorts, etc.
        object Restart : Change()

        // User scrolls to the end of the list. Calls loading of new page
        object LoadMore : Change()

        // Clearing the list and loaded elements
        object Reset : Change()

        // Loading the new page was successful
        data class NewPageLoaded<T>(val pageNumber: Int, val items: List<T>) : Change()

        // Loading the new page ended with error
        data class PageLoadError(val error: Throwable) : Change()
    }

    sealed class Effect : SideEffect {
        // Call asynchronous load of a new page
        data class LoadPage(val page: Int = 0) : Effect()
    }

    sealed class State : ViewState {
        // Empty screen
        object Empty : State()

        // Loader in the middle of screen
        object EmptyProgress : State()

        // Error while loading first page
        data class EmptyError(val error: Throwable) : State()

        // Loaded list of elements
        data class Data<T>(val pageCount: Int = 0, val data: List<T>, val error: Throwable? = null) : State()

        // Show loader on pull-to-refresh
        data class Refresh<T>(val pageCount: Int, val data: List<T>) : State()

        // Loading new page
        data class NewPageProgress<T>(val pageCount: Int, val data: List<T>) : State()

        // The whole list is loaded. Nothing to load more.
        data class FullData<T>(val pageCount: Int, val data: List<T>) : State()
    }

    sealed class Error {

        object NewPageLoadingFailed : Error()

        object RefreshPageFailed : Error()

    }

    // How to react to an error
    sealed class ErrorHandleMod {
        // Show alert for error
        data class Alert(val showError: (Error) -> Unit) : ErrorHandleMod()

        // Show in the end of list an element of list with error
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
                    when {
                        items.isEmpty() -> State.Empty
                        items.size < pageSize -> State.FullData(0, items)
                        else -> State.Data(0, items)
                    }
                }
                is State.Refresh<*> -> {
                    when {
                        items.isEmpty() -> State.Empty
                        items.size < pageSize -> State.FullData(0, items)
                        else -> State.Data(0, items)
                    }
                }
                is State.NewPageProgress<*> -> {
                    if (items.size < pageSize) {
                        State.FullData(currentState.pageCount, currentState.data + items)
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
                            errorHandleMod.showError(Error.RefreshPageFailed)
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
                            errorHandleMod.showError(Error.NewPageLoadingFailed)
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
