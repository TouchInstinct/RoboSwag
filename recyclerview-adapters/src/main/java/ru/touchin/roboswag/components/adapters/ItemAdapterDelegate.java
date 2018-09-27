package ru.touchin.roboswag.components.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Objects of such class controls creation and binding of specific type of RecyclerView's ViewHolders.
 * Such delegates are creating and binding ViewHolders for specific items.
 * Default {@link #getItemViewType} is generating on construction of object.
 *
 * @param <TViewHolder> Type of {@link RecyclerView.ViewHolder} of delegate;
 * @param <TItem>       Type of items to bind to {@link RecyclerView.ViewHolder}s.
 */
public abstract class ItemAdapterDelegate<TViewHolder extends RecyclerView.ViewHolder, TItem> extends AdapterDelegate<TViewHolder> {

    @Override
    public boolean isForViewType(@NonNull final List<Object> items, final int adapterPosition, final int collectionPosition) {
        return collectionPosition >= 0
                && collectionPosition < items.size()
                && isForViewType(items.get(collectionPosition), adapterPosition, collectionPosition);
    }

    /**
     * Returns if object is processable by this delegate.
     * This item will be casted to {@link TItem} and passes to {@link #onBindViewHolder(TViewHolder, TItem, int, int, List)}.
     *
     * @param item                  Item to check;
     * @param adapterPosition       Position of item in adapter;
     * @param collectionPosition    Position of item in collection that contains item;
     * @return True if item is processable by this delegate.
     */
    public boolean isForViewType(@NonNull final Object item, final int adapterPosition, final int collectionPosition) {
        return true;
    }

    @Override
    public long getItemId(@NonNull final List<Object> items, final int adapterPosition, final int collectionPosition) {
        //noinspection unchecked
        return getItemId((TItem) items.get(collectionPosition), adapterPosition, collectionPosition);
    }

    /**
     * Returns unique ID of item to support stable ID's logic of RecyclerView's adapter.
     *
     * @param item                 Item in adapter;
     * @param adapterPosition      Position of item in adapter;
     * @param collectionPosition   Position of item in collection that contains item;
     * @return Unique item ID.
     */
    public long getItemId(@NonNull final TItem item, final int adapterPosition, final int collectionPosition) {
        return 0;
    }

    @Override
    public void onBindViewHolder(
            @NonNull final RecyclerView.ViewHolder holder,
            @NonNull final List<Object> items,
            final int adapterPosition,
            final int collectionPosition,
            @NonNull final List<Object> payloads
    ) {
        //noinspection unchecked
        onBindViewHolder((TViewHolder) holder, (TItem) items.get(collectionPosition), adapterPosition, collectionPosition, payloads);
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
    public abstract void onBindViewHolder(
            @NonNull final TViewHolder holder,
            @NonNull final TItem item,
            final int adapterPosition,
            final int collectionPosition,
            @NonNull final List<Object> payloads
    );

}
