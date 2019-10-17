package ru.touchin.roboswag.recyclerview_adapters

import androidx.recyclerview.widget.RecyclerView
import android.util.SparseArray
import android.view.ViewGroup

/**
 * Manager for delegation callbacks from [RecyclerView.Adapter] to delegates.
 */
class DelegatesManager {

    private val delegates = SparseArray<AdapterDelegate<*>>()

    fun getItemViewType(items: List<*>, adapterPosition: Int, collectionPosition: Int): Int {
        for (index in 0 until delegates.size()) {
            val delegate = delegates.valueAt(index)
            if (delegate.isForViewType(items, adapterPosition, collectionPosition)) {
                return delegate.itemViewType
            }
        }
        throw IllegalStateException("Delegate not found for adapterPosition: $adapterPosition")
    }

    fun getItemId(items: List<*>, adapterPosition: Int, collectionPosition: Int): Long {
        val delegate = getDelegate(getItemViewType(items, adapterPosition, collectionPosition))
        return delegate.getItemId(items, adapterPosition, collectionPosition)
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = getDelegate(viewType).onCreateViewHolder(parent)

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, items: List<*>, adapterPosition: Int, collectionPosition: Int, payloads: List<Any>) {
        val delegate = getDelegate(getItemViewType(items, adapterPosition, collectionPosition))
        delegate.onBindViewHolder(holder, items, adapterPosition, collectionPosition, payloads)
    }

    /**
     * Adds [PositionAdapterDelegate] to adapter.
     *
     * @param delegate Delegate to add.
     */
    fun addDelegate(delegate: AdapterDelegate<*>) = delegates.put(delegate.itemViewType, delegate)

    /**
     * Removes [AdapterDelegate] from adapter.
     *
     * @param delegate Delegate to remove.
     */
    fun removeDelegate(delegate: AdapterDelegate<*>) = delegates.remove(delegate.itemViewType)

    private fun getDelegate(viewType: Int) = delegates[viewType] ?: throw IllegalStateException("No AdapterDelegate added for view type: $viewType")

}
