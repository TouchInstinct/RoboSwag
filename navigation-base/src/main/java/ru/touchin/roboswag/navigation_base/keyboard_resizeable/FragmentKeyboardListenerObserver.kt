package ru.touchin.roboswag.navigation_base.keyboard_resizeable

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import ru.touchin.roboswag.navigation_base.activities.BaseActivity

/**
 * Observer for adding listeners for activity's keyboardBehaviorDetector with lifecycle awareness
 */
class FragmentKeyboardListenerObserver(
        fragment: Fragment,
        private val onShow: OnShowListener? = null,
        private val onHide: OnHideListener? = null
) : LifecycleObserver {

    private val keyboardDetector = (fragment.requireActivity() as? BaseActivity)
            ?.keyboardBehaviorDetector
            ?: error("Fragment must be launched from BaseActivity")

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun addListener() {
        onShow?.let(keyboardDetector::addOnShowListener)
        onHide?.let(keyboardDetector::addOnHideListener)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun removeListener() {
        onShow?.let(keyboardDetector::removeOnShowListener)
        onHide?.let(keyboardDetector::removeOnHideListener)
    }
}
