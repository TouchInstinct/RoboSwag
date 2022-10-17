package ru.touchin.roboswag.base_filters.tags

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox

class TagView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
): AppCompatCheckBox(context, attrs, defStyleAttr) {

    var tagId: Int? = null

    private var action: (( view: TagView, isChecked: Boolean) -> Unit)? = null

    init {
        setOnClickListener {
            action?.invoke(this, isChecked)
        }
    }

    fun setOnCheckAction(action: (view: TagView, isChecked: Boolean) -> Unit) {
        this.action = action
    }
}
