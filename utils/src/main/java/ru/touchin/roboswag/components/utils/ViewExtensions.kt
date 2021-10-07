package ru.touchin.roboswag.components.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Returns string representation of [View]'s ID.
 *
 * @param view [View] to get ID from;
 * @return Readable ID.
 */
fun View.getViewIdString(): String = try {
    resources.getResourceName(id)
} catch (exception: Resources.NotFoundException) {
    id.toString()
}

/**
 * Hides device keyboard for target activity.
 */
fun Activity.hideSoftInput() {
    currentFocus?.hideSoftInput()
}

/**
 * Hides device keyboard for target view.
 */
fun View.hideSoftInput() {
    clearFocus()
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Shows device keyboard over [Activity] and focuses [View].
 * Do NOT use it if keyboard is over [android.app.Dialog] - it won't work as they have different [Activity.getWindow].
 * Do NOT use it if you are not sure that view is already added on screen.
 *
 * @param view View to get focus for input from keyboard.
 */
fun View.showSoftInput() {
    requestFocus()
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Returns listener for BottomSheetDialogFragment so that dialog is shifted when the keyboard is opened
 */

fun BottomSheetDialogFragment.getResizableShowListener() = DialogInterface.OnShowListener { dialog ->
    (dialog as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            ?.let { BottomSheetBehavior.from(it) }
            ?.apply {
                state = BottomSheetBehavior.STATE_EXPANDED
                skipCollapsed = true
            }
}
