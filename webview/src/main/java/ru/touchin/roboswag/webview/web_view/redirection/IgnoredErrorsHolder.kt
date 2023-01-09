package ru.touchin.roboswag.webview.web_view.redirection

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest

/**
 * Define some urls here which errors should be ignored while redirection to prevent show error screen
 * Additionally you can add custom error ignoring condition
 */
class IgnoredErrorsHolder(
        private val ignoredUrlsList: List<String> = listOf(
                "https://stats.g.doubleclick.net/"
        ),
        private val ignoreCondition: ((WebResourceRequest, WebResourceError) -> Boolean)? = null
) {

    constructor(vararg urls: String) : this(urls.toList())

    fun shouldIgnoreError(request: WebResourceRequest, error: WebResourceError): Boolean {
        if (ignoreCondition != null) return ignoreCondition.invoke(request, error)

        val url = request.url.toString()

        return ignoredUrlsList.any { url in it }
    }
}
