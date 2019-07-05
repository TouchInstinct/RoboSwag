package ru.touchin.adapters

import androidx.recyclerview.widget.RecyclerView

abstract class SimpleDataObserver : RecyclerView.AdapterDataObserver() {

    abstract fun onChange()

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) = onChange()

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = onChange()

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = onChange()

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) = onChange()

}
