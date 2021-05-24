package ru.touchin.roboswag.webview.web_view

interface WebViewCallback {

    fun onStateChanged(newState: WebViewLoadingState)

    fun onOverrideUrlLoading(url: String?): Boolean

    fun onPageCookiesLoaded(cookies: Map<String, String>?)

}
