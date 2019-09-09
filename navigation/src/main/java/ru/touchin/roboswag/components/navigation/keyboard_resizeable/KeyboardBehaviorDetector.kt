package ru.touchin.roboswag.components.navigation.keyboard_resizeable

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.touchin.roboswag.components.navigation.activities.BaseActivity

/**
 *     This detector NOT detect landscape fullscreen keyboard
 *
 *     You activity must have android:windowSoftInputMode="adjustResize" at least, otherwise listeners won't be called
 */
class KeyboardBehaviorDetector(
        activity: BaseActivity
) {

    private val view = activity.window.decorView

    var keyboardHideListener: (() -> Unit)? = null
    var keyboardShowListener: ((Int) -> Unit)? = null

    var startNavigationBarHeight = -1
        private set

    private val listener = { isKeyboardOpen: Boolean,
                             windowInsets: WindowInsetsCompat ->
        if (isKeyboardOpen) {
            keyboardShowListener?.invoke(
                    windowInsets.systemWindowInsetBottom - startNavigationBarHeight
            )
        } else {
            keyboardHideListener?.invoke()
        }
    }

    // Call this in "onResume()" of a fragment
    fun startDetection() {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, windowInsets ->
            val bottomInset = windowInsets.systemWindowInsetBottom
            listener(isKeyboardOpen(bottomInset), windowInsets)

            if (startNavigationBarHeight == -1) startNavigationBarHeight = bottomInset

            windowInsets
        }
    }

    // Call this in "onPause()" of a fragment
    fun stopDetection() {
        ViewCompat.setOnApplyWindowInsetsListener(view, null)
    }

    private fun isKeyboardOpen(navigationHeight: Int) =
            navigationHeight != startNavigationBarHeight && startNavigationBarHeight != -1

}
