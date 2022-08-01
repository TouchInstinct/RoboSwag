package ru.touchin.roboswag.base_filters.select_list_item.adapter

import android.view.View
import ru.touchin.roboswag.base_filters.databinding.SelectionItemBinding
import ru.touchin.roboswag.base_filters.select_list_item.ListSelectionView
import ru.touchin.roboswag.base_filters.select_list_item.model.BaseSelectionItem

class SelectionItemViewHolder<ItemType: BaseSelectionItem>(private val binding: SelectionItemBinding,
                                                           private val onItemSelectAction: (ItemType) -> Unit,
                                                           private val selectionType: ListSelectionView.SelectionType
                              ) : BaseSelectionViewHolder<ItemType>(binding.root) {

    override fun bind(item: ItemType) {
        binding.run {
            val checkListener = View.OnClickListener {
                itemRadiobutton.isChecked = true
                onItemSelectAction.invoke(item.copyWithSelection(isSelected = when (selectionType) {
                    ListSelectionView.SelectionType.SINGLE_SELECT -> true
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
