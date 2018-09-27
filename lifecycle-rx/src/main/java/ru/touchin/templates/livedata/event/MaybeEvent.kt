package ru.touchin.templates.livedata.event

/**
 * Event class that emits from [io.reactivex.Maybe].
 */
sealed class MaybeEvent<out T>(open val data: T?) {

    class Loading<out T>(data: T?): MaybeEvent<T>(data)

    class Success<out T>(override val data: T): MaybeEvent<T>(data)

    class Error<out T>(val throwable: Throwable, data: T?): MaybeEvent<T>(data)

    class Completed<out T>(data: T?): MaybeEvent<T>(data)

}
