package ru.touchin.roboswag.webview_delegate

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

open class SimpleWebViewClient(private val callback: WebViewCallback) : WebViewClient() {

    var isError = false

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        isError = false
        callback.onStateChanged(LoadingState.LOADING)
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        pageFinished(view)
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
        val shouldOverride = callback.onOverrideUrlLoading(url)
        if (!shouldOverride) {
            view.loadUrl(url)
        }
        return shouldOverride
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        return shouldOverrideUrlLoading(view, request.url.toString())
    }

    override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
        handler.proceed()
    }

    private fun pageFinished(view: WebView) {
        callback.onStateChanged(if (isError) LoadingState.ERROR else LoadingState.LOADED)
        callback.clearCacheAndData(view)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
        onReceivedError(view, error.errorCode, error.description.toString(), request.url.toString())
    }

    override fun onReceivedError(view: WebView, errorCode: Int, description: String?, failingUrl: String) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        if (!(errorCode == -10 && "about:blank" == failingUrl)) {
            isError = true
        }
        pageFinished(view)
    }

}
