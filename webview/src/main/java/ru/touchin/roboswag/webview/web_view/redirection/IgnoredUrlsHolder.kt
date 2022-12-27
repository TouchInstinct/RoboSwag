package ru.touchin.roboswag.webview.web_view.redirection

/**
 * Define some urls here which errors should be ignored while redirection to prevent show error screen
 */
class IgnoredUrlsHolder(
        private val ignoredUrlsList: List<String> = listOf(
                "https://stats.g.doubleclick.net/"
        )
) {

    constructor(vararg urls: String) : this(urls.toList())

    fun shouldIgnoreError(url: String) = ignoredUrlsList.any { url in it }
}
