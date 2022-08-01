package ru.touchin.roboswag.base_filters.select_list_item.adapter

import android.view.View
import android.view.ViewGroup
import ru.touchin.roboswag.base_filters.databinding.SelectionItemBinding
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

class SelectionItemViewHolder<ItemType: BaseSelectionItem>(private val binding: SelectionItemBinding,
                              private val onItemSelectAction: (ItemType) -> Unit,
                              private val selectionType: SelectionType
                              ) : BaseSelectionViewHolder<ItemType>(binding.root) {

    override fun bind(item: ItemType) {
        binding.run {
            val checkListener = View.OnClickListener {
                itemRadiobutton.isChecked = true
                onItemSelectAction.invoke(item.copyWithSelection(isSelected = when (selectionType) {
                    SelectionType.SINGLE_SELECT -> true
                    else -> !item.isSelected
                }))
            }

            itemTitle.text = item.title
            root.setOnClickListener(checkListener)

            itemRadiobutton.setOnClickListener(checkListener)
            itemRadiobutton.isChecked = item.isSelected
        }
    }

}

