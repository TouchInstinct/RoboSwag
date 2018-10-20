package ru.touchin.extensions

import android.os.Build
import android.view.View

private const val RIPPLE_EFFECT_DELAY = 150L

/**
 * Sets click listener to view. On click it will call something after delay.
 *
 * @param listener  Click listener.
 */
fun View.setOnRippleClickListener(listener: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        setOnClickListener { postDelayed({ if (hasWindowFocus()) listener() }, RIPPLE_EFFECT_DELAY) }
    } else {
        setOnClickListener { listener() }
    }
}
