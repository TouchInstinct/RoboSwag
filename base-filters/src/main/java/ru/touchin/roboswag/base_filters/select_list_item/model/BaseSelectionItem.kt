package ru.touchin.roboswag.base_filters.select_list_item.model

abstract class BaseSelectionItem(
        open val id: Int,
        open val title: String,
        open val isSelected: Boolean
) {

    abstract fun isItemTheSame(compareItem: BaseSelectionItem): Boolean

    abstract fun isContentTheSame(compareItem: BaseSelectionItem): Boolean

    abstract fun <ItemType>copyWithSelection(isSelected: Boolean): ItemType
}
