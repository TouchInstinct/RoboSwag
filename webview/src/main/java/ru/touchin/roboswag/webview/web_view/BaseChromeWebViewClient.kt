package ru.touchin.roboswag.webview.web_view

import android.webkit.ConsoleMessage
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView

open class BaseChromeWebViewClient(
        private val onJsConfirm: (() -> Unit)? = null,
        private val onJsAlert: (() -> Unit)? = null,
        private val onJsPrompt: ((String?) -> Unit)? = null,
        private val onJsError: ((error: ConsoleMessage) -> Unit)? = null
) : WebChromeClient() {

    override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        onJsConfirm?.invoke()
        result?.confirm()
        return true
    }

    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        onJsAlert?.invoke()
        result?.confirm()
        return true
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        if (consoleMessage?.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
            onJsError?.invoke(consoleMessage)
        }
        return super.onConsoleMessage(consoleMessage)
    }

    override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
        onJsPrompt?.invoke(defaultValue)
        result?.confirm()
        return true
    }

}
