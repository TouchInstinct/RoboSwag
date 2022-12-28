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
import ru.touchin.roboswag.webview.web_view.redirection.IgnoredErrorsHolder

open class BaseWebViewClient(
        private val callback: WebViewCallback,
        private val ignoredErrorsHolder: IgnoredErrorsHolder = IgnoredErrorsHolder(),
        private val isSslPinningEnable: Boolean = true
) : WebViewClient() {

    companion object {
        private const val WEB_VIEW_TIMEOUT_MS = 30 * 1000L // 30 sec
    }

    private var isError = false
    private var isTimeout = true

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        isError = false
        callback.onStateChanged(WebViewState.LOADING)

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

    /**
     * onPageFinished is always called after onReceivedError,
     * except when there is a error page in the cache and onReceivedError is called first
     */
    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        isTimeout = false
        if (url == "about:blank") {
            isError = true
        }
        if (!isError) {
            callback.onPageCookiesLoaded(CookieManager.getInstance().getCookie(url).processCookies())
        }
        pageFinished()
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        return if (!callback.onOverrideUrlLoading(url) && view.originalUrl != null) {
            callback.onRedirectInsideWebView(webView = view, url = url)
            true
        } else {
            false
        }
    }

    override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
        if (isSslPinningEnable) {
            super.onReceivedSslError(view, handler, error)
            isError = true
            callback.onStateChanged(WebViewState.ERROR)
        } else {
            handler.proceed()
        }
    }

    /**
     * onReceivedError isn't called when url is "about:blank" (url string isBlank)
     */
    override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
        if (ignoredErrorsHolder.shouldIgnoreError(request, error)) return
        isError = true
    }

    private fun pageFinished() {
        callback.onStateChanged(if (isError) WebViewState.ERROR else WebViewState.SUCCESS)
    }

    private fun String?.processCookies(): Map<String, String> {
        val cookiesMap = mutableMapOf<String, String>()
        this?.split(";")
                ?.forEach { cookie ->
                    val splittedCookie = cookie.trim().split("=")
                    cookiesMap[splittedCookie.first()] = splittedCookie.last()
                }
        return cookiesMap
    }

}
