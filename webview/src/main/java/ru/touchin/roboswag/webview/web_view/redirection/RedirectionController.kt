package ru.touchin.roboswag.webview.web_view.redirection

import ru.touchin.roboswag.webview.web_view.BaseWebView
import java.net.URL

/**
 * Set [RedirectionController] to [BaseWebView] to handle url redirections
 *
 * By default all redirections are dismissed. For example:
 *
 *        baseWebView.redirectionController = RedirectionController(
 *                hosts = listOf("www.petshop.ru"),
 *                paths = listOf("catalog")
 *        )
 */
class RedirectionController(
        private val regex: List<Regex>? = null,
        private val hosts: List<String>? = null,
        private val paths: List<String>? = null,
        private val queries: List<String>? = null,
        private val checkCondition: ((url: String) -> Boolean)? = null
) {

    fun shouldRedirectToUrl(rawUrl: String?): Boolean {
        if (rawUrl == null) return false

        if (checkCondition != null) return checkCondition.invoke(rawUrl)

        val url = URL(rawUrl)

        if (!regex.isNullOrEmpty()) return regex.any { rawUrl.matches(it) }

        if (!queries.isNullOrEmpty()) return queries.any { it in url.query }

        if (!paths.isNullOrEmpty()) return paths.any { it in url.path }

        if (!hosts.isNullOrEmpty()) return hosts.any { it == url.host }

        return false
    }
}
