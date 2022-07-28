package ru.touchin.roboswag.base_filters.select_list_item.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.touchin.roboswag.base_filters.databinding.SelectionItemBinding
import ru.touchin.roboswag.base_filters.select_list_item.OnItemSelectedListener
import ru.touchin.roboswag.base_filters.select_list_item.model.RowSelectionItem
import ru.touchin.roboswag.recyclerview_adapters.adapters.ItemAdapterDelegate

class SheetSelectionDelegate(
        private val onItemSelectAction: OnItemSelectedListener
) : ItemAdapterDelegate<SheetSelectionDelegate.SelectionItemViewHolder, RowSelectionItem>() {

    override fun onCreateViewHolder(parent: ViewGroup): SelectionItemViewHolder = SelectionItemViewHolder(
            binding = SelectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(
            holder: SelectionItemViewHolder,
            item: RowSelectionItem,
            adapterPosition: Int,
            collectionPosition: Int,
            payloads: MutableList<Any>
    ) = holder.bind(item)

    inner class SelectionItemViewHolder(val binding: SelectionItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RowSelectionItem) {
            binding.run {
                val checkListener = View.OnClickListener {
                    itemRadiobutton.isChecked = true
                    onItemSelectAction.invoke(item.copy(isSelected = !item.isSelected))
                }

                itemTitle.text = item.title
                root.setOnClickListener(checkListener)

                itemRadiobutton.setOnClickListener(checkListener)
                itemRadiobutton.isChecked = item.isSelected
            }
        }

    }

}
