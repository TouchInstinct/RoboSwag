package ru.touchin.roboswag.recyclerview_adapters.decorators

import android.graphics.Canvas
import android.graphics.Rect
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.util.set
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class GroupItemDecoration<TViewHolder : GroupItemDecoration.ViewHolder>(
        @RecyclerView.Orientation
        private val orientation: Int = RecyclerView.VERTICAL,
        private val predicate: (adapterPosition: Int) -> Boolean,
        private val onCreateViewHolder: (parent: ViewGroup) -> TViewHolder,
        private val onBindViewHolder: (adapterPosition: Int, TViewHolder) -> Unit
) : RecyclerView.ItemDecoration() {

    private val viewHoldersPool = SparseArray<TViewHolder>()
    private val bounds = Rect()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val adapterPosition = parent.getChildAdapterPosition(view)
        if (predicate(adapterPosition)) {
            calculateOutRectPosition(adapterPosition, parent, outRect)
        } else {
            viewHoldersPool.remove(adapterPosition)
        }
    }

    @Suppress("detekt.NestedBlockDepth")
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        var isInvalidated = false
        loop@ for (child in parent.children) {
            val adapterPosition = parent.getChildAdapterPosition(child)
            val groupView = viewHoldersPool[adapterPosition]?.view
            when {
                predicate(adapterPosition) -> {
                    if (groupView != null) {
                        val layoutParams = groupView.layoutParams as ViewGroup.MarginLayoutParams
                        parent.getDecoratedBoundsWithMargins(child, bounds)
                        canvas.save()
                        translateCanvasByOrientation(canvas, parent, layoutParams)
                        groupView.draw(canvas)
                        canvas.restore()
                    } else {
                        isInvalidated = true
                        break@loop
                    }
                }
                groupView != null -> {
                    isInvalidated = true
                    break@loop
                }
            }
        }
        if (isInvalidated) {
            parent.invalidateItemDecorations()
        }
    }

    private fun calculateOutRectPosition(adapterPosition: Int, parent: RecyclerView, outRect: Rect) {
        val groupViewHolder = obtainViewHolder(adapterPosition, parent)
        onBindViewHolder(adapterPosition, groupViewHolder)
        val groupView = groupViewHolder.view
        val layoutParams = groupView.layoutParams as ViewGroup.MarginLayoutParams
        val widthSpec = getWidthChildMeasureSpec(parent, layoutParams, groupView)
        val heightSpec = getHeightChildMeasureSpec(parent, layoutParams, groupView)
        groupView.measure(widthSpec, heightSpec)
        groupView.layout(0, 0, groupView.measuredWidth, groupView.measuredHeight)
        when (orientation) {
            RecyclerView.VERTICAL -> outRect.top = groupView.measuredHeight
            RecyclerView.HORIZONTAL -> outRect.left = groupView.measuredWidth
        }
    }

    private fun translateCanvasByOrientation(canvas: Canvas, parent: RecyclerView, layoutParams: ViewGroup.MarginLayoutParams) {
        when (orientation) {
            RecyclerView.VERTICAL -> canvas.translate(parent.paddingLeft.toFloat() + layoutParams.leftMargin, bounds.top.toFloat())
            RecyclerView.HORIZONTAL -> canvas.translate(bounds.left.toFloat(), parent.paddingTop.toFloat() + layoutParams.topMargin)
        }
    }

    private fun obtainViewHolder(adapterPosition: Int, parent: RecyclerView) = viewHoldersPool[adapterPosition]
            ?: onCreateViewHolder(parent).also { viewHoldersPool[adapterPosition] = it }

    private fun getHeightChildMeasureSpec(parent: RecyclerView, layoutParams: ViewGroup.MarginLayoutParams, groupView: View): Int {
        return ViewGroup.getChildMeasureSpec(
                View.MeasureSpec.makeMeasureSpec(parent.measuredHeight, View.MeasureSpec.EXACTLY),
                parent.paddingTop + parent.paddingBottom + layoutParams.topMargin + layoutParams.bottomMargin,
                groupView.layoutParams.height
        )
    }

    private fun getWidthChildMeasureSpec(parent: RecyclerView, layoutParams: ViewGroup.MarginLayoutParams, groupView: View): Int {
        return ViewGroup.getChildMeasureSpec(
                View.MeasureSpec.makeMeasureSpec(parent.measuredWidth, View.MeasureSpec.EXACTLY),
                parent.paddingLeft + parent.paddingRight + layoutParams.leftMargin + layoutParams.rightMargin,
                groupView.layoutParams.width
        )
    }

    open class ViewHolder(val view: View) {
        fun <T : View> findViewById(@IdRes resId: Int): T = view.findViewById(resId)
    }

}
