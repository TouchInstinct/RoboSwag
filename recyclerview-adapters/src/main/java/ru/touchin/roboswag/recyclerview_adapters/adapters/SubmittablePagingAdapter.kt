package ru.touchin.roboswag.recyclerview_adapters.adapters

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class SubmittablePagingAdapter<T : Any, VH : RecyclerView.ViewHolder>(private val diffCallback: DiffUtil.ItemCallback<T>):
        PagingDataAdapter<T, VH>(diffCallback) {
    /**
     * [items] list of updated elements
     * [transform] callback that keeps logic of transformation item based on newItem.
     * Should return updated element
     */
    fun submitList(items: List<T>, transform: T.(newItem: T, payload: Any?) -> T) {
        items.forEach { newItem ->
            snapshot().forEachIndexed { index, oldItem ->
                if (oldItem != null && diffCallback.areItemsTheSame(oldItem, newItem) && !diffCallback.areContentsTheSame(oldItem, newItem)) {
                    val payload = diffCallback.getChangePayload(oldItem, newItem)

                    oldItem.transform(newItem, payload)

                    notifyItemChanged(index, payload)
                }
            }
        }
    }
}
