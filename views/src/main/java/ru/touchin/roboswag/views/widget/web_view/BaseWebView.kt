package ru.touchin.roboswag.views.widget.web_view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import androidx.core.content.withStyledAttributes
import androidx.core.widget.TextViewCompat
import ru.touchin.extensions.setOnRippleClickListener
import ru.touchin.roboswag.components.utils.UiUtils
import ru.touchin.roboswag.views.R
import ru.touchin.roboswag.views.databinding.BaseWebViewBinding
import ru.touchin.roboswag.views.widget.Switcher

open class BaseWebView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0
) : Switcher(context, attrs), WebViewCallback {

    private val binding = BaseWebViewBinding.inflate(LayoutInflater.from(context), this)

    var onWebViewLoaded: (() -> Unit)? = null
    var onWebViewRepeatButtonClicked: (() -> Unit)? = null
    var onWebViewScrolled: ((WebView, Int, Int) -> Unit)? = null

    init {
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

            errorRepeatButton.setOnRippleClickListener {
                onWebViewRepeatButtonClicked?.invoke()
            }
        }
    }

    init {
        binding.webView.onScrollChanged = { scrollX, scrollY, _, _ ->
            onWebViewScrolled?.invoke(binding.webView, scrollX, scrollY)
        }
        setWebViewPreferences()
    }

    override fun onStateChanged(newState: WebViewLoadingState) {
        when (newState) {
            WebViewLoadingState.LOADED -> {
                onWebViewLoaded?.invoke()
                showChild(R.id.web_view)
            }
            WebViewLoadingState.LOADING -> {
                showChild(R.id.progress_bar)
            }
            WebViewLoadingState.ERROR -> {
                showChild(R.id.error_layout)
            }
        }
    }

    fun setBaseWebViewClient() {
        binding.webView.webViewClient = BaseWebViewClient(this)
    }

    fun getWebView() = binding.webView

    fun loadUrl(url: String?) {
        binding.webView.loadUrl(url)
    }

    fun setState(newState: WebViewLoadingState) {
        onStateChanged(newState)
    }

    fun setOnWebViewDisplayedContentAction(action: () -> Unit) {
        binding.webView.onWebViewDisplayedContent = action
    }

    @SuppressLint("SetJavaScriptEnabled")
    open fun setWebViewPreferences() {
        binding.webView.apply {
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            with(settings) {
                loadsImagesAutomatically = true
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
            }
        }
    }

}
