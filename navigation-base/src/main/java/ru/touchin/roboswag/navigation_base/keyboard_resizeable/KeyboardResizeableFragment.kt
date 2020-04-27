package ru.touchin.roboswag.navigation_base.keyboard_resizeable

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleObserver
import ru.touchin.roboswag.components.utils.UiUtils
import ru.touchin.roboswag.navigation_base.activities.BaseActivity
import ru.touchin.roboswag.navigation_base.activities.OnBackPressedListener
import ru.touchin.roboswag.navigation_base.fragments.BaseFragment

abstract class KeyboardResizeableFragment<TActivity : BaseActivity, TState : Parcelable>(
        @LayoutRes layoutRes: Int
) : BaseFragment<TActivity, TState>(
        layoutRes
) {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            view.requestApplyInsets()
        }
        lifecycle.addObserver(activity.keyboardBehaviorDetector as LifecycleObserver)
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
