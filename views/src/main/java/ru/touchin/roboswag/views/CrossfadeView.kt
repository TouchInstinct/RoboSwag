package ru.touchin.roboswag.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ViewAnimator
import androidx.annotation.IdRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.children

class CrossfadeView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ViewAnimator(context, attrs) {

    @IdRes
    private var defaultChild: Int = 0

    init {
        setInAnimation(context, R.anim.fade_in_animation)
        setOutAnimation(context, R.anim.fade_out_animation)

        context.withStyledAttributes(attrs, R.styleable.CrossfadeView, 0) {
            defaultChild = getResourceId(R.styleable.CrossfadeView_defaultChild, 0)
        }
    }

    fun showChild(@IdRes childId: Int) {
        children.forEachIndexed { index, view ->
            if (view.id == childId && displayedChild != index) {
                displayedChild = index
            }
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        if (child.id == defaultChild) {
            showChild(defaultChild)
        }
    }

}
