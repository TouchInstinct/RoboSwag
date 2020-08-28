package ru.touchin.roboswag.pagination

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.touchin.roboswag.recyclerview_adapters.adapters.ItemAdapterDelegate
import ru.touchin.mvi_arch.core_pagination.R
import ru.touchin.roboswag.utils.UiUtils

class ProgressAdapterDelegate : ItemAdapterDelegate<RecyclerView.ViewHolder, ProgressItem>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            object : RecyclerView.ViewHolder(UiUtils.inflate(R.layout.item_progress, parent)) {}

    override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            item: ProgressItem,
            adapterPosition: Int,
            collectionPosition: Int,
            payloads: MutableList<Any>
    ) = Unit
}
