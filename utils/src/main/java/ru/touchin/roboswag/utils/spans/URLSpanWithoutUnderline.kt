package ru.touchin.roboswag.utils.spans

import android.text.TextPaint
import android.text.style.URLSpan

open class URLSpanWithoutUnderline(url: String) : URLSpan(url) {

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
    }
}
