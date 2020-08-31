package ru.touchin.roboswag.views.decorators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.res.getDrawableOrThrow
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import ru.touchin.roboswag.components.utils.px

abstract class DividerItemDecoration(
        context: Context,
        @DrawableRes drawableId: Int? = null,
        protected open val predicate: ((position: Int) -> Boolean) = { true },
        protected open val startMargin: Int = 0,
        protected open val endMargin: Int = 0,
        protected open val offset: Boolean = true,
        protected open val showOnLastItem: Boolean = false
) : RecyclerView.ItemDecoration() {

    protected val bounds = Rect()
    protected val divider: Drawable

    init {
        if (drawableId == null) {
            context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider)).apply {
                divider = getDrawableOrThrow(0)
                recycle()
            }
        } else {
            divider = context.getDrawable(drawableId)!!
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (offset) {
            drawDivider(canvas, parent, state)
        }
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (!offset) {
            drawDivider(canvas, parent, state)
        }
    }

    private fun drawDivider(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        canvas.save()
        parent.children.forEach { child ->
            val position = parent.getChildAdapterPosition(child)
            if (predicate(position) && (position != state.itemCount - 1 || showOnLastItem)) {
                parent.getDecoratedBoundsWithMargins(child, bounds)
                val top = getDividerTop(child)
                val bottom = getDividerBottom(child)
                divider.setBounds(
                        bounds.left + startMargin,
                        top,
                        bounds.right - (endMargin.toFloat().px).toInt(),
                        bottom
                )
                divider.draw(canvas)
            }
        }
        canvas.restore()
    }

    abstract fun getDividerTop(child: View): Int

    abstract fun getDividerBottom(child: View): Int

}
