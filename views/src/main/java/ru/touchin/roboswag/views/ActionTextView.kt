package ru.touchin.roboswag.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import ru.touchin.extensions.getResourceIdOrNull
import ru.touchin.roboswag.components.utils.movementmethods.ClickableMovementMethod
import ru.touchin.roboswag.components.utils.spans.toClickableSubstringText

class ActionTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var onClickAction: () -> Unit = { }

    init {
        isClickable = false
        isLongClickable = false
        highlightColor = Color.TRANSPARENT

        movementMethod = ClickableMovementMethod

        context.withStyledAttributes(attrs, R.styleable.ActionTextView, defStyleAttr, 0) {
            val actionText = getString(R.styleable.ActionTextView_actionText).orEmpty()
            val actionTextStyle = getResourceIdOrNull(R.styleable.ActionTextView_actionTextStyle)
            val isUnderlineText = getBoolean(R.styleable.ActionTextView_isUnderlineText, false)

            text = text.toClickableSubstringText(
                    substring = actionText,
                    clickAction = { onClickAction.invoke() },
                    styleId = actionTextStyle,
                    isUnderlineText = isUnderlineText
            )
        }
    }

    fun setOnActionClickListener(onClickAction: () -> Unit) {
        this.onClickAction = onClickAction
    }

}
