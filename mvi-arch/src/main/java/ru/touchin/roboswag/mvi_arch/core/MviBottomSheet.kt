package ru.touchin.roboswag.mvi_arch.core

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.touchin.mvi_arch.R
import ru.touchin.roboswag.mvi_arch.di.ViewModelAssistedFactory
import ru.touchin.roboswag.mvi_arch.di.ViewModelFactory
import ru.touchin.roboswag.mvi_arch.marker.ViewAction
import ru.touchin.roboswag.mvi_arch.marker.ViewState
import javax.inject.Inject

abstract class MviBottomSheet<NavArgs, State, Action, VM>(
        @LayoutRes private val layoutId: Int
) : BottomSheetDialogFragment(), IMvi<NavArgs, State, Action, VM>
        where NavArgs : Parcelable,
              Action : ViewAction,
              State : ViewState,
              VM : MviViewModel<NavArgs, Action, State> {

    @Inject
    lateinit var viewModelMap: MutableMap<Class<out ViewModel>, ViewModelAssistedFactory<out ViewModel>>

    protected lateinit var state: NavArgs

    protected abstract fun injectDependencies()

    @MainThread
    protected inline fun <reified ViewModel : VM> viewModel(): Lazy<ViewModel> =
            lazy {
                val fragmentArguments = arguments ?: bundleOf()

                ViewModelProvider(
                        viewModelStore,
                        ViewModelFactory(viewModelMap, this, fragmentArguments)
                ).get(ViewModel::class.java)
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.RoundedCornersBottomSheetDialogTheme)
        (savedInstanceState ?: arguments)?.getParcelable<NavArgs>(MviFragment.INIT_ARGS_KEY)?.let { savedState ->
            state = savedState
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRenderState()
    }

    private fun setupRenderState() {
        viewModel.state.observe(viewLifecycleOwner, Observer(this::renderState))
    }

    override fun addOnBackPressedCallback(action: Action) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                dispatchAction(action)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(MviFragment.INIT_ARGS_KEY, state)
    }

}
