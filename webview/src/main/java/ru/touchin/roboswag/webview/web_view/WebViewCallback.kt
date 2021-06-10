package ru.touchin.roboswag.webview.web_view

import android.webkit.WebView

interface WebViewCallback {

    fun onStateChanged(newState: WebViewLoadingState)

    fun onOverrideUrlLoading(url: String?): Boolean

    fun onPageCookiesLoaded(cookies: Map<String, String>?)

    fun actionOnRedirectInsideWebView(webView: WebView, url: String?)

}
