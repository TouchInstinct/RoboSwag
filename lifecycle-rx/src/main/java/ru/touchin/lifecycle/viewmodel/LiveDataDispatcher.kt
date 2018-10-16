package ru.touchin.lifecycle.viewmodel

import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import ru.touchin.lifecycle.event.ContentEvent
import ru.touchin.lifecycle.event.Event

interface LiveDataDispatcher {

    fun <T> Flowable<out T>.dispatchTo(liveData: MutableLiveData<ContentEvent<T>>): Disposable

    fun <T> Observable<out T>.dispatchTo(liveData: MutableLiveData<ContentEvent<T>>): Disposable

    fun <T> Single<out T>.dispatchTo(liveData: MutableLiveData<ContentEvent<T>>): Disposable

    fun <T> Maybe<out T>.dispatchTo(liveData: MutableLiveData<ContentEvent<T>>): Disposable

    fun Completable.dispatchTo(liveData: MutableLiveData<Event>): Disposable

}
