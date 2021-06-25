package ru.touchin.roboswag.components.utils.spans

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.URLSpan
import android.text.util.Linkify
import android.view.View
import androidx.core.text.HtmlCompat
import ru.touchin.extensions.indexesOf

/**
 * Convert text with 'href' tags and raw links to spanned text with clickable URLSpan.
 */
fun String.getSpannedTextWithUrls(
        removeUnderline: Boolean = true,
        flags: Int = HtmlCompat.FROM_HTML_MODE_COMPACT
): Spanned {
    // HtmlCompat.fromHtml doesn't respect line breaks
    val text = this.replace(lineBreakRegex, "<br/>")
    val spannableText = SpannableString(HtmlCompat.fromHtml(text, flags))

    // Linkify removes all previous URLSpan's, we need to save all created spans for reapply after Linkify
    val spans = spannableText.getUrlSpans()
    Linkify.addLinks(spannableText, Linkify.WEB_URLS)
    spans.forEach {
        spannableText.setSpan(it.span, it.start, it.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    if (!removeUnderline) {
        spannableText.getUrlSpans()
                .forEach { urlSpan ->
                    spannableText.removeSpan(urlSpan.span)
                    spannableText.setSpan(
                            URLSpanWithoutUnderline(urlSpan.span.url),
                            urlSpan.start,
                            urlSpan.end,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
    }
    return spannableText
}

private val lineBreakRegex by lazy(LazyThreadSafetyMode.NONE) {
    "\r?\n".toRegex()
}

private fun SpannableString.getUrlSpans() = getSpans(0, length, URLSpan::class.java)
        .map { UrlSpanWithBorders(it, this.getSpanStart(it), this.getSpanEnd(it)) }

private data class UrlSpanWithBorders(val span: URLSpan, val start: Int, val end: Int)

/**
 * Find substring inside string (for example in TextView) and fill it with ClickableSpan
 */
fun CharSequence.getClickableSubstring(substring: String, clickAction: () -> Unit, color: Int? = null) = SpannableString(this)
        .apply {
            indexesOf(substring)?.let { (startSpan, endSpan) ->
                setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        clickAction.invoke()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }
                }, startSpan, endSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                color?.let { setSpan(ForegroundColorSpan(it), startSpan, endSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) }
            }
        }
