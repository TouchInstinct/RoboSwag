package ru.touchin.roboswag.components.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Objects of such class controls creation and binding of specific type of RecyclerView's ViewHolders.
 * Such delegates are creating and binding ViewHolders by position in adapter.
 * Default {@link #getItemViewType} is generating on construction of object.
 *
 * @param <TViewHolder> Type of {@link RecyclerView.ViewHolder} of delegate.
 */
public abstract class PositionAdapterDelegate<TViewHolder extends RecyclerView.ViewHolder> extends AdapterDelegate<TViewHolder> {

    @Override
    public boolean isForViewType(@NonNull final List<Object> items, final int adapterPosition, final int collectionPosition) {
        return isForViewType(adapterPosition);
    }

    /**
     * Returns if object is processable by this delegate.
     *
     * @param adapterPosition Position of item in adapter;
     * @return True if item is processable by this delegate.
     */
    public abstract boolean isForViewType(final int adapterPosition);

    @Override
    public long getItemId(@NonNull final List<Object> objects, final int adapterPosition, final int itemsOffset) {
        return getItemId(adapterPosition);
    }

    /**
     * Returns unique ID of item to support stable ID's logic of RecyclerView's adapter.
     *
     * @param adapterPosition Position of item in adapter;
     * @return Unique item ID.
     */
    public long getItemId(final int adapterPosition) {
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
        onBindViewHolder((TViewHolder) holder, adapterPosition, payloads);
    }

    /**
     * Binds position with payloads to ViewHolder.
     *
     * @param holder            ViewHolder to bind position to;
     * @param adapterPosition   Position of item in adapter;
     * @param payloads          Payloads.
     */
    public void onBindViewHolder(@NonNull final TViewHolder holder, final int adapterPosition, @NonNull final List<Object> payloads) {
        //do nothing by default
    }

}
