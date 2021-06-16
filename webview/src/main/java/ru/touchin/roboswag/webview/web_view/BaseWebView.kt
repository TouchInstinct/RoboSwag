package ru.touchin.roboswag.webview.web_view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.WebView
import androidx.core.content.withStyledAttributes
import androidx.core.widget.TextViewCompat
import ru.touchin.extensions.setOnRippleClickListener
import ru.touchin.roboswag.views.widget.Switcher
import ru.touchin.roboswag.webview.R
import ru.touchin.roboswag.webview.databinding.BaseWebViewBinding

open class BaseWebView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0
) : Switcher(context, attrs), WebViewCallback {

    private val binding = BaseWebViewBinding.inflate(LayoutInflater.from(context), this)

    var onWebViewLoaded: (() -> Unit)? = null
    var onWebViewRepeatButtonClicked: (() -> Unit)? = null
    var onWebViewScrolled: ((WebView, Int, Int) -> Unit)? = null
    var onCookieLoaded: ((cookies: Map<String, String>?) -> Unit)? = null

    var onJsConfirm: (() -> Unit)? = null
    var onJsAlert: (() -> Unit)? = null
    var onJsPrompt: ((defaultValue: String?) -> Unit)? = null
    var onJsError: ((error: ConsoleMessage) -> Unit)? = null

    var isPullToRefreshEnable = false
        set(value) {
            binding.pullToRefresh.isEnabled = value
            binding.pullToRefresh.isRefreshing = false
            field = value
        }

    var isRedirectEnable = false

    /**
     * If you need to do some action on url click inside WebView, just assign this parameter and disable isRedirectEnable
     **/
    var actionOnRedirect: ((String?, WebView) -> Unit)? = null

    init {
        binding.pullToRefresh.isEnabled = isPullToRefreshEnable
        binding.apply {
            context.withStyledAttributes(attrs, R.styleable.BaseWebView, defStyleAttr, 0) {
                if (hasValue(R.styleable.BaseWebView_errorTextAppearance)) {
                    TextViewCompat.setTextAppearance(errorText, getResourceId(R.styleable.BaseWebView_errorTextAppearance, 0))
                }
                if (hasValue(R.styleable.BaseWebView_repeatButtonTextAppearance)) {
                    TextViewCompat.setTextAppearance(errorRepeatButton, getResourceId(R.styleable.BaseWebView_repeatButtonTextAppearance, 0))
                }
                if (hasValue(R.styleable.BaseWebView_progressBarTintColor)) {
                    progressBar.indeterminateTintList = ColorStateList.valueOf(getColor(R.styleable.BaseWebView_progressBarTintColor, Color.BLACK))
                }
                if (hasValue(R.styleable.BaseWebView_repeatButtonBackground)) {
                    errorRepeatButton.setBackgroundResource(getResourceId(R.styleable.BaseWebView_repeatButtonBackground, 0))
                }
                if (hasValue(R.styleable.BaseWebView_screenBackground)) {
                    setBackgroundResource(getResourceId(R.styleable.BaseWebView_screenBackground, 0))
                }
                if (hasValue(R.styleable.BaseWebView_errorText)) {
                    errorText.text = getString(R.styleable.BaseWebView_errorText)
                }
                if (hasValue(R.styleable.BaseWebView_repeatButtonText)) {
                    errorRepeatButton.text = getString(R.styleable.BaseWebView_repeatButtonText)
                }
            }
            pullToRefresh.setOnRefreshListener {
                webView.reload()
            }
            errorRepeatButton.setOnRippleClickListener {
                onWebViewRepeatButtonClicked?.invoke()
            }
            webView.onScrollChanged = { scrollX, scrollY, _, _ ->
                onWebViewScrolled?.invoke(binding.webView, scrollX, scrollY)
            }
            setWebViewPreferences()
        }
    }

    override fun onStateChanged(newState: WebViewLoadingState) {
        when {
            newState == WebViewLoadingState.LOADED -> {
                onWebViewLoaded?.invoke()
                binding.pullToRefresh.isRefreshing = false
                showChild(R.id.pull_to_refresh)
            }
            newState == WebViewLoadingState.LOADING
                    && !binding.pullToRefresh.isRefreshing -> {
                showChild(R.id.progress_bar)
            }
            newState == WebViewLoadingState.ERROR -> {
                showChild(R.id.error_layout)
            }
        }
    }

    override fun onOverrideUrlLoading(url: String?): Boolean = isRedirectEnable

    override fun onPageCookiesLoaded(cookies: Map<String, String>?) {
        onCookieLoaded?.invoke(cookies)
    }

    override fun actionOnRedirectInsideWebView(webView: WebView, url: String?) {
        actionOnRedirect?.invoke(url, webView)
    }

    fun setBaseWebViewClient(isSSlPinningEnable: Boolean = false) {
        binding.webView.webViewClient = BaseWebViewClient(this, isSSlPinningEnable)
        binding.webView.webChromeClient = BaseChromeWebViewClient(onJsConfirm, onJsAlert, onJsPrompt, onJsError)
    }

    fun getWebView() = binding.webView

    /**
     * if url is null it changes to empty string
     * to prevent infinite LOADING state
     */
    fun loadUrl(url: String?, extraHeaders: Map<String,String>? = null) {
        binding.webView.loadUrl(url ?: "", extraHeaders?: emptyMap())
    }

    fun setState(newState: WebViewLoadingState) {
        onStateChanged(newState)
    }

    fun setOnWebViewDisplayedContentAction(action: () -> Unit) {
        binding.webView.onWebViewDisplayedContent = action
    }

    /**
     * loadWithOverviewMode loads the WebView completely zoomed out
     * useWideViewPort sets page size to fit screen
     * setInitialScale(1) prevents horizontal scrolling when
     * page has horizontal paddings
     */
    @SuppressLint("SetJavaScriptEnabled")
    open fun setWebViewPreferences() {
        binding.webView.apply {
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            with(settings) {
                loadsImagesAutomatically = true
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                setInitialScale(1)
            }
        }
    }

}
