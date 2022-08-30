package ru.touchin.roboswag.viewable_dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.touchin.roboswag.alerts.R

class AlertDialogManager {

    fun showAlertDialog(
            context: Context,
            style: Int = R.style.AlertDialogDefault,
            title: String? = null,
            message: String? = null,
            positiveButtonText: String = context.getString(R.string.positive_btn),
            onPositiveAction: (() -> Unit)? = null,
            negativeBtnTitle: String? = null,
            onNegativeAction: (() -> Unit)? = null,
            dialogLayout: Int = R.layout.dialog_alert,
            cancelable: Boolean = true,
            onCancelAction: () -> Unit = {}
    ) {
        val styledContext = ContextThemeWrapper(context, style)
        MaterialAlertDialogBuilder(styledContext)
                .setView(LayoutInflater.from(styledContext).inflate(dialogLayout, null))
                .show()
                .apply {
                    setupAlertDialog(
                            dialog = this,
                            title = title,
                            message = message,
                            positiveButtonText = positiveButtonText,
                            onPositiveClick = onPositiveAction,
                            negativeButtonText = negativeBtnTitle,
                            onNegativeClick = onNegativeAction,
                            cancelable = cancelable,
                            onCancelAction = onCancelAction
                    )
                }
    }

    fun showOkDialog(
            context: Context,
            style: Int = R.style.AlertDialogDefault,
            title: String? = null,
            message: String? = null,
            okButtonText: String = context.getString(R.string.positive_btn),
            onOkAction: (() -> Unit)? = null,
            cancelable: Boolean = true,
            onCancelAction: () -> Unit = {}
    ) = showAlertDialog(
            context = context,
            style = style,
            title = title,
            message = message,
            positiveButtonText = okButtonText,
            onPositiveAction = onOkAction,
            cancelable = cancelable,
            onCancelAction = onCancelAction
    )


    private fun setupAlertDialog(
            dialog: androidx.appcompat.app.AlertDialog,
            title: String? = null,
            message: String? = null,
            positiveButtonText: String,
            onPositiveClick: (() -> Unit)? = null,
            negativeButtonText: String? = null,
            onNegativeClick: (() -> Unit)? = null,
            cancelable: Boolean = true,
            onCancelAction: () -> Unit = {}
    ) {
        dialog.setCancelable(cancelable)
        dialog.setOnDismissListener { onCancelAction() }
        dialog.findViewById<TextView>(R.id.alert_title)?.setTextOrGone(title)
        dialog.findViewById<TextView>(R.id.alert_message)?.setTextOrGone(message)
        dialog.findViewById<TextView>(R.id.alert_positive_button)?.let { buttonView ->
            setupButton(dialog, buttonView, positiveButtonText, onPositiveClick)
        }
        dialog.findViewById<TextView>(R.id.alert_negative_button)?.let { buttonView ->
            setupButton(dialog, buttonView, negativeButtonText, onNegativeClick)
        }
    }
}
