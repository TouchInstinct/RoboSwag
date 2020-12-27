/*
 *  Copyright (c) 2017 RoboSwag (Gavriil Sitnikov, Vsevolod Ivanov)
 *
 *  This file is part of RoboSwag library.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package ru.touchin.roboswag.recyclerview_adapters.adapters;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Objects of such class controls creation and binding of specific type of RecyclerView's ViewHolders.
 * Default {@link #getItemViewType} is generating on construction of object.
 *
 * @param <TViewHolder> Type of {@link RecyclerView.ViewHolder} of delegate.
 */
public abstract class AdapterDelegate<TViewHolder extends RecyclerView.ViewHolder> {

    private final int defaultItemViewType = ViewCompat.generateViewId();

    /**
     * Unique ID of AdapterDelegate.
     *
     * @return Unique ID.
     */
    public int getItemViewType() {
        return defaultItemViewType;
    }

    /**
     * Returns if object is processable by this delegate.
     *
     * @param items                  Items to check;
     * @param adapterPosition        Position of item in adapter;
     * @param collectionPosition     Position of item in collection;
     * @return True if item is processable by this delegate.
     */
    public abstract boolean isForViewType(@NonNull final List<Object> items, final int adapterPosition, final int collectionPosition);

    /**
     * Returns unique ID of item to support stable ID's logic of RecyclerView's adapter.
     *
     * @param items                Items in adapter;
     * @param adapterPosition      Position of item in adapter;
     * @param collectionPosition   Position of item in collection;
     * @return Unique item ID.
     */
    public long getItemId(@NonNull final List<Object> items, final int adapterPosition, final int collectionPosition) {
        return 0;
    }

    /**
     * Creates ViewHolder to bind item to it later.
     *
     * @param parent Container of ViewHolder's view.
     * @return New ViewHolder.
     */
    @NonNull
    public abstract TViewHolder onCreateViewHolder(@NonNull final ViewGroup parent);

    /**
     * Binds item to created by this object ViewHolder.
     *
     * @param holder               ViewHolder to bind item to;
     * @param items                Items in adapter;
     * @param adapterPosition      Position of item in adapter;
     * @param collectionPosition   Position of item in collection that contains item;
     * @param payloads             Payloads;
     */
    public abstract void onBindViewHolder(
            @NonNull final RecyclerView.ViewHolder holder,
            @NonNull final List<Object> items,
            final int adapterPosition,
            final int collectionPosition,
            @NonNull final List<Object> payloads
    );

}
