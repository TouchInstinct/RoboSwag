package ru.touchin.roboswag.webview_delegate

import android.content.pm.ApplicationInfo
import android.view.View
import android.webkit.WebView
import androidx.annotation.CallSuper
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

open class SimpleWebViewDelegate(
        protected val webView: WebView,
        protected open val errorView: View,
        protected open val loadingView: View
) : WebViewCallback {

    init {
        if (0 != webView.context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        webView.webViewClient = getWebViewClient()

        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        with(webView.settings) {
            loadsImagesAutomatically = true
            javaScriptEnabled = true
            domStorageEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
        }
    }

    open fun getWebViewClient() = SimpleWebViewClient(this)

    open override fun clearCacheAndData(view: WebView) {
        with(view) {
            clearMatches()
            clearFormData()
            clearCache(true)
        }
    }

    @CallSuper
    open override fun onStateChanged(newState: LoadingState) {
        when (newState) {
            LoadingState.LOADED -> {
                webView.isVisible = true
                errorView.isVisible = false
                loadingView.isVisible = false
            }
            LoadingState.LOADING -> {
                webView.isInvisible = true
                errorView.isVisible = false
                loadingView.isVisible = true
            }
            LoadingState.ERROR -> {
                webView.isInvisible = true
                errorView.isVisible = true
                loadingView.isVisible = false
            }
        }
    }

    open fun loadUrl(url: String?) {
        webView.loadUrl(url)
    }

    open fun onBackPressed(): Boolean {
        if (webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return false
    }

}
