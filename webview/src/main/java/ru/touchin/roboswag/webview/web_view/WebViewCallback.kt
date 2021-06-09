package ru.touchin.roboswag.webview.web_view

import androidx.annotation.StringRes

interface WebViewCallback {

    fun onStateChanged(newState: WebViewLoadingState)

    fun onOverrideUrlLoading(url: String?): Boolean

    fun onPageCookiesLoaded(cookies: Map<String, String>?)

    fun onInsideWebViewRedirect(url:String?)

}
