package ru.touchin.roboswag.recyclerview_adapters

import androidx.recyclerview.widget.RecyclerView

/**
 * Objects of such class controls creation and binding of specific type of RecyclerView's ViewHolders.
 * Such delegates are creating and binding ViewHolders for specific items.
 * Default [.getItemViewType] is generating on construction of object.
 *
 * @param <TViewHolder> Type of [RecyclerView.ViewHolder] of delegate;
 * @param <TItem>       Type of items to bind to [RecyclerView.ViewHolder]s.
</TItem></TViewHolder> */
abstract class ItemAdapterDelegate<TViewHolder : RecyclerView.ViewHolder, TItem> : AdapterDelegate<TViewHolder>() {

    override fun isForViewType(items: List<*>, adapterPosition: Int, collectionPosition: Int): Boolean {
        return (collectionPosition >= 0
                && collectionPosition < items.size
                && isForViewType(items[collectionPosition]!!, adapterPosition, collectionPosition))
    }

    /**
     * Returns if object is processable by this delegate.
     * This item will be casted to [TItem] and passes to [.onBindViewHolder].
     *
     * @param item                  Item to check;
     * @param adapterPosition       Position of item in adapter;
     * @param collectionPosition    Position of item in collection that contains item;
     * @return True if item is processable by this delegate.
     */
    open fun isForViewType(item: Any, adapterPosition: Int, collectionPosition: Int): Boolean {
        return true
    }

    override fun getItemId(items: List<*>, adapterPosition: Int, collectionPosition: Int): Long =
            getItemId(items[collectionPosition] as TItem, adapterPosition, collectionPosition)

    /**
     * Returns unique ID of item to support stable ID's logic of RecyclerView's adapter.
     *
     * @param item                 Item in adapter;
     * @param adapterPosition      Position of item in adapter;
     * @param collectionPosition   Position of item in collection that contains item;
     * @return Unique item ID.
     */
    fun getItemId(item: TItem, adapterPosition: Int, collectionPosition: Int): Long = 0

    override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            items: List<*>,
            adapterPosition: Int,
            collectionPosition: Int,
            payloads: List<*>
    ) {
        onBindViewHolder(holder as TViewHolder, items[collectionPosition] as TItem, adapterPosition, collectionPosition, payloads)
    }

    /**
     * Binds item with payloads to created by this object ViewHolder.
     *
     * @param holder               ViewHolder to bind item to;
     * @param item                 Item in adapter;
     * @param adapterPosition      Position of item in adapter;
     * @param collectionPosition   Position of item in collection that contains item;
     * @param payloads             Payloads;
     */
    abstract fun onBindViewHolder(
            holder: TViewHolder,
            item: TItem,
            adapterPosition: Int,
            collectionPosition: Int,
            payloads: List<*>
    )

}
