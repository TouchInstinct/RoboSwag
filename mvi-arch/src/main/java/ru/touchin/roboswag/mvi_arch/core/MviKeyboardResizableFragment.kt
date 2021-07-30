package ru.touchin.roboswag.mvi_arch.core

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleObserver
import ru.touchin.roboswag.components.utils.hideSoftInput
import ru.touchin.roboswag.mvi_arch.marker.ViewAction
import ru.touchin.roboswag.mvi_arch.marker.ViewState
import ru.touchin.roboswag.navigation_base.activities.OnBackPressedListener

// CPD-OFF
/**
 * Same code as in [ru.touchin.roboswag.navigation_base.keyboard_resizeable.KeyboardResizeableFragment] but inherited from MviFragment
 * Used to detect IME events (show, hide)
 */

abstract class MviKeyboardResizableFragment<NavArgs, State, Action, VM>(
        @LayoutRes layout: Int
) : MviFragment<NavArgs, State, Action, VM>(layout)
        where NavArgs : Parcelable,
              State : ViewState,
              Action : ViewAction,
              VM : MviViewModel<NavArgs, Action, State> {

    private var isKeyboardVisible: Boolean = false

    private val keyboardHideListener = OnBackPressedListener {
        if (isKeyboardVisible) {
            activity.hideSoftInput()
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

        view.requestApplyInsets()
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
// CPD-ON
