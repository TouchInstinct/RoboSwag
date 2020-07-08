package ru.touchin.extensions

import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import ru.touchin.utils.ActionThrottler

const val RIPPLE_EFFECT_DELAY_MS = 150L

/**
 * Sets click listener to view. On click it will call something after delay.
 *
 * @param listener  Click listener.
 */
fun View.setOnRippleClickListener(listener: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        setOnClickListener {
            ActionThrottler.throttleAction {
                postDelayed({ if (hasWindowFocus()) listener() }, RIPPLE_EFFECT_DELAY_MS)
            }
        }
    } else {
        setOnClickListener { listener() }
    }
}

fun View.showSoftInput() {
    requestFocus()
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideSoftInput() {
    clearFocus()
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}