package ru.touchin.roboswag.views.text_view

import android.content.Context
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * A [android.widget.TextView] which support text as SpannableString with ellipsize implementation
 * @author Rinat Nurmukhametov
 */
open class EllipsizeSpannableTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        private const val THREE_DOTS = "..."
        private const val THREE_DOTS_LENGTH = THREE_DOTS.length
    }

    private var spannableStringBuilder = SpannableStringBuilder()

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (layout.lineCount >= maxLines) {
            val charSequence = text
            val lastCharDown: Int = layout.getLineVisibleEnd(maxLines - 1)

            if (lastCharDown >= THREE_DOTS_LENGTH && charSequence.length > lastCharDown) {
                spannableStringBuilder.clear()

                spannableStringBuilder
                        .append(charSequence.subSequence(0, lastCharDown - THREE_DOTS_LENGTH))
                        .append(THREE_DOTS)

                text = spannableStringBuilder
            }
        }

        super.onLayout(changed, left, top, right, bottom)
    }
}
