package ru.touchin.livedata.destroyable

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Oksana Pokrovskaya on 7/03/18.
 * Simple implementation of [Destroyable]. Could be used to not implement interface but use such object inside.
 */
open class BaseDestroyable : Destroyable {

    private val subscriptions = CompositeDisposable()

    override fun clearSubscriptions() = subscriptions.clear()

    /**
     * Call it on parent's onDestroy method.
     */
    fun onDestroy() = subscriptions.dispose()

    override fun <T> Flowable<T>.untilDestroy(
            onNext: (T) -> Unit,
            onError: (Throwable) -> Unit,
            onComplete: () -> Unit
    ): Disposable = observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext, onError, onComplete)
            .also { subscriptions.add(it) }

    override fun <T> Observable<T>.untilDestroy(
            onNext: (T) -> Unit,
            onError: (Throwable) -> Unit,
            onComplete: () -> Unit
    ): Disposable = observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext, onError, onComplete)
            .also { subscriptions.add(it) }

    override fun <T> Single<T>.untilDestroy(
            onSuccess: (T) -> Unit,
            onError: (Throwable) -> Unit
    ): Disposable = observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
            .also { subscriptions.add(it) }

    override fun Completable.untilDestroy(
            onComplete: () -> Unit,
            onError: (Throwable) -> Unit
    ): Disposable = observeOn(AndroidSchedulers.mainThread())
            .subscribe(onComplete, onError)
            .also { subscriptions.add(it) }

    override fun <T> Maybe<T>.untilDestroy(
            onSuccess: (T) -> Unit,
            onError: (Throwable) -> Unit,
            onComplete: () -> Unit
    ): Disposable = observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError, onComplete)
            .also { subscriptions.add(it) }

}
