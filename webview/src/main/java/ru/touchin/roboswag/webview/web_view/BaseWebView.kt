package ru.touchin.roboswag.webview.web_view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.webkit.CookieManager
import androidx.core.content.withStyledAttributes
import androidx.core.widget.TextViewCompat
import ru.touchin.extensions.getColorOrNull
import ru.touchin.extensions.getResourceIdOrNull
import ru.touchin.extensions.setOnRippleClickListener
import ru.touchin.roboswag.views.widget.Switcher
import ru.touchin.roboswag.webview.R
import ru.touchin.roboswag.webview.databinding.BaseWebViewBinding
import ru.touchin.roboswag.webview.web_view.redirection.IgnoredErrorsHolder
import ru.touchin.roboswag.webview.web_view.redirection.RedirectionController

open class BaseWebView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0
) : Switcher(context, attrs), WebViewCallback {

    private val binding = BaseWebViewBinding.inflate(LayoutInflater.from(context), this)

    private var isCircleProgressBar = true

    private var isPullToRefreshEnabled = false
        set(value) {
            binding.pullToRefresh.isEnabled = value
            binding.pullToRefresh.isRefreshing = false
            field = value
        }

    var redirectionController = RedirectionController()

    init {
        initAttributes(attrs, defStyleAttr)
        binding.pullToRefresh.isEnabled = isPullToRefreshEnabled

        binding.apply {
            pullToRefresh.setOnRefreshListener {
                webView.reload()
            }
            errorRepeatButton.setOnRippleClickListener {
                onRepeatButtonClicked()
            }
            webView.onScrollChanged = { scrollX, scrollY, _, _ ->
                onWebViewScrolled(scrollX, scrollY)
            }
            setWebViewPreferences()
        }
    }

    override fun onStateChanged(newState: WebViewState) {
        when {
            newState == WebViewState.SUCCESS -> {
                binding.pullToRefresh.isRefreshing = false
                showChild(R.id.pull_to_refresh)
            }
            newState == WebViewState.LOADING && !binding.pullToRefresh.isRefreshing -> {
                showChild(if (isCircleProgressBar) R.id.progress_bar else R.id.linear_progress_bar)
            }
            newState == WebViewState.ERROR -> {
                showChild(R.id.error_layout)
            }
        }
    }

    override fun onOverrideUrlLoading(url: String?): Boolean =
            redirectionController.shouldRedirectToUrl(url)

    override fun onRepeatButtonClicked() {
        loadUrl(getWebView().url)
    }

    override fun onProgressChanged(progress: Int) {
        binding.linearProgressBar.progress = progress
    }

    fun setBaseWebViewClient(
            callback: WebViewCallback = this,
            ignoredErrorsHolder: IgnoredErrorsHolder = IgnoredErrorsHolder()
    ) {
        binding.webView.webViewClient = BaseWebViewClient(callback, ignoredErrorsHolder)
        binding.webView.webChromeClient = BaseChromeWebViewClient(callback)
    }

    fun handleBackPressed(closeScreenAction: () -> Unit) {
        with(getWebView()) { if (canGoBack()) goBack() else closeScreenAction.invoke() }
    }

    fun getWebView() = binding.webView

    /**
     * if url is null it changes to empty string
     * to prevent infinite LOADING state
     */
    fun loadUrl(url: String?, extraHeaders: Map<String, String> = emptyMap(), cookies: Map<String, String> = mapOf()) {
        CookieManager.getInstance().apply {
            cookies.forEach {
                setCookie(url, "${it.key}=${it.value}")
            }
        }
        binding.webView.loadUrl(url ?: "", extraHeaders)
    }

    /**
     * @param htmlString raw html string to be loaded into WebView
     * @param cssString raw css string to add styles
     * @param cssFileName name for .css file which can be placed in assets folder
     * @param styleDeps dependencies for styles which must be included into html,
     * e.g. </link rel="preconnect" href="https://fonts.googleapis.com"/>
     */
    fun loadHtmlContent(
            htmlString: String,
            cssString: String = "",
            cssFileName: String = "",
            styleDeps: String = ""
    ) {
        val indexOfHead = htmlString
                .indexOf("</head>", ignoreCase = true)
                .takeIf { it >= 0 } ?: 0

        val styledHtml = StringBuilder(htmlString)
                .insert(indexOfHead, styleDeps)
                .insert(indexOfHead, "<style>$cssString</style>")
                .insert(indexOfHead, "<link href=\"$cssFileName\" type=\"text/css\" rel=\"stylesheet\"/>")

        getWebView().loadDataWithBaseURL(
                "file:///android_asset/".takeIf { cssFileName.isNotEmpty() },
                styledHtml.toString(),
                "text/html",
                "utf-8",
                null
        )
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

    private fun initAttributes(attrs: AttributeSet?, defStyleAttr: Int) = with(binding) {
        context.withStyledAttributes(attrs, R.styleable.BaseWebView, defStyleAttr, 0) {
            getResourceIdOrNull(R.styleable.BaseWebView_errorTextAppearance)?.let {
                TextViewCompat.setTextAppearance(errorText, it)
            }
            getResourceIdOrNull(R.styleable.BaseWebView_repeatButtonTextAppearance)?.let {
                TextViewCompat.setTextAppearance(errorRepeatButton, it)
            }
            getResourceIdOrNull(R.styleable.BaseWebView_repeatButtonBackground)?.let {
                errorRepeatButton.setBackgroundResource(it)
            }
            getColorOrNull(R.styleable.BaseWebView_progressBarTintColor)?.let {
                progressBar.indeterminateTintList = ColorStateList.valueOf(it)
                linearProgressBar.indeterminateTintList = ColorStateList.valueOf(it)
            }
            getResourceIdOrNull(R.styleable.BaseWebView_repeatButtonBackground)?.let {
                setBackgroundResource(getResourceId(R.styleable.BaseWebView_screenBackground, 0))
            }

            getString(R.styleable.BaseWebView_errorText)?.let { errorText.text = it }
            getString(R.styleable.BaseWebView_repeatButtonText)?.let { errorRepeatButton.text = it }

            isCircleProgressBar = getBoolean(R.styleable.BaseWebView_isCircleProgressBar, true)
            isPullToRefreshEnabled = getBoolean(R.styleable.BaseWebView_isPullToRefreshEnabled, false)

            onStateChanged(WebViewState.LOADING)
        }
    }

}
