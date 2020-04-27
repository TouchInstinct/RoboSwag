package ru.touchin.extensions.rx

import io.reactivex.Completable
import io.reactivex.Flowable

fun <T> Flowable<T>.emitAfter(other: Completable): Flowable<T> = this.flatMap { value ->
    other.andThen(Flowable.just(value))
}
