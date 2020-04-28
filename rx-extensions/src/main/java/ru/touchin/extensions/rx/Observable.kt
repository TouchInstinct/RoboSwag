package ru.touchin.extensions.rx

import io.reactivex.Completable
import io.reactivex.Observable
import ru.touchin.extensions.rx.utils.StringConstants
import ru.touchin.roboswag.core.utils.Optional
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

fun <T> Observable<T>.emitAfter(other: Completable): Observable<T> = this.flatMap { value ->
    other.andThen(Observable.just(value))
}

fun <T> Observable<Optional<T>>.unwrapOrError(
        errorMessage: String = StringConstants.OPTIONAL_UNWRAPPING_ERROR_MESSAGE
): Observable<T> = this.flatMap { wrapper ->
    wrapper.get()
            ?.let { Observable.just(it) }
            ?: Observable.error(ShouldNotHappenException(errorMessage))
}

fun <T> Observable<Optional<T>>.unwrapOrSkip(): Observable<T> = this
        .filter { it.get() != null }
        .map { it.get() }
