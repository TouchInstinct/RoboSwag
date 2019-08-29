package ru.touchin.roboswag.components.navigation.keyboard_resizeable

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import ru.touchin.roboswag.components.navigation.activities.BaseActivity

// The workaround forces an activity to resize when keyboard appears in the full-screen mode
class KeyboardBehaviorDetector(
        activity: BaseActivity,
        fragmentContainerId: Int
) {

    companion object {
        private const val SCREEN_TO_KEYBOARD_HEIGHT_RATIO = 4.75
    }

    private val contentContainer = activity.findViewById(android.R.id.content) as ViewGroup
    private val fragmentContainer = activity.findViewById(fragmentContainerId) as ViewGroup
    private lateinit var rootView: View
    private val listener = { possiblyResizeChildOfContent() }

    private var keyboardHideListener: (() -> Unit)? = null
    private var keyboardShowListener: ((Int) -> Unit)? = null

    fun setKeyboardHideListener(listener: () -> Unit) {
        keyboardHideListener = listener
    }

    fun removeKeyboardHideListener() {
        keyboardHideListener = null
    }

    fun setKeyboardShowListener(listener: (Int) -> Unit) {
        keyboardShowListener = listener
    }

    fun removeKeyboardShowListener() {
        keyboardShowListener = null
    }

    // Call this in "onResume()" of a fragment
    fun startDetection() {
        rootView = fragmentContainer.getChildAt(0)

        contentContainer.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    // Call this in "onPause()" of a fragment
    fun stopDetection() {
        contentContainer.viewTreeObserver.removeOnGlobalLayoutListener(listener)
    }

    //https://stackoverflow.com/questions/2150078/how-to-check-visibility-of-software-keyboard-in-android?rq=1
    private fun possiblyResizeChildOfContent() {
        val rect = Rect()
        rootView.getWindowVisibleDisplayFrame(rect)
        val height = rootView.context.resources.displayMetrics.heightPixels
        val diff = height - rect.bottom

        if (diff > rootView.rootView.height / SCREEN_TO_KEYBOARD_HEIGHT_RATIO) {
            keyboardShowListener?.invoke(diff)
        } else {
            keyboardHideListener?.invoke()
        }
    }
}
