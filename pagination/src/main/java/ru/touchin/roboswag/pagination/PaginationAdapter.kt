package ru.touchin.roboswag.pagination

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.touchin.roboswag.recyclerview_adapters.adapters.AdapterDelegate
import ru.touchin.roboswag.recyclerview_adapters.adapters.DelegationListAdapter
import ru.touchin.mvi_test.core_ui.pagination.ProgressItem

class PaginationAdapter(
        private val nextPageCallback: () -> Unit,
        private val itemIdDiff: (old: Any, new: Any) -> Boolean,
        vararg delegate: AdapterDelegate<out RecyclerView.ViewHolder>
) : DelegationListAdapter<Any>(
        object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean = itemIdDiff(oldItem, newItem)

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
        }
) {

    internal var fullData = false

    init {
        addDelegate(ProgressAdapterDelegate())
        delegate.forEach(this::addDelegate)
    }

    fun update(data: List<Any>, isPageProgress: Boolean) {
        submitList(data + listOfNotNull(ProgressItem.takeIf { isPageProgress }))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (!fullData && position >= itemCount - 10) nextPageCallback.invoke()
    }

}
