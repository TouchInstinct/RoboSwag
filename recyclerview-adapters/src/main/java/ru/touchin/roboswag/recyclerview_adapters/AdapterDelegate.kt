/*
 *  Copyright (c) 2019 RoboSwag (Gavriil Sitnikov, Vsevolod Ivanov)
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

package ru.touchin.roboswag.recyclerview_adapters

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup

/**
 * Objects of such class controls creation and binding of specific type of RecyclerView's ViewHolders.
 * Default [.getItemViewType] is generating on construction of object.
 *
 * @param <TViewHolder> Type of [RecyclerView.ViewHolder] of delegate.
</TViewHolder> */
abstract class AdapterDelegate<TViewHolder : RecyclerView.ViewHolder> {

    /**
     * Unique ID of AdapterDelegate.
     *
     * @return Unique ID.
     */
    val itemViewType = ViewCompat.generateViewId()

    /**
     * Returns if object is processable by this delegate.
     *
     * @param items                  Items to check;
     * @param adapterPosition        Position of item in adapter;
     * @param collectionPosition     Position of item in collection;
     * @return True if item is processable by this delegate.
     */
    abstract fun isForViewType(items: List<*>, adapterPosition: Int, collectionPosition: Int): Boolean

    /**
     * Returns unique ID of item to support stable ID's logic of RecyclerView's adapter.
     *
     * @param items                Items in adapter;
     * @param adapterPosition      Position of item in adapter;
     * @param collectionPosition   Position of item in collection;
     * @return Unique item ID.
     */
    open fun getItemId(items: List<*>, adapterPosition: Int, collectionPosition: Int): Long = 0

    /**
     * Creates ViewHolder to bind item to it later.
     *
     * @param parent Container of ViewHolder's view.
     * @return New ViewHolder.
     */
    abstract fun onCreateViewHolder(parent: ViewGroup): TViewHolder

    /**
     * Binds item to created by this object ViewHolder.
     *
     * @param holder               ViewHolder to bind item to;
     * @param items                Items in adapter;
     * @param adapterPosition      Position of item in adapter;
     * @param collectionPosition   Position of item in collection that contains item;
     * @param payloads             Payloads;
     */
    abstract fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            items: List<*>,
            adapterPosition: Int,
            collectionPosition: Int,
            payloads: List<*>
    )

}
