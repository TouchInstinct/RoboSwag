package ru.touchin.lifecycle.viewmodel

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import ru.touchin.lifecycle.event.ContentEvent
import ru.touchin.lifecycle.event.Event

class BaseLiveDataDispatcher(private val destroyable: BaseDestroyable = BaseDestroyable()) : LiveDataDispatcher, Destroyable by destroyable {

    override fun <T> Flowable<out T>.dispatchTo(liveData: MutableLiveData<ContentEvent<T>>): Disposable {
        liveData.setLoadingEvent()
        return untilDestroy(
                { data -> liveData.value = ContentEvent.Success(data) },
                { throwable -> liveData.value = ContentEvent.Error(throwable, liveData.value?.data) },
                { liveData.value = ContentEvent.Complete(liveData.value?.data) })
    }

    override fun <T> Observable<out T>.dispatchTo(liveData: MutableLiveData<ContentEvent<T>>): Disposable {
        liveData.setLoadingEvent()
        return untilDestroy(
                { data -> liveData.value = ContentEvent.Success(data) },
                { throwable -> liveData.value = ContentEvent.Error(throwable, liveData.value?.data) },
                { liveData.value = ContentEvent.Complete(liveData.value?.data) })
    }

    override fun <T> Single<out T>.dispatchTo(liveData: MutableLiveData<ContentEvent<T>>): Disposable {
        liveData.setLoadingEvent()
        return untilDestroy(
                { data -> liveData.value = ContentEvent.Success(data) },
                { throwable -> liveData.value = ContentEvent.Error(throwable, liveData.value?.data) })
    }

    override fun <T> Maybe<out T>.dispatchTo(liveData: MutableLiveData<ContentEvent<T>>): Disposable {
        liveData.setLoadingEvent()
        return untilDestroy(
                { data -> liveData.value = ContentEvent.Success(data) },
                { throwable -> liveData.value = ContentEvent.Error(throwable, liveData.value?.data) },
                { liveData.value = ContentEvent.Complete(liveData.value?.data) })
    }

    override fun Completable.dispatchTo(liveData: MutableLiveData<Event>): Disposable {
        liveData.setLoadingEvent()
        return untilDestroy(
                { liveData.value = Event.Complete },
                { throwable -> liveData.value = Event.Error(throwable) })
    }

    private fun <T> MutableLiveData<ContentEvent<T>>.setLoadingEvent() {
        val loadingContent = ContentEvent.Loading(this.value?.data)
        if (Looper.getMainLooper().thread == Thread.currentThread()) {
            this.value = loadingContent
        } else {
            this.postValue(loadingContent)
        }
    }

    @JvmName("setCompletableLoadingEvent")
    private fun MutableLiveData<Event>.setLoadingEvent() {
        val loadingContent = Event.Loading
        if (Looper.getMainLooper().thread == Thread.currentThread()) {
            this.value = loadingContent
        } else {
            this.postValue(loadingContent)
        }
    }

}
