package ru.touchin.roboswag.alerts

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import ru.touchin.roboswag.alerts.dialog_view.setTextOrGone
import ru.touchin.roboswag.alerts.dialog_view.setupButton

object ViewableAlertDialog {

    fun showAlertDialog(
        context: Context,
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
        AlertDialog.Builder(context)
            .setView(LayoutInflater.from(context).inflate(dialogLayout, null))
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
                    onCancelAction = onCancelAction)
            }
    }

    private fun setupAlertDialog(
        dialog: AlertDialog,
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
