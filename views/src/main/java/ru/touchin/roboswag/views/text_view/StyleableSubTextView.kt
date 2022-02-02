package ru.touchin.roboswag.views.text_view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes
import ru.touchin.extensions.getResourceIdOrNull
import ru.touchin.roboswag.components.utils.spans.toStyleableSubstringText
import ru.touchin.roboswag.views.R

/**
 * A [android.widget.TextView] which support styling substrings of text
 */
open class StyleableSubTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : EllipsizeSpannableTextView(context, attrs, defStyleAttr) {

    protected var substringStyleId = typeface.style

    var substring: String? = null
        set(value) {
            field = value

            text = text // call setText after setting substring to reset substring
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.StyleableSubTextView, defStyleAttr, 0) {
            getResourceIdOrNull(R.styleable.StyleableSubTextView_subtextStyle)?.let { substringStyleId = it }
            substring = getString(R.styleable.StyleableSubTextView_subtext)
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        val spannableText = when (substring == null) {
            true -> text
            false -> text?.toStyleableSubstringText(substring.orEmpty(), substringStyleId, context)
        }

        super.setText(spannableText, type)
    }

}
