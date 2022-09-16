package ru.touchin.roboswag.viewable_dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.touchin.roboswag.alerts.R

class AlertDialogManager {

    @SuppressWarnings("detekt.LongParameterList")
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
                .setupAlertDialog(
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

    private fun AlertDialog.setupAlertDialog(
            title: String? = null,
            message: String? = null,
            positiveButtonText: String,
            onPositiveClick: (() -> Unit)? = null,
            negativeButtonText: String? = null,
            onNegativeClick: (() -> Unit)? = null,
            cancelable: Boolean = true,
            onCancelAction: () -> Unit = {}
    ) {
        setCancelable(cancelable)
        setOnDismissListener { onCancelAction() }
        findViewById<TextView>(R.id.alert_title)?.setTextOrGone(title)
        findViewById<TextView>(R.id.alert_message)?.setTextOrGone(message)
        findViewById<TextView>(R.id.alert_positive_button)?.let { buttonView ->
            setupButton(this, buttonView, positiveButtonText, onPositiveClick)
        }
        findViewById<TextView>(R.id.alert_negative_button)?.let { buttonView ->
            setupButton(this, buttonView, negativeButtonText, onNegativeClick)
        }
    }
}
