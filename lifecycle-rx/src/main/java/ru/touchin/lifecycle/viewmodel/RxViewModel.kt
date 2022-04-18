package ru.touchin.lifecycle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.annotation.CallSuper

/**
 * Base class of ViewModel with [io.reactivex.disposables.Disposable] handling.
 */
open class RxViewModel(
        private val destroyable: BaseDestroyable = BaseDestroyable(),
        private val liveDataDispatcher: LiveDataDispatcher = BaseLiveDataDispatcher(destroyable)
) : ViewModel(), Destroyable by destroyable, LiveDataDispatcher by liveDataDispatcher {

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        destroyable.onDestroy()
    }

}
