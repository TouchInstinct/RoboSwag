package ru.touchin.roboswag.base_filters.select_list_item.adapter

import android.view.View
import ru.touchin.roboswag.base_filters.databinding.SelectionItemBinding
import ru.touchin.roboswag.base_filters.select_list_item.model.BaseSelectionItem
import ru.touchin.roboswag.base_filters.SelectionType

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
