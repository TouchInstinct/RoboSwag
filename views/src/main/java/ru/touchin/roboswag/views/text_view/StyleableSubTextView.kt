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
class StyleableSubTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
): EllipsizeSpannableTextView(context, attrs, defStyleAttr) {

    private var styleId = typeface.style
    var substring: String? = null
        set(value) {
            field = value

            value?.let(this::setSubstringText)
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.StyleableSubTextView, defStyleAttr, 0) {
            val substring = getString(R.styleable.StyleableSubTextView_subtext)
            getResourceIdOrNull(R.styleable.StyleableSubTextView_subtextStyle)?.let { styleId = it }

            substring?.let(this@StyleableSubTextView::setSubstringText)
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        substring
                ?.let { super.setText(text?.toStyleableSubstringText(it, styleId, context), type) }
                ?:super.setText(text, type)

    }

    private fun setSubstringText(substring: String) {
        text = text.toStyleableSubstringText(substring, styleId, context)
    }

}
