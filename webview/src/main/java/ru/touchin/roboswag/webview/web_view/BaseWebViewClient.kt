package ru.touchin.roboswag.webview.web_view

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Handler
import android.os.Looper
import android.webkit.CookieManager
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.postDelayed

open class BaseWebViewClient(private val callback: WebViewCallback, private val isSslPinningEnable: Boolean) : WebViewClient() {

    companion object {
        private const val WEB_VIEW_TIMEOUT_MS = 30 * 1000L // 30 sec
    }

    private var isError = false
    private var isTimeout = true

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        isError = false
        callback.onStateChanged(WebViewLoadingState.LOADING)

        Looper.myLooper()?.let { looper ->
            val handler = Handler(looper)
            handler.postDelayed(WEB_VIEW_TIMEOUT_MS) {
                if (isTimeout) {
                    isError = true
                    pageFinished()
                }
                isTimeout = true
            }
        }
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        isTimeout = false
        callback.onPageCookiesLoaded(CookieManager.getInstance().getCookie(url).processCookies())
        pageFinished()
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
        return !callback.onOverrideUrlLoading(url) && view.originalUrl != null
    }

    override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
        if (isSslPinningEnable) {
            super.onReceivedSslError(view, handler, error)
            isError = true
            callback.onStateChanged(WebViewLoadingState.ERROR)
        } else {
            handler.proceed()
        }
    }

    override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
        if (!(error.errorCode == -10 && "about:blank" == request.url.toString())) {
            isError = true
        }
        pageFinished()
    }

    private fun pageFinished() {
        callback.onStateChanged(if (isError) WebViewLoadingState.ERROR else WebViewLoadingState.LOADED)
    }

    private fun String.processCookies(): Map<String, String> {
        val cookiesMap = mutableMapOf<String, String>()
        this.split(";")
                .forEach { cookie ->
                    val splittedCookie = cookie.trim().split("=")
                    cookiesMap[splittedCookie.first()] = splittedCookie.last()
                }
        return cookiesMap
    }

}

enum class WebViewLoadingState {
    LOADING,
    ERROR,
    LOADED
}
