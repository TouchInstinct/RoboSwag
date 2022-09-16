package ru.touchin.roboswag.viewable_dialog

import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import ru.touchin.extensions.setOnRippleClickListener

fun setupButton(alertDialog: AlertDialog, buttonView: TextView, text: String?, onButtonClick: (() -> Unit)?) {
    buttonView.setTextOrGone(text)
    buttonView.setOnRippleClickListener {
        onButtonClick?.invoke()
        alertDialog.dismiss()
    }
}

fun TextView.setTextOrGone(text: CharSequence?) {
    isVisible = !text.isNullOrEmpty()
    setText(text)
}
