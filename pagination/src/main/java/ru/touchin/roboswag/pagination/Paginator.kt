package ru.touchin.roboswag.pagination

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import ru.touchin.roboswag.mvi_arch.core.Store
import ru.touchin.roboswag.mvi_arch.marker.SideEffect
import ru.touchin.roboswag.mvi_arch.marker.StateChange
import ru.touchin.roboswag.mvi_arch.marker.ViewState

/**
 * Класс, наследник Store, который реализует изменение состояния списка элементов, который поддерживает постраничную загрузку.
 * На выход принимает способ обработки ошибки загрузки страницы, метод загрузки страницы и размер страницы.
 */
class Paginator<Item>(
        private val errorHandleMod: ErrorHandleMod,
        private val loadPage: suspend (Int) -> List<Item>,
        private val pageSize: Int
) : Store<Paginator.Change, Paginator.Effect, Paginator.State>(State.Empty) {

    sealed class Change : StateChange {
        // Вызов pull-to-refresh
        object Refresh : Change()

        // Полная перезагрузка данных при смене внешних параметров: фильтров, сортировок итд
        object Restart : Change()

        // Пользователь скроллит до конца списка. Вызывает загрузку новой страницы
        object LoadMore : Change()

        // Пока не понятно
        object Reset : Change()

        // Загрузка новой страницы прошла успешно
        data class NewPageLoaded<T>(val pageNumber: Int, val items: List<T>) : Change()

        // Загрузка новой страницы прошла с ошибкой
        data class PageLoadError(val error: Throwable) : Change()
    }

    sealed class Effect : SideEffect {
        // Вызов асинхронной загрузки новой страницы
        data class LoadPage(val page: Int = 0) : Effect()
    }

    sealed class State : ViewState {
        // Пустой экран
        object Empty : State()

        // Лоадер в середине экрана
        object EmptyProgress : State()

        // Ошибка при загрузке первой страницы
        data class EmptyError(val error: Throwable) : State()

        // Загружен список элементов
        data class Data<T>(val pageCount: Int = 0, val data: List<T>, val error: Throwable? = null) : State()

        // Показать лоадер при pull-to-refresh
        data class Refresh<T>(val pageCount: Int, val data: List<T>) : State()

        // Загрузка новой страницы
        data class NewPageProgress<T>(val pageCount: Int, val data: List<T>) : State()

        // Весь список загружен. Больше нечего грузить
        data class FullData<T>(val pageCount: Int, val data: List<T>) : State()
    }

    sealed class Error {
        // Ошибка загрузки новой страницы
        object NewPageFailed : Error()

        // Ошибка обновления страницы
        object RefreshFailed : Error()
    }

    // Способы обработки ошибки
    sealed class ErrorHandleMod {
        // Показывать алерт на ошибку
        data class Alert(val showError: (Error) -> Unit) : ErrorHandleMod()

        // Показывать в конце списка элемент списка с ошибкой
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
