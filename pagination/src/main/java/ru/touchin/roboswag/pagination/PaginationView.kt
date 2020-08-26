package ru.touchin.roboswag.pagination

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.touchin.extensions.setOnRippleClickListener
import ru.touchin.mvi_arch.core_pagination.databinding.ViewPaginationBinding

// TODO: add an errorview with empty state and error text
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
            elementsRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            emptyText.setOnRippleClickListener { refreshCallback() }
        }
    }

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
                is Paginator.State.Empty -> {
                    adapter.update(emptyList(), false)
                }
                is Paginator.State.EmptyProgress -> {
                    adapter.update(emptyList(), false)
                }
                is Paginator.State.EmptyError -> {
                    adapter.update(emptyList(), false)
                }
                is Paginator.State.Data<*> -> {
                    adapter.update(state.data as List<Any>, false)
                }
                is Paginator.State.Refresh<*> -> {
                    adapter.update(state.data as List<Any>, false)
                }
                is Paginator.State.NewPageProgress<*> -> {
                    adapter.update(state.data as List<Any>, true)
                }
                is Paginator.State.FullData<*> -> {
                    adapter.update(state.data as List<Any>, false)
                }
            }
        }
    }

}
