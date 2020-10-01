package ru.touchin.roboswag.views.widget.web_view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class CustomWebView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    var onWebViewDisplayedContent: (() -> Unit)? = null
    var onScrollChanged: ((Int, Int, Int, Int) -> Unit)? = null

    // https://stackoverflow.com/a/14678910
    override fun invalidate() {
        super.invalidate()

        if (contentHeight > 0) {
            onWebViewDisplayedContent?.invoke()
        }

    }

    override fun onScrollChanged(scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY)
        onScrollChanged?.invoke(scrollX, scrollY, oldScrollX, oldScrollY)
    }

}
