package ru.touchin.livedata.dispatcher

import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import ru.touchin.livedata.destroyable.BaseDestroyable
import ru.touchin.livedata.destroyable.Destroyable
import ru.touchin.templates.livedata.event.CompletableEvent
import ru.touchin.templates.livedata.event.MaybeEvent
import ru.touchin.templates.livedata.event.ObservableEvent
import ru.touchin.templates.livedata.event.SingleEvent

class BaseLiveDataDispatcher(private val destroyable: BaseDestroyable = BaseDestroyable()) : LiveDataDispatcher, Destroyable by destroyable {

    override fun <T> Flowable<out T>.dispatchTo(liveData: MutableLiveData<ObservableEvent<T>>): Disposable {
        liveData.value = ObservableEvent.Loading(liveData.value?.data)
        return untilDestroy(
                { data -> liveData.value = ObservableEvent.Success(data) },
                { throwable -> liveData.value = ObservableEvent.Error(throwable, liveData.value?.data) },
                { liveData.value = ObservableEvent.Completed(liveData.value?.data) })
    }

    override fun <T> Observable<out T>.dispatchTo(liveData: MutableLiveData<ObservableEvent<T>>): Disposable {
        liveData.value = ObservableEvent.Loading(liveData.value?.data)
        return untilDestroy(
                { data -> liveData.value = ObservableEvent.Success(data) },
                { throwable -> liveData.value = ObservableEvent.Error(throwable, liveData.value?.data) },
                { liveData.value = ObservableEvent.Completed(liveData.value?.data) })
    }

    override fun <T> Single<out T>.dispatchTo(liveData: MutableLiveData<SingleEvent<T>>): Disposable {
        liveData.value = SingleEvent.Loading(liveData.value?.data)
        return untilDestroy(
                { data -> liveData.value = SingleEvent.Success(data) },
                { throwable -> liveData.value = SingleEvent.Error(throwable, liveData.value?.data) })
    }

    override fun Completable.dispatchTo(liveData: MutableLiveData<CompletableEvent>): Disposable {
        liveData.value = CompletableEvent.Loading
        return untilDestroy(
                { liveData.value = CompletableEvent.Completed },
                { throwable -> liveData.value = CompletableEvent.Error(throwable) })
    }

    override fun <T> Maybe<out T>.dispatchTo(liveData: MutableLiveData<MaybeEvent<T>>): Disposable {
        liveData.value = MaybeEvent.Loading(liveData.value?.data)
        return untilDestroy(
                { data -> liveData.value = MaybeEvent.Success(data) },
                { throwable -> liveData.value = MaybeEvent.Error(throwable, liveData.value?.data) },
                { liveData.value = MaybeEvent.Completed(liveData.value?.data) })
    }

}
