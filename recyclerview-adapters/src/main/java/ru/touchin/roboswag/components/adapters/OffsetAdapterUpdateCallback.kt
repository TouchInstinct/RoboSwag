package ru.touchin.roboswag.components.adapters

import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView

class OffsetAdapterUpdateCallback(private val adapter: RecyclerView.Adapter<*>, private val offsetProvider: () -> Int) : ListUpdateCallback  {

    override fun onInserted(position: Int, count: Int) {
        adapter.notifyItemRangeInserted(position + offsetProvider(), count)
    }

    override fun onRemoved(position: Int, count: Int) {
        adapter.notifyItemRangeRemoved(position + offsetProvider(), count)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        adapter.notifyItemMoved(fromPosition + offsetProvider(), toPosition + offsetProvider())
    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        adapter.notifyItemRangeChanged(position + offsetProvider(), count, payload)
    }

}
