package ru.touchin.templates.livedata.event

/**
 * Event class that emits from [io.reactivex.Observable].
 */
sealed class ObservableEvent<out T>(open val data: T?) {

    class Loading<out T>(data: T? = null): ObservableEvent<T>(data)

    class Success<out T>(override val data: T): ObservableEvent<T>(data)

    class Error<out T>(val throwable: Throwable, data: T? = null): ObservableEvent<T>(data)

    class Completed<out T>(data: T? = null): ObservableEvent<T>(data)

}
