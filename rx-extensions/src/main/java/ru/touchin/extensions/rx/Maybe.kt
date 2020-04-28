package ru.touchin.extensions.rx

import io.reactivex.Completable
import io.reactivex.Maybe
import ru.touchin.extensions.rx.utils.StringConstants
import ru.touchin.roboswag.core.utils.Optional
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

fun <T> Maybe<T>.emitAfter(other: Completable): Maybe<T> = this.flatMap { value ->
    other.andThen(Maybe.just(value))
}

fun <T> Maybe<Optional<T>>.unwrapOrError(
        errorMessage: String = StringConstants.OPTIONAL_UNWRAPPING_ERROR_MESSAGE
): Maybe<T> = this.flatMap { wrapper ->
    wrapper.get()
            ?.let { Maybe.just(it) }
            ?: Maybe.error(ShouldNotHappenException(errorMessage))
}
