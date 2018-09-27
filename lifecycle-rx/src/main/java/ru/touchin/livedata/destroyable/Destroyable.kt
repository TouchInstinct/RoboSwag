package ru.touchin.livedata.destroyable

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.internal.functions.Functions
import ru.touchin.roboswag.core.log.Lc
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

/**
 * Created by Oksana Pokrovskaya on 7/03/18.
 * Interface that should be implemented by ([android.arch.lifecycle.ViewModel] etc.)
 * to not manually manage subscriptions.
 * Use [.untilDestroy] method to subscribe to observable where you want and unsubscribe onDestroy.
 */
interface Destroyable {

    companion object {
        private fun getActionThrowableForAssertion(codePoint: String, method: String = "untilDestroy"): (Throwable) -> Unit = { throwable ->
            Lc.assertion(ShouldNotHappenException("Unexpected error on $method at $codePoint", throwable))
        }
    }

    /**
     * Removes all subscriptions
     */
    fun clearSubscriptions()

    /**
     * Method should be used to guarantee that observable won't be subscribed after onDestroy.
     * It is automatically subscribing to the observable and calls onNextAction and onErrorAction on observable events.
     * Don't forget to process errors if observable can emit them.
     *
     * @param onNext        Action which will raise on every [io.reactivex.Emitter.onNext] item;
     * @param onError       Action which will raise on every [io.reactivex.Emitter.onError] throwable;
     * @param onComplete    Action which will raise on every [io.reactivex.Emitter.onComplete] item;
     * @return [Disposable] which is wrapping source observable to unsubscribe from it onDestroy.
     */
    fun <T> Flowable<T>.untilDestroy(
            onNext: (T) -> Unit = Functions.emptyConsumer<T>()::accept,
            onError: (Throwable) -> Unit = getActionThrowableForAssertion(Lc.getCodePoint(this, 2)),
            onComplete: () -> Unit = Functions.EMPTY_ACTION::run
    ): Disposable

    /**
     * Method should be used to guarantee that observable won't be subscribed after onDestroy.
     * It is automatically subscribing to the observable and calls onNextAction and onErrorAction on observable events.
     * Don't forget to process errors if observable can emit them.
     *
     * @param onNext        Action which will raise on every [io.reactivex.Emitter.onNext] item;
     * @param onError       Action which will raise on every [io.reactivex.Emitter.onError] throwable;
     * @param onComplete    Action which will raise on every [io.reactivex.Emitter.onComplete] item;
     * @return [Disposable] which is wrapping source observable to unsubscribe from it onDestroy.
     */
    fun <T> Observable<T>.untilDestroy(
            onNext: (T) -> Unit = Functions.emptyConsumer<T>()::accept,
            onError: (Throwable) -> Unit = getActionThrowableForAssertion(Lc.getCodePoint(this, 2)),
            onComplete: () -> Unit = Functions.EMPTY_ACTION::run
    ): Disposable

    /**
     * Method should be used to guarantee that single won't be subscribed after onDestroy.
     * It is automatically subscribing to the single and calls onSuccessAction and onErrorAction on single events.
     * Don't forget to process errors if single can emit them.
     *
     * @param onSuccess     Action which will raise on every [io.reactivex.SingleEmitter.onSuccess] item;
     * @param onError       Action which will raise on every [io.reactivex.SingleEmitter.onError] throwable;
     * @return [Disposable] which is wrapping source single to unsubscribe from it onDestroy.
     */
    fun <T> Single<T>.untilDestroy(
            onSuccess: (T) -> Unit = Functions.emptyConsumer<T>()::accept,
            onError: (Throwable) -> Unit = getActionThrowableForAssertion(Lc.getCodePoint(this, 2))
    ): Disposable

    /**
     * Method should be used to guarantee that completable won't be subscribed after onDestroy.
     * It is automatically subscribing to the completable and calls onCompletedAction and onErrorAction on completable events.
     * Don't forget to process errors if completable can emit them.
     *
     * @param onComplete    Action which will raise on every [io.reactivex.CompletableEmitter.onComplete] item;
     * @param onError       Action which will raise on every [io.reactivex.CompletableEmitter.onError] throwable;
     * @return [Disposable] which is wrapping source completable to unsubscribe from it onDestroy.
     */
    fun Completable.untilDestroy(
            onComplete: () -> Unit = Functions.EMPTY_ACTION::run,
            onError: (Throwable) -> Unit = getActionThrowableForAssertion(Lc.getCodePoint(this, 2))
    ): Disposable

    /**
     * Method should be used to guarantee that maybe won't be subscribed after onDestroy.
     * It is automatically subscribing to the maybe and calls onSuccessAction and onErrorAction on maybe events.
     * Don't forget to process errors if completable can emit them.
     *
     * @param onSuccess     Action which will raise on every [io.reactivex.MaybeEmitter.onSuccess] ()} item;
     * @param onError       Action which will raise on every [io.reactivex.MaybeEmitter.onError] throwable;
     * @param onComplete    Action which will raise on every [io.reactivex.MaybeEmitter.onComplete] item;
     * @return [Disposable] which is wrapping source maybe to unsubscribe from it onDestroy.
     */
    fun <T> Maybe<T>.untilDestroy(
            onSuccess: (T) -> Unit = Functions.emptyConsumer<T>()::accept,
            onError: (Throwable) -> Unit = getActionThrowableForAssertion(Lc.getCodePoint(this, 2)),
            onComplete: () -> Unit = Functions.EMPTY_ACTION::run
    ): Disposable

}
