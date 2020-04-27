package ru.touchin.extensions.rx

import io.reactivex.Completable
import io.reactivex.Single

fun <T> Single<T>.emitAfter(other: Completable): Single<T> = this.flatMap { value ->
    other.andThen(Single.just(value))
}
