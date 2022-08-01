package ru.touchin.roboswag.base_filters.select_list_item.adapter

import android.view.ViewGroup
import ru.touchin.roboswag.base_filters.select_list_item.ListSelectionView.SelectionType
import ru.touchin.roboswag.base_filters.select_list_item.model.BaseSelectionItem
import ru.touchin.roboswag.recyclerview_adapters.adapters.ItemAdapterDelegate

typealias HolderFactoryType<ItemType> = (ViewGroup, (ItemType) -> Unit, SelectionType) -> BaseSelectionViewHolder<ItemType>

class SheetSelectionDelegate<ItemType>(
        private val onItemSelectAction: (ItemType) -> Unit,
        private val selectionType: SelectionType,
        private val factory: HolderFactoryType<ItemType>
) : ItemAdapterDelegate<BaseSelectionViewHolder<ItemType>, ItemType>()
        where ItemType : BaseSelectionItem {

    override fun onCreateViewHolder(parent: ViewGroup): BaseSelectionViewHolder<ItemType> =
            factory.invoke(parent, onItemSelectAction, selectionType)

    override fun onBindViewHolder(
            holder: BaseSelectionViewHolder<ItemType>,
            item: ItemType,
            adapterPosition: Int,
            collectionPosition: Int,
            payloads: MutableList<Any>
    ) = holder.bind(item)

}
