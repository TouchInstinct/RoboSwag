package ru.touchin.extensions

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.Adapter<out RecyclerView.ViewHolder>.onDataUpdatedAndDrawn(onChanged: () -> Unit) = registerAdapterDataObserver(
        object : SimpleDataObserver() {
            override fun onChange() = onChanged()
        }
)
