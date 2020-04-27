package ru.touchin.roboswag.components.navigation_viewcontroller.keyboard_resizeable

import android.os.Build
import android.os.Parcelable
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleObserver
import ru.touchin.roboswag.components.navigation_viewcontroller.viewcontrollers.ViewController
import ru.touchin.roboswag.components.utils.UiUtils
import ru.touchin.roboswag.navigation_base.activities.BaseActivity
import ru.touchin.roboswag.navigation_base.activities.OnBackPressedListener

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
        lifecycle.addObserver(activity.keyboardBehaviorDetector as LifecycleObserver)
    }

    private var isKeyboardVisible: Boolean = false

    private val keyboardHideListener = OnBackPressedListener {
        if (isKeyboardVisible) {
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
        notifyKeyboardHidden()
        if (isHideKeyboardOnBackEnabled) activity.removeOnBackPressedListener(keyboardHideListener)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        activity.keyboardBehaviorDetector?.apply {
            keyboardHideListener = {
                if (isKeyboardVisible) {
                    onKeyboardHide()
                }
                isKeyboardVisible = false
            }
            keyboardShowListener = { diff ->
                onKeyboardShow(diff)
                isKeyboardVisible = true
            }
        }
    }

    override fun onStop() {
        super.onStop()
        activity.keyboardBehaviorDetector?.apply {
            keyboardHideListener = null
            keyboardShowListener = null
        }
    }

    private fun notifyKeyboardHidden() {
        if (isKeyboardVisible) onKeyboardHide()
        isKeyboardVisible = false
    }
}
