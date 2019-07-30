package ru.touchin.roboswag.components.utils.movementmethods

import android.text.Selection
import android.text.Spannable
import android.text.method.BaseMovementMethod
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.widget.TextView

/**
 * Created by Daniil Borisovskii on 29/07/2019.
 * Helper object for make clickable a part of SpannableString with ClickableSpan using custom handler
 *
 * Example usage:
 *
 * findViewById<TextView>(R.id.text_view_id).apply {
 *     text = SpannableString(sourceText).also {
 *         it.setSpan(
 *                 object : ClickableSpan() {
 *                     override fun onClick(widget: View) {
 *                         //Do what you need
 *                     }
 *                 },
 *                 startIndexOfClickablePart,
 *                 endIndexOfClickablePart,
 *                 Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
 *         )
 *     }
 *     movementMethod = ClickableMovementMethod
 *     isClickable = false
 *     isLongClickable = false
 * }
 */
object ClickableMovementMethod : BaseMovementMethod() {

    override fun canSelectArbitrarily() = false

    override fun onTouchEvent(widget: TextView, text: Spannable, event: MotionEvent): Boolean {
        val action: Int = event.actionMasked
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            val x = event.x.toInt()
                    .minus(widget.totalPaddingLeft)
                    .plus(widget.scrollX)
            val y = event.y.toInt()
                    .minus(widget.totalPaddingTop)
                    .plus(widget.scrollY)

            val layout = widget.layout
            val line = layout.getLineForVertical(y)
            val off = layout.getOffsetForHorizontal(line, x.toFloat())

            val link = text.getSpans(off, off, ClickableSpan::class.java)
            if (link.isNotEmpty()) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget)
                } else {
                    Selection.setSelection(text, text.getSpanStart(link[0]), text.getSpanEnd(link[0]))
                }
                return true
            } else {
                Selection.removeSelection(text)
            }
        }

        return false

    }

    override fun initialize(widget: TextView?, text: Spannable?) = Selection.removeSelection(text)
}
