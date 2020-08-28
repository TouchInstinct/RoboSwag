package ru.touchin.roboswag.extensions.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.touchin.roboswag.extensions.rx.utils.StringConstants
import ru.touchin.roboswag.utils.core.Optional
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

fun <T> Flowable<T>.emitAfter(other: Completable): Flowable<T> = this.flatMap { value ->
    other.andThen(Flowable.just(value))
}

fun <T> Flowable<Optional<T>>.unwrapOrError(
        errorMessage: String = StringConstants.OPTIONAL_UNWRAPPING_ERROR_MESSAGE
): Flowable<T> = this.flatMap { wrapper ->
    wrapper.get()
            ?.let { Flowable.just(it) }
            ?: Flowable.error(ShouldNotHappenException(errorMessage))
}

fun <T> Flowable<Optional<T>>.unwrapOrFilter(): Flowable<T> = this
        .filter { it.get() != null }
        .map { it.get() }
