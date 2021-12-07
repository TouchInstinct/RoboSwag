package ru.touchin.roboswag.navigation_base.keyboard_resizeable

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 *     This detector NOT detect landscape fullscreen keyboard
 *
 *     Your activity must have android:windowSoftInputMode="adjustResize" at least, otherwise listeners won't be called
 */

typealias OnHideListener = () -> Unit
typealias OnShowListener = (Int) -> Unit

class KeyboardBehaviorDetector(
        activity: FragmentActivity
) : LifecycleObserver {

    private val view = activity.window.decorView

    private val keyboardHideListeners: MutableList<OnHideListener> = mutableListOf()
    private val keyboardShowListeners: MutableList<OnShowListener> = mutableListOf()

    fun addOnHideListener(listener: OnHideListener) { keyboardHideListeners.add(listener) }

    fun addOnShowListener(listener: OnShowListener) { keyboardShowListeners.add(listener) }

    fun removeOnHideListener(listener: OnHideListener) { keyboardHideListeners.remove(listener) }

    fun removeOnShowListener(listener: OnShowListener) { keyboardShowListeners.remove(listener) }

    // -1 when we never measure insets yet
    var startNavigationBarHeight = -1
        private set

    private val listener = { isKeyboardOpen: Boolean, windowInsets: WindowInsetsCompat ->
        if (isKeyboardOpen) {
            keyboardShowListeners.forEach { it.invoke(windowInsets.systemWindowInsetBottom - startNavigationBarHeight) }
        } else {
            keyboardHideListeners.forEach { it.invoke()}
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startDetection() {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, windowInsets ->
            val bottomInset = windowInsets.systemWindowInsetBottom
            listener(isKeyboardOpen(bottomInset), windowInsets)

            if (startNavigationBarHeight == -1) startNavigationBarHeight = bottomInset

            ViewCompat.onApplyWindowInsets(view, windowInsets)
        }
        ViewCompat.requestApplyInsets(view)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopDetection() {
        ViewCompat.setOnApplyWindowInsetsListener(view, null)
    }

    private fun isKeyboardOpen(navigationHeight: Int) =
            navigationHeight != startNavigationBarHeight && startNavigationBarHeight != -1

}
