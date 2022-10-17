package ru.touchin.roboswag.base_filters.select_list_item.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.touchin.roboswag.base_filters.select_list_item.model.BaseSelectionItem
import ru.touchin.roboswag.base_filters.SelectionType
import ru.touchin.roboswag.recyclerview_adapters.adapters.DelegationListAdapter

class SheetSelectionAdapter<ItemType : BaseSelectionItem>(
        onItemSelectAction: (ItemType) -> Unit,
        selectionType: SelectionType,
        factory: HolderFactoryType<ItemType>
) : DelegationListAdapter<BaseSelectionItem>(object : DiffUtil.ItemCallback<BaseSelectionItem>() {

    override fun areItemsTheSame(oldItem: BaseSelectionItem, newItem: BaseSelectionItem): Boolean =
            oldItem.isItemTheSame(newItem)

    override fun areContentsTheSame(oldItem: BaseSelectionItem, newItem: BaseSelectionItem): Boolean =
            oldItem.isContentTheSame(newItem)

}) {

    init {
        addDelegate(SheetSelectionDelegate(
                onItemSelectAction = onItemSelectAction,
                selectionType = selectionType,
                factory = factory
        ))
    }

}
