package ru.touchin.roboswag.base_filters.select_list_item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import ru.touchin.roboswag.base_filters.databinding.SingleSelectionLayoutBinding
import ru.touchin.roboswag.base_filters.select_list_item.adapter.SheetSelectionAdapter
import ru.touchin.roboswag.base_filters.select_list_item.model.RowSelectionItem

typealias OnItemSelectedListener = (item: RowSelectionItem) -> Unit
typealias OnSelectionResultListener = (items: List<RowSelectionItem>) -> Unit

class ListSelectionView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var mutableItems: List<RowSelectionItem> = emptyList()
    private var selectionType = SelectionType.SINGLE_SELECT

    private var onItemsClickListener: OnSelectionResultListener? = null
    private var onItemClickListener: OnItemSelectedListener? = null

    private val binding = SingleSelectionLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    private val adapter by lazy {
        SheetSelectionAdapter(onItemSelectAction = onItemSelectedListener)
    }

    private val onItemSelectedListener: OnItemSelectedListener = { item ->
        onItemClickListener?.invoke(item)
        updateAfterSelection(item)
        onItemsClickListener?.invoke(mutableItems)
    }

    private fun updateList() {
        adapter.submitList(mutableItems)
    }

    private fun updateAfterSelection(selectedItem: RowSelectionItem) {
        mutableItems = mutableItems.map { item ->
            when {
                item.id == selectedItem.id -> selectedItem
                selectionType == SelectionType.SINGLE_SELECT -> item.copy(isSelected = false)
                else -> item
            }
        }
        updateList()
    }

    fun setItems(items: List<RowSelectionItem>) = apply {
        binding.itemsRecycler.adapter = adapter
        mutableItems = items
        updateList()
    }

    fun <T> setItems(
            source: List<T>,
            mapper: (T) -> RowSelectionItem
    ) = setItems(source.map { item -> mapper.invoke(item) })

    fun addItemDecoration(itemDecoration: RecyclerView.ItemDecoration) = apply {
        binding.itemsRecycler.addItemDecoration(itemDecoration)
    }

    fun onItemClickListener(listener: OnItemSelectedListener) = apply {
        this@ListSelectionView.onItemClickListener = listener
    }

    fun onResultListener(listener: OnSelectionResultListener) = apply {
        this@ListSelectionView.onItemsClickListener = listener
    }

    fun withSelectionType(type: SelectionType) = apply {
        selectionType = type
    }

    enum class SelectionType { SINGLE_SELECT, MULTI_SELECT }
}
