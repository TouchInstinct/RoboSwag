package ru.touchin.roboswag.base_filters.tags

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import com.google.android.material.chip.ChipGroup
import ru.touchin.roboswag.base_filters.R
import ru.touchin.roboswag.base_filters.SelectionType
import ru.touchin.roboswag.base_filters.databinding.TagSelectionLayoutBinding
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

    private val binding = TagSelectionLayoutBinding.inflate(LayoutInflater.from(context), this)
    private var filterItem: FilterItem by Delegates.notNull()

    private var tagsContainer: ChipGroup = binding.multiLineTagGroup

    private var propertySelectedAction: PropertySelectedAction? = null
    private var moreValuesAction: FilterMoreAction? = null

    private var selectionType = SelectionType.MULTI_SELECT
    private var isSingleLine = false
    private var tagSpacingHorizontalDp: Int = 0
    private var tagSpacingVerticalDp: Int = 0

    @LayoutRes
    private var tagLayout: Int = R.layout.layout_default_tag

    private var moreTagText: String = ""
    private var maxTagCount: Int? = null

    @LayoutRes
    private var moreTagLayout: Int? = null

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

    fun build(filterItem: FilterItem) {
        this.filterItem = filterItem
        tagsContainer = getTagView(isSingleLine)

        with(tagsContainer) {
            removeAllViews()

            this.isSingleLine = isSingleLine

            chipSpacingHorizontal = tagSpacingHorizontalDp.px
            chipSpacingVertical = tagSpacingVerticalDp.px

            val properties = maxTagCount
                    ?.let { count -> filterItem.properties.take(count) }
                    ?: filterItem.properties
            properties.forEach { property ->
                addView(createTag(property))
            }

            if (maxTagCount != null && filterItem.properties.size > maxTagCount!!) {
                createMoreChip(filterItem)?.let { addView(it) }
            }
        }
    }

    private fun getTagView(isSingleLine: Boolean): ChipGroup {
        binding.lineTagContainer.isVisible = isSingleLine
        binding.multiLineTagGroup.isVisible = !isSingleLine
        return if (isSingleLine) binding.singleLineTagGroup else binding.multiLineTagGroup
    }

    private fun createTag(property: FilterProperty): TagView =
            (UiUtils.inflate(tagLayout, this) as? TagView)?.apply {
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
            } ?: throw IllegalArgumentException("Layout for tag must be extended from TagView")

    private fun createMoreChip(filter: FilterItem): View? = moreTagLayout?.let {
        (UiUtils.inflate(it, this) as? TextView)?.apply {
            text = moreTagText
            setOnClickListener { moreValuesAction?.invoke(filter) }
        }
    }

    private fun clearCheck(selectedId: Int) {
        for (i in 0 until tagsContainer.childCount) {
            val child = tagsContainer.getChildAt(i)
            if (child is TagView && child.tagId != selectedId) {
                child.isChecked = false
            }
        }
    }

    private fun clearExcludedCheck(property: FilterProperty) {
        val excludingIds = property.excludes.map { it.id }

        for (i in 0 until tagsContainer.childCount) {
            val child = tagsContainer.getChildAt(i)
            if (child is TagView && child.tagId in excludingIds) {
                child.isChecked = false
            }
        }
    }
}
