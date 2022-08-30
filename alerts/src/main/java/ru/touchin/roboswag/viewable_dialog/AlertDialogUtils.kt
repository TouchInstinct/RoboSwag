package ru.touchin.roboswag.viewable_dialog

import android.widget.TextView
import androidx.core.view.isVisible
import ru.touchin.extensions.setOnRippleClickListener

fun setupButton(alertDialog: androidx.appcompat.app.AlertDialog, buttonView: TextView, text: String?, onButtonClick: (() -> Unit)?) {
    buttonView.setTextOrGone(text)
    buttonView.setOnRippleClickListener {
        onButtonClick?.invoke()
        alertDialog.dismiss()
    }
}

fun TextView.setTextOrGone(text: CharSequence?) {
    if (!text.isNullOrEmpty()) {
        isVisible = true
        setText(text)
    } else {
        isVisible = false
        setText(null)
    }
}
