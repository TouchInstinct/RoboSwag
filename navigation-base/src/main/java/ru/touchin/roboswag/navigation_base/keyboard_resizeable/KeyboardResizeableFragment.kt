package ru.touchin.roboswag.navigation_base.keyboard_resizeable

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleObserver
import ru.touchin.roboswag.components.utils.hideSoftInput
import ru.touchin.roboswag.navigation_base.activities.BaseActivity
import ru.touchin.roboswag.navigation_base.activities.OnBackPressedListener
import ru.touchin.roboswag.navigation_base.fragments.BaseFragment

abstract class KeyboardResizeableFragment<TActivity : BaseActivity>(
        @LayoutRes layoutRes: Int
) : BaseFragment<TActivity>(
        layoutRes
) {

    private var isKeyboardVisible: Boolean = false

    private val onBackPressedListener = OnBackPressedListener {
        if (isKeyboardVisible) {
            activity.hideSoftInput()
            true
        } else {
            false
        }
    }

    private val keyboardHideListener: OnHideListener = {
        if (isKeyboardVisible) {
            onKeyboardHide()
        }
        isKeyboardVisible = false
    }

    private val keyboardShowListener: OnShowListener = { diff ->
        onKeyboardShow(diff)
        isKeyboardVisible = true
    }

    private var isHideKeyboardOnBackEnabled = false

    protected open fun onKeyboardShow(diff: Int = 0) {}

    protected open fun onKeyboardHide() {}

    protected fun hideKeyboardOnBackPressed() {
        isHideKeyboardOnBackEnabled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.requestApplyInsets()
        lifecycle.addObserver(activity.keyboardBehaviorDetector as LifecycleObserver)
    }

    override fun onResume() {
        super.onResume()
        if (isHideKeyboardOnBackEnabled) activity.addOnBackPressedListener(onBackPressedListener)
    }

    override fun onPause() {
        super.onPause()
        if (isKeyboardVisible) activity.hideSoftInput()
        if (isHideKeyboardOnBackEnabled) activity.removeOnBackPressedListener(onBackPressedListener)
    }

    override fun onStart() {
        super.onStart()
        activity.keyboardBehaviorDetector?.apply {
            addOnHideListener(keyboardHideListener)
            addOnShowListener(keyboardShowListener)
        }
    }

    override fun onStop() {
        super.onStop()
        activity.keyboardBehaviorDetector?.apply {
            removeOnHideListener(keyboardHideListener)
            removeOnShowListener(keyboardShowListener)
        }
    }

}
