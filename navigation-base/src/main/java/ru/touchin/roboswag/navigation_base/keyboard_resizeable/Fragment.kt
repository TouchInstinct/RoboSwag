package ru.touchin.roboswag.navigation_base.keyboard_resizeable

import androidx.fragment.app.Fragment

fun Fragment.addKeyboardListener(
        onShow: OnShowListener? = null,
        onHide: OnHideListener? = null
) {
    lifecycle.addObserver(
            FragmentKeyboardListenerObserver(
                    fragment = this,
                    onShow = onShow,
                    onHide = onHide
            )
    )
}
