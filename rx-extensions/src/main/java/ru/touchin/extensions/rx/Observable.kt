package ru.touchin.extensions.rx

import io.reactivex.Completable
import io.reactivex.Observable

fun <T> Observable<T>.emitAfter(other: Completable): Observable<T> = this.flatMap { value ->
    other.andThen(Observable.just(value))
}
