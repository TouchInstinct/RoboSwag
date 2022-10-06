package ru.touchin.roboswag.navigation_base.keyboard_resizeable

import androidx.fragment.app.Fragment

/**
 * Use in [Fragment.onViewCreated] to access [Fragment.getViewLifecycleOwner]
 */
fun Fragment.addKeyboardListener(
        onShow: OnShowListener? = null,
        onHide: OnHideListener? = null
) {
    viewLifecycleOwner.lifecycle.addObserver(
            FragmentKeyboardListenerObserver(
                    fragment = this,
                    onShow = onShow,
                    onHide = onHide
            )
    )
}
