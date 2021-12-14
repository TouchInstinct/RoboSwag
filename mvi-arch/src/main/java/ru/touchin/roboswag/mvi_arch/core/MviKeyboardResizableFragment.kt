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
import ru.touchin.roboswag.navigation_base.keyboard_resizeable.OnHideListener
import ru.touchin.roboswag.navigation_base.keyboard_resizeable.OnShowListener

// CPD-OFF
/**
 * Same code as in [ru.touchin.roboswag.navigation_base.keyboard_resizeable.StatefulKeyboardResizeableFragment] but inherited from MviFragment
 * Used to detect IME events (show, hide)
 */

@Deprecated(
        "You have to be inherited from this class to be able to implement keyboard detection",
        replaceWith = ReplaceWith("fragment.addKeyboardListener")
)
abstract class MviKeyboardResizableFragment<NavArgs, State, Action, VM>(
        @LayoutRes layout: Int
) : MviFragment<NavArgs, State, Action, VM>(layout)
        where NavArgs : Parcelable,
              State : ViewState,
              Action : ViewAction,
              VM : MviViewModel<NavArgs, Action, State> {

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
// CPD-ON
