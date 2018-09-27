package ru.touchin.templates.livedata.event

/**
 * Event class that emits from [io.reactivex.Completable].
 */
sealed class CompletableEvent {

    object Loading: CompletableEvent()

    object Completed: CompletableEvent()

    data class Error(val throwable: Throwable): CompletableEvent()

}
