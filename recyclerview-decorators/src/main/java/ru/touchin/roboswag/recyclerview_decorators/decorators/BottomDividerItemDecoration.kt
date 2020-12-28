package ru.touchin.roboswag.recyclerview_decorators.decorators

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView

open class BottomDividerItemDecoration(
        context: Context,
        @DrawableRes drawableId: Int? = null,
        override val predicate: ((position: Int) -> Boolean) = { true },
        override val startMargin: Int = 0,
        override val endMargin: Int = 0,
        override val offset: Boolean = true,
        override val showOnLastItem: Boolean = false
) : DividerItemDecoration(context, drawableId, predicate, startMargin, endMargin, offset, showOnLastItem) {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (offset && predicate(position) && (position != state.itemCount - 1 || showOnLastItem)) {
            outRect.set(0, 0, 0, divider.intrinsicHeight)
        }
    }

    override fun getDividerTop(child: View): Int = getDividerBottom(child) - divider.intrinsicHeight

    override fun getDividerBottom(child: View): Int = bounds.bottom + child.translationY.toInt()

}
