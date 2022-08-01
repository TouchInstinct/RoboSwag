package ru.touchin.roboswag.base_filters.select_list_item.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.touchin.roboswag.base_filters.select_list_item.model.BaseSelectionItem

abstract class BaseSelectionViewHolder<ItemType: BaseSelectionItem>(val view: View)
    : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: ItemType)
}
