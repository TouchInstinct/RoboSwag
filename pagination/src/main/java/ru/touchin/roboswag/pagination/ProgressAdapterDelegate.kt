package ru.touchin.roboswag.pagination

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.touchin.adapters.ItemAdapterDelegate
import ru.touchin.mvi_arch.core_pagination.R
import ru.touchin.mvi_test.core_ui.pagination.ProgressItem
import ru.touchin.roboswag.components.utils.UiUtils

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
