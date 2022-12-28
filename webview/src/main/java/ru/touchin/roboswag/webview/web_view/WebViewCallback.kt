package ru.touchin.roboswag.webview.web_view

import android.webkit.ConsoleMessage
import android.webkit.WebView

interface WebViewCallback {

    fun onStateChanged(newState: WebViewState)

    fun onOverrideUrlLoading(url: String?): Boolean

    fun onRepeatButtonClicked()

    fun onPageCookiesLoaded(cookies: Map<String, String>?) = Unit

    fun onRedirectInsideWebView(webView: WebView, url: String?) = Unit

    fun onWebViewScrolled(scrollX: Int, scrollY: Int) = Unit

    fun onJsConfirm(message: String?) = Unit

    fun onJsAlert(message: String?) = Unit

    fun onJsPrompt(defaultValue: String?) = Unit

    fun onJsError(error: ConsoleMessage) = Unit

    fun onProgressChanged(progress: Int) = Unit
}
