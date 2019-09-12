package ru.touchin.roboswag.components.navigation_new.viewcontrollers

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import ru.touchin.roboswag.core.log.Lc
import ru.touchin.roboswag.core.log.LcGroup

class LifecycleLoggingObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAnyLifecycleEvent() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
    }

}
