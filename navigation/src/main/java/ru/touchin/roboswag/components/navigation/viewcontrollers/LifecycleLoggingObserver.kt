package ru.touchin.roboswag.components.navigation.viewcontrollers

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import ru.touchin.roboswag.core.log.Lc
import ru.touchin.roboswag.core.log.LcGroup

class LifecycleLoggingObserver (
        private val call: lifecycleOwner
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateLifecycleEvent() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(lifecycleOwner, "onCreate"))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartLifecycleEvent() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(lifecycleOwner, "onStop"))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumeLifecycleEvent() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(lifecycleOwner, "onResume"))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPauseLifecycleEvent() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(lifecycleOwner, "onPause"))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopLifecycleEvent() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(lifecycleOwner, "onStop"))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyLifecycleEvent() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(lifecycleOwner, "onDestroy"))
    }

}
