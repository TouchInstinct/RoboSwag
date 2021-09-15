package ru.touchin.extensions

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
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


/**
 * Used for finding suitable parent view for snackbar
 * Method was taken from com.google.android.material.snackbar.Snackbar.findSuitableParent
 */
fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                return view
            } else {
                fallback = view
            }
        }

        if (view != null) {
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)

    return fallback
}
