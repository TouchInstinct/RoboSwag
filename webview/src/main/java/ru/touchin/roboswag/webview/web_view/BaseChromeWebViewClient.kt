package ru.touchin.roboswag.webview.web_view

import android.webkit.ConsoleMessage
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView

open class BaseChromeWebViewClient(
        private val callback: WebViewCallback
) : WebChromeClient() {

    override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        callback.onJsConfirm(message)
        result?.confirm()
        return true
    }

    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        callback.onJsAlert(message)
        result?.confirm()
        return true
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        if (consoleMessage?.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
            callback.onJsError(consoleMessage)
        }
        return super.onConsoleMessage(consoleMessage)
    }

    override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
        callback.onJsPrompt(defaultValue)
        result?.confirm()
        return true
    }

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        callback.onProgressChanged(newProgress)
    }

}
