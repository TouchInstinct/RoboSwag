package ru.touchin.roboswag.base_filters.select_list_item.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.touchin.roboswag.base_filters.select_list_item.ListSelectionView
import ru.touchin.roboswag.base_filters.select_list_item.OnItemSelectedListener
import ru.touchin.roboswag.base_filters.select_list_item.model.RowSelectionItem
import ru.touchin.roboswag.recyclerview_adapters.adapters.DelegationListAdapter

class SheetSelectionAdapter(
        onItemSelectAction: OnItemSelectedListener,
        selectionType: ListSelectionView.SelectionType
): DelegationListAdapter<RowSelectionItem>(object : DiffUtil.ItemCallback<RowSelectionItem>() {

    override fun areItemsTheSame(oldItem: RowSelectionItem, newItem: RowSelectionItem): Boolean =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RowSelectionItem, newItem: RowSelectionItem): Boolean =
            oldItem == newItem

}) {

    init {
        addDelegate(SheetSelectionDelegate(onItemSelectAction, selectionType))
    }

}
