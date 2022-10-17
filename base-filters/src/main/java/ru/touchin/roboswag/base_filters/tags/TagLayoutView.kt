package ru.touchin.roboswag.base_filters.tags

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.children
import com.google.android.material.chip.ChipGroup
import ru.touchin.roboswag.base_filters.R
import ru.touchin.roboswag.base_filters.SelectionType
import ru.touchin.roboswag.base_filters.databinding.LayoutMultiLineTagGroupBinding
import ru.touchin.roboswag.base_filters.databinding.LayoutSingleLineTagGroupBinding
import ru.touchin.roboswag.base_filters.tags.model.FilterItem
import ru.touchin.roboswag.base_filters.tags.model.FilterProperty
import ru.touchin.roboswag.components.utils.UiUtils
import ru.touchin.roboswag.components.utils.px
import kotlin.properties.Delegates

typealias PropertySelectedAction = (FilterProperty) -> Unit
typealias FilterMoreAction = (FilterItem) -> Unit

class TagLayoutView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var filterItem: FilterItem by Delegates.notNull()

    private var tagsContainer: ChipGroup by Delegates.notNull()

    private var propertySelectedAction: PropertySelectedAction? = null
    private var moreValuesAction: FilterMoreAction? = null

    private var selectionType = SelectionType.MULTI_SELECT
    private var isSingleLine = false
    private var tagSpacingHorizontalDp: Int = 0
    private var tagSpacingVerticalDp: Int = 0

    @LayoutRes
    private var tagLayout: Int = R.layout.layout_default_tag

    private var moreTagText: String = ""
    private var maxTagCount = Int.MAX_VALUE

    @LayoutRes
    private var moreTagLayout: Int = tagLayout

    private fun inflateAndGetChipGroup(isSingleLine: Boolean): ChipGroup = when (isSingleLine) {
        true -> LayoutSingleLineTagGroupBinding.inflate(LayoutInflater.from(context), this, true).tagGroup
        false -> LayoutMultiLineTagGroupBinding.inflate(LayoutInflater.from(context), this, true).tagGroup
    }

    private fun createTag(property: FilterProperty): TagView {
        val tagView = UiUtils.inflate(tagLayout, this)
        require(tagView is TagView) { "Layout for tag must contain TagView as root view" }

        return tagView.apply {
            text = property.title
            isChecked = property.isSelected
            tagId = property.id

            setOnCheckAction { view, isChecked ->
                when {
                    selectionType == SelectionType.SINGLE_SELECT && isChecked -> clearCheck(property.id)
                    selectionType == SelectionType.MULTI_SELECT && isChecked -> clearExcludedCheck(property)
                }
                view.isChecked = isChecked
                propertySelectedAction?.invoke(property.copyWithSelected(isSelected = isChecked))
            }
        }
    }

    private fun createMoreTag(filter: FilterItem): View {
        val moreTag = UiUtils.inflate(moreTagLayout, this)
        require(moreTag is TextView) { "Layout for more tag must contain TextView as root view" }

        return moreTag.apply {
            text = moreTagText
            setOnClickListener { moreValuesAction?.invoke(filter) }
        }
    }

    private fun clearCheck(selectedId: Int) {
        tagsContainer.children.forEach { tagView ->
            if (tagView is TagView && tagView.tagId != selectedId) {
                tagView.isChecked = false
            }
        }
    }

    private fun clearExcludedCheck(property: FilterProperty) {
        val excludingIds = property.excludes.map { it.id }

        tagsContainer.children.forEach { tagView ->
            if (tagView is TagView && tagView.tagId in excludingIds) {
                tagView.isChecked = false
            }
        }
    }

    inner class Builder(private val filterItem: FilterItem) {

        fun onMoreValuesAction(action: FilterMoreAction) = apply {
            moreValuesAction = action
        }

        fun onPropertySelectedAction(action: PropertySelectedAction) = apply {
            propertySelectedAction = action
        }

        fun setMaxTagCount(count: Int) = apply {
            maxTagCount = count
        }

        fun setSpacingHorizontal(horizontalSpacingDp: Int) = apply {
            tagSpacingHorizontalDp = horizontalSpacingDp
        }

        fun setSpacingVertical(verticalSpacingDp: Int) = apply {
            tagSpacingVerticalDp = verticalSpacingDp
        }

        fun setSpacing(value: Int) = apply {
            tagSpacingHorizontalDp = value
            tagSpacingVerticalDp = value
        }

        fun setSelectionType(type: SelectionType) = apply {
            selectionType = type
        }

        fun isSingleLine(value: Boolean) = apply {
            isSingleLine = value
        }

        fun setTagLayout(@LayoutRes layoutId: Int) = apply {
            tagLayout = layoutId
        }

        fun setMoreTagLayout(@LayoutRes layoutId: Int, text: String) = apply {
            moreTagLayout = layoutId
            moreTagText = text
        }

        fun build() {
            this@TagLayoutView.filterItem = filterItem
            tagsContainer = inflateAndGetChipGroup(isSingleLine)

            with(tagsContainer) {
                removeAllViews()

                this.isSingleLine = isSingleLine

                chipSpacingHorizontal = tagSpacingHorizontalDp.px
                chipSpacingVertical = tagSpacingVerticalDp.px

                filterItem.properties.take(maxTagCount).forEach { property ->
                    addView(createTag(property))
                }

                if (filterItem.properties.size > maxTagCount) {
                    addView(createMoreTag(filterItem))
                }
            }
        }
    }
}
