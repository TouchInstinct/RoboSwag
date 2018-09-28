package ru.touchin.roboswag.components.adapters

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import ru.touchin.roboswag.components.extensions.setOnRippleClickListener

/**
 * Base adapter with delegation and diff computing on background thread.
 */
open class DelegationListAdapter<TItem>(config: AsyncDifferConfig<TItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    constructor(diffCallback: DiffUtil.ItemCallback<TItem>) : this(AsyncDifferConfig.Builder<TItem>(diffCallback).build())

    var itemClickListener: ((TItem, RecyclerView.ViewHolder) -> Unit)? = null

    private val delegatesManager = DelegatesManager()
    private var differ = AsyncListDiffer(OffsetAdapterUpdateCallback(this, ::getHeadersCount), config)

    open fun getHeadersCount() = 0

    open fun getFootersCount() = 0

    override fun getItemCount() = getHeadersCount() + getList().size + getFootersCount()

    override fun getItemViewType(position: Int) = delegatesManager.getItemViewType(getList(), position, getCollectionPosition(position))

    override fun getItemId(position: Int) = delegatesManager.getItemId(getList(), position, getCollectionPosition(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = delegatesManager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        val collectionPosition = getCollectionPosition(position)
        if (collectionPosition in 0 until getList().size) {
            if (itemClickListener != null) {
                holder.itemView.setOnRippleClickListener {
                    itemClickListener?.invoke(getList()[getCollectionPosition(holder.adapterPosition)], holder)
                }
            } else {
                holder.itemView.setOnClickListener(null)
            }
        }
        delegatesManager.onBindViewHolder(holder, getList(), position, collectionPosition, payloads)
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = Unit

    /**
     * Adds [AdapterDelegate] to adapter.
     *
     * @param delegate Delegate to add.
     */
    fun addDelegate(delegate: AdapterDelegate<*>) = delegatesManager.addDelegate(delegate)

    /**
     * Removes [AdapterDelegate] from adapter.
     *
     * @param delegate Delegate to remove.
     */
    fun removeDelegate(delegate: AdapterDelegate<*>) = delegatesManager.removeDelegate(delegate)

    /**
     * Submits a new list to be diffed, and displayed.
     *
     * If a list is already being displayed, a diff will be computed on a background thread, which
     * will dispatch Adapter.notifyItem events on the main thread.
     *
     * @param list The new list to be displayed.
     */
    fun submitList(list: List<TItem>) = differ.submitList(list)

    /**
     * Get the current List - any diffing to present this list has already been computed and
     * dispatched via the ListUpdateCallback.
     * <p>
     * If a <code>null</code> List, or no List has been submitted, an empty list will be returned.
     * <p>
     * The returned list may not be mutated - mutations to content must be done through
     * {@link #submitList(List)}.
     *
     * @return current List.
     */
    fun getList(): List<TItem> = differ.currentList

    fun getCollectionPosition(adapterPosition: Int) = adapterPosition - getHeadersCount()

}
