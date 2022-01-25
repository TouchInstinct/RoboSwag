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

    init {
        context.withStyledAttributes(attrs, R.styleable.StyleableSubTextView, defStyleAttr, 0) {
            val substring = getString(R.styleable.StyleableSubTextView_subtext)
            val subtextStyle = getResourceIdOrNull(R.styleable.StyleableSubTextView_subtextStyle)

            if (subtextStyle != null && substring != null) {
                text = text.toStyleableSubstringText(
                        substring = substring,
                        styleId = subtextStyle,
                        context = context
                )
            }

        }
    }

}
