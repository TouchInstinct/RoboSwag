package ru.touchin.extensions.rx

import io.reactivex.Completable
import io.reactivex.Maybe

fun <T> Maybe<T>.emitAfter(other: Completable): Maybe<T> = this.flatMap { value ->
    other.andThen(Maybe.just(value))
}
