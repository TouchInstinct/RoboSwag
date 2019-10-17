package ru.touchin.roboswag.recyclerview_adapters

import androidx.recyclerview.widget.RecyclerView

/**
 * Objects of such class controls creation and binding of specific type of RecyclerView's ViewHolders.
 * Such delegates are creating and binding ViewHolders by position in adapter.
 * Default [.getItemViewType] is generating on construction of object.
 *
 * @param <TViewHolder> Type of [RecyclerView.ViewHolder] of delegate.
</TViewHolder> */
abstract class PositionAdapterDelegate<TViewHolder : RecyclerView.ViewHolder> : AdapterDelegate<TViewHolder>() {

    override fun isForViewType(items: List<*>, adapterPosition: Int, collectionPosition: Int): Boolean {
        return isForViewType(adapterPosition)
    }

    /**
     * Returns if object is processable by this delegate.
     *
     * @param adapterPosition Position of item in adapter;
     * @return True if item is processable by this delegate.
     */
    abstract fun isForViewType(adapterPosition: Int): Boolean

    override fun getItemId(items: List<*>, adapterPosition: Int, collectionPosition: Int): Long {
        return getItemId(adapterPosition)
    }

    /**
     * Returns unique ID of item to support stable ID's logic of RecyclerView's adapter.
     *
     * @param adapterPosition Position of item in adapter;
     * @return Unique item ID.
     */
    fun getItemId(adapterPosition: Int): Long {
        return 0
    }

    override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            items: List<*>,
            adapterPosition: Int,
            collectionPosition: Int,
            payloads: List<*>
    ) {
        onBindViewHolder(holder as TViewHolder, adapterPosition, payloads)
    }

    /**
     * Binds position with payloads to ViewHolder.
     *
     * @param holder            ViewHolder to bind position to;
     * @param adapterPosition   Position of item in adapter;
     * @param payloads          Payloads.
     */
    open fun onBindViewHolder(holder: TViewHolder, adapterPosition: Int, payloads: List<*>) {
        //do nothing by default
    }

}
