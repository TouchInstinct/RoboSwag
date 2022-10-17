package ru.touchin.roboswag.base_filters.select_list_item.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DefaultSelectionItem(
        override val id: Int,
        override val title: String,
        override val isSelected: Boolean = false
) : BaseSelectionItem(id, title, isSelected), Parcelable {

    override fun isItemTheSame(compareItem: BaseSelectionItem): Boolean = when {
        compareItem is DefaultSelectionItem && id == compareItem.id -> true
        else -> false
    }

    override fun isContentTheSame(compareItem: BaseSelectionItem): Boolean =
            this == compareItem

    @Suppress("UNCHECKED_CAST")
    override fun <ItemType> copyWithSelection(isSelected: Boolean): ItemType =
            this.copy(isSelected = isSelected) as ItemType
}
