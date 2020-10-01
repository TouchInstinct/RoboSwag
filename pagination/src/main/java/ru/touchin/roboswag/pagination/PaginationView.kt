package ru.touchin.roboswag.pagination

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.touchin.extensions.setOnRippleClickListener
import ru.touchin.mvi_arch.core_pagination.databinding.ViewPaginationBinding

/**
 * View, responsible for displaying paginator
 */

// TODO: add an errorview with empty state and error text
// TODO: add LoadingContentView
class PaginationView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private lateinit var refreshCallback: (() -> Unit)
    private lateinit var adapter: PaginationAdapter

    private val binding = ViewPaginationBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        with(binding) {
            swipeToRefresh.setOnRefreshListener { refreshCallback() }
            // TODO: удалить и перенести настройку layoutManager в init
            elementsRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            emptyText.setOnRippleClickListener { refreshCallback() }
        }
    }

    // Метод, который настраивает view: выставляет adapter для recyclerView и передает лямбду, которую надо вызвать при pull-to-refresh
    fun init(refreshCallback: () -> Unit, adapter: PaginationAdapter) {
        this.refreshCallback = refreshCallback
        this.adapter = adapter
        binding.elementsRecycler.adapter = adapter
    }

    fun render(state: Paginator.State) {
        with(binding) {
            swipeToRefresh.isRefreshing = state is Paginator.State.Refresh<*>
            swipeToRefresh.isEnabled = state !is Paginator.State.EmptyProgress
            switcher.showChild(
                    when (state) {
                        Paginator.State.Empty, is Paginator.State.EmptyError -> emptyText.id
                        Paginator.State.EmptyProgress -> progressBar.id
                        else -> elementsRecycler.id
                    }
            )
            adapter.fullData = state === Paginator.State.Empty || state is Paginator.State.FullData<*>

            when (state) {
                is Paginator.State.EmptyError, Paginator.State.Empty, Paginator.State.EmptyProgress  -> {
                    adapter.update(emptyList(), PaginationAdapter.UpdateState.Common)
                }
                is Paginator.State.Data<*> -> {
                    adapter.update(state.data as List<Any>, PaginationAdapter.UpdateState.Common)
                }
                is Paginator.State.Refresh<*> -> {
                    adapter.update(state.data as List<Any>, PaginationAdapter.UpdateState.Common)
                }
                is Paginator.State.NewPageProgress<*> -> {
                    adapter.update(state.data as List<Any>, PaginationAdapter.UpdateState.Progress)
                }
                is Paginator.State.FullData<*> -> {
                    adapter.update(state.data as List<Any>, PaginationAdapter.UpdateState.Common)
                }
            }
        }
    }

}
