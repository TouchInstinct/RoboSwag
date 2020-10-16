package ru.touchin.extensions.rx

import io.reactivex.Completable
import io.reactivex.Single
import ru.touchin.extensions.rx.utils.StringConstants
import ru.touchin.roboswag.core.utils.Optional
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

fun <T> Single<T>.emitAfter(other: Completable): Single<T> = this.flatMap { value ->
    other.andThen(Single.just(value))
}

fun <T> Single<Optional<T>>.unwrapOrError(
        errorMessage: String = StringConstants.OPTIONAL_UNWRAPPING_ERROR_MESSAGE
): Single<T> = this.flatMap { wrapper ->
    wrapper.get()
            ?.let { Single.just(it) }
            ?: Single.error(ShouldNotHappenException(errorMessage))
}
