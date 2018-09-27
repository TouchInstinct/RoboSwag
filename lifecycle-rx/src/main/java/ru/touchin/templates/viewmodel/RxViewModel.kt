package ru.touchin.templates.viewmodel

import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import ru.touchin.livedata.dispatcher.BaseLiveDataDispatcher
import ru.touchin.livedata.dispatcher.LiveDataDispatcher
import ru.touchin.livedata.destroyable.BaseDestroyable
import ru.touchin.livedata.destroyable.Destroyable

/**
 * Base class of ViewModel with [io.reactivex.disposables.Disposable] handling.
 */
open class RxViewModel(
        private val destroyable: BaseDestroyable = BaseDestroyable(),
        private val liveDataDispatcher: BaseLiveDataDispatcher = BaseLiveDataDispatcher(destroyable)
) : ViewModel(), Destroyable by destroyable, LiveDataDispatcher by liveDataDispatcher {

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        destroyable.onDestroy()
    }

}
