package ru.touchin.roboswag.webview.web_view.redirection

import ru.touchin.roboswag.webview.web_view.BaseWebView
import java.net.URL

/**
 * Set [RedirectionController] to [BaseWebView] to handle url redirections
 *
 * By default all redirections are dismissed. Any matched condition will perform redirect.
 * If you need multiple conditions -> use [RedirectionCondition.CompositeCondition]
 *
 *        webView.redirectionController = RedirectionController(
 *                CompositeCondition(ByHost("www.petshop.ru") + ByPath("catalog")),
 *                ByPredicate { url -> checkUrl(url) }
 *        )
 */
class RedirectionController(private val conditions: List<RedirectionCondition> = emptyList()) {

    constructor(vararg redirectionConditions: RedirectionCondition)
            : this(redirectionConditions.toList())

    fun shouldRedirectToUrl(rawUrl: String?): Boolean {
        val url = URL(rawUrl)

        return conditions.any { it.shouldRedirect(url) }
    }
}

/**
 * Class with base redirection conditions
 */
sealed class RedirectionCondition {

    operator fun plus(other: RedirectionCondition) = listOf(this, other)

    abstract fun shouldRedirect(url: URL): Boolean

    class ByRegex(private val regex: Regex) : RedirectionCondition() {
        override fun shouldRedirect(url: URL) = url.toString().matches(regex)
    }

    class ByHost(private val host: String) : RedirectionCondition() {
        override fun shouldRedirect(url: URL) = url.host == host
    }

    class ByPath(private val path: String) : RedirectionCondition() {
        override fun shouldRedirect(url: URL) = path in url.path
    }

    class ByQuery(private val query: String) : RedirectionCondition() {
        override fun shouldRedirect(url: URL) = query in url.query
    }

    class ByPredicate(private val predicate: (URL) -> Boolean) : RedirectionCondition() {
        override fun shouldRedirect(url: URL) = predicate.invoke(url)
    }

    /**
     * All of the conditions should be matched to perform redirect
     */
    class CompositeCondition(private val conditions: List<RedirectionCondition>) : RedirectionCondition() {
        override fun shouldRedirect(url: URL): Boolean = conditions.all { it.shouldRedirect(url) }
    }
}
