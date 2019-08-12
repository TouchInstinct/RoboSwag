package ru.touchin.roboswag.webview_delegate

import android.webkit.WebView

interface WebViewCallback {

    fun onOverrideUrlLoading(url: String?): Boolean = false

    fun onInterceptRequest(url: String) {}

    fun onStartPageLoading(url: String) {}

    fun clearCacheAndData(view: WebView)

    fun onStateChanged(newState: LoadingState)

}
