package ru.touchin.roboswag.views.text_view

import android.content.Context
import android.graphics.Color
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import androidx.core.text.toSpannable
import ru.touchin.extensions.indexesOf
import ru.touchin.roboswag.components.utils.movementmethods.ClickableMovementMethod
import ru.touchin.roboswag.views.R

class MultipleActionTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val onClickActions = mutableListOf<SubstringClickAction>()

    @ColorInt
    private var actionColor = currentTextColor

    private var isUnderlineText = false

    init {
        isClickable = false
        isLongClickable = false
        highlightColor = Color.TRANSPARENT
        movementMethod = ClickableMovementMethod

        context.withStyledAttributes(attrs, R.styleable.MultipleActionTextView, defStyleAttr, 0) {
            actionColor = getColor(R.styleable.MultipleActionTextView_actionColor, currentTextColor)
            isUnderlineText = getBoolean(R.styleable.MultipleActionTextView_isUnderlineText, false)
        }
    }

    fun configure(builderAction: MutableList<SubstringClickAction>.() -> Unit) {
        onClickActions.apply(builderAction)
        applyActionSpans()
    }

    private fun applyActionSpans() {
        text = text.toSpannable().apply {
            onClickActions.forEach { (substring, action) ->

                indexesOf(substring)?.let { (startSpan, endSpan) ->

                    setSpan(object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            action.invoke()
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.isUnderlineText = isUnderlineText
                            ds.color = actionColor
                        }
                    }, startSpan, endSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }
    }

    data class SubstringClickAction(val substring: String, val action: () -> Unit)
}
