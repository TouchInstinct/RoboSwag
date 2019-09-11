package ru.touchin.roboswag.components.navigation.keyboard_resizeable

import android.os.Build
import android.os.Parcelable
import androidx.annotation.LayoutRes
import ru.touchin.roboswag.components.navigation.activities.BaseActivity
import ru.touchin.roboswag.components.navigation.activities.OnBackPressedListener
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewController
import ru.touchin.roboswag.components.utils.UiUtils

abstract class KeyboardResizeableViewController<TActivity : BaseActivity, TState : Parcelable>(
        @LayoutRes layoutRes: Int,
        creationContext: CreationContext
) : ViewController<TActivity, TState>(
        creationContext,
        layoutRes
) {
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            creationContext.container?.requestApplyInsets()
        }
    }

    private var keyboardIsVisible: Boolean = false

    private val keyboardHideListener = OnBackPressedListener {
        if (keyboardIsVisible) {
            UiUtils.OfViews.hideSoftInput(activity)
            true
        } else {
            false
        }
    }

    private var isHideKeyboardOnBackEnabled = false

    protected open fun onKeyboardShow(diff: Int = 0) {}

    protected open fun onKeyboardHide() {}

    protected fun hideKeyboardOnBackPressed() {
        isHideKeyboardOnBackEnabled = true
    }

    override fun onResume() {
        super.onResume()
        if (isHideKeyboardOnBackEnabled) activity.addOnBackPressedListener(keyboardHideListener)
    }

    override fun onPause() {
        super.onPause()
        if (isHideKeyboardOnBackEnabled) activity.removeOnBackPressedListener(keyboardHideListener)
    }

    override fun onStart() {
        super.onStart()
        activity.keyboardBehaviorDetector?.apply {
            keyboardHideListener = {
                if (keyboardIsVisible) {
                    onKeyboardHide()
                }
                keyboardIsVisible = false
            }
            keyboardShowListener = { diff ->
                onKeyboardShow(diff)
                keyboardIsVisible = true
            }
            startDetection()
        }
    }

    override fun onStop() {
        super.onStop()
        activity.keyboardBehaviorDetector?.apply {
            keyboardHideListener = null
            keyboardShowListener = null
            stopDetection()
        }
    }
}
