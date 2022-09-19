package ru.touchin.roboswag.base_filters.select_list_item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import ru.touchin.roboswag.base_filters.databinding.SelectionItemBinding
import ru.touchin.roboswag.base_filters.databinding.SingleSelectionLayoutBinding
import ru.touchin.roboswag.base_filters.select_list_item.adapter.BaseSelectionViewHolder
import ru.touchin.roboswag.base_filters.select_list_item.adapter.HolderFactoryType
import ru.touchin.roboswag.base_filters.select_list_item.adapter.SelectionItemViewHolder
import ru.touchin.roboswag.base_filters.select_list_item.adapter.SheetSelectionAdapter
import ru.touchin.roboswag.base_filters.select_list_item.model.BaseSelectionItem

private typealias OnSelectedItemListener<ItemType> = (item: ItemType) -> Unit
private typealias OnSelectedItemsListener<ItemType> = (items: List<ItemType>) -> Unit

/**
 *  Base [ListSelectionView] to use in filters screen for choosing single or multi items in list.
 *
 *  @param ItemType Type of model's element in list.
 *  It must implement [BaseSelectionItem] abstract class.
 *
 *  @param HolderType Type of viewHolder in recyclerView.
 *  It must extend [BaseSelectionViewHolder] abstract class.
 *
 **/

class ListSelectionView<ItemType, HolderType> @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes)
        where ItemType : BaseSelectionItem,
              HolderType : BaseSelectionViewHolder<ItemType> {

    private var mutableItems: List<ItemType> = emptyList()
    private var selectionType = SelectionType.SINGLE_SELECT

    private var onSelectedItemChanged: OnSelectedItemListener<ItemType>? = null
    private var onSelectedItemsChanged: OnSelectedItemsListener<ItemType>? = null
    private var factory: HolderFactoryType<ItemType> = getDefaultFactory()

    private fun getDefaultFactory(): HolderFactoryType<ItemType> = { parent, clickListener, selectionType ->
        SelectionItemViewHolder(
                binding = SelectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onItemSelectAction = clickListener,
                selectionType = selectionType
        )
    }

    private val binding = SingleSelectionLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    private val adapter by lazy {
        SheetSelectionAdapter(
                onItemSelectAction = onItemSelectedListener,
                selectionType = selectionType,
                factory = factory
        )
    }

    private val onItemSelectedListener: (item: ItemType) -> Unit = { item ->
        onSelectedItemChanged?.invoke(item)
        updateAfterSelection(item)
        onSelectedItemsChanged?.invoke(mutableItems)
    }

    private fun updateList() {
        adapter.submitList(mutableItems)
    }

    private fun updateAfterSelection(selectedItem: ItemType) {
        mutableItems = mutableItems.map { item ->
            when {
                item.isItemTheSame(selectedItem) -> selectedItem
                selectionType == SelectionType.SINGLE_SELECT -> item.copyWithSelection(isSelected = false)
                else -> item
            }
        }
        updateList()
    }

    inner class Builder {

        fun setItems(items: List<ItemType>) = apply {
            mutableItems = items
        }

        fun <T> setItems(
                source: List<T>,
                mapper: (T) -> ItemType
        ) = setItems(source.map { item -> mapper.invoke(item) })

        fun showInHolder(holderFactory: HolderFactoryType<ItemType>) = apply {
            factory = holderFactory
        }

        fun addItemDecoration(itemDecoration: RecyclerView.ItemDecoration) = apply {
            binding.itemsRecycler.addItemDecoration(itemDecoration)
        }

        fun onSelectedItemListener(listener: OnSelectedItemListener<ItemType>) = apply {
            this@ListSelectionView.onSelectedItemChanged = listener
        }

        fun onSelectedItemsListener(listener: OnSelectedItemsListener<ItemType>) = apply {
            this@ListSelectionView.onSelectedItemsChanged = listener
        }

        fun withSelectionType(type: SelectionType) = apply {
            selectionType = type
        }

        fun build() = this@ListSelectionView.also {
            binding.itemsRecycler.adapter = adapter
            updateList()
        }
    }

    enum class SelectionType { SINGLE_SELECT, MULTI_SELECT }
}
