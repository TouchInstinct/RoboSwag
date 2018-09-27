package ru.touchin.livedata.dispatcher

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import ru.touchin.templates.livedata.event.CompletableEvent
import ru.touchin.templates.livedata.event.MaybeEvent
import ru.touchin.templates.livedata.event.ObservableEvent
import ru.touchin.templates.livedata.event.SingleEvent

interface LiveDataDispatcher {

    fun <T> Flowable<out T>.dispatchTo(liveData: MutableLiveData<ObservableEvent<T>>): Disposable

    fun <T> Observable<out T>.dispatchTo(liveData: MutableLiveData<ObservableEvent<T>>): Disposable

    fun <T> Single<out T>.dispatchTo(liveData: MutableLiveData<SingleEvent<T>>): Disposable

    fun Completable.dispatchTo(liveData: MutableLiveData<CompletableEvent>): Disposable

    fun <T> Maybe<out T>.dispatchTo(liveData: MutableLiveData<MaybeEvent<T>>): Disposable

}
