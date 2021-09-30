package ru.touchin.roboswag.mvi_arch.core

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.touchin.mvi_arch.R
import ru.touchin.roboswag.components.utils.px
import ru.touchin.roboswag.mvi_arch.di.ViewModelAssistedFactory
import ru.touchin.roboswag.mvi_arch.di.ViewModelFactory
import ru.touchin.roboswag.mvi_arch.marker.ViewAction
import ru.touchin.roboswag.mvi_arch.marker.ViewState
import javax.inject.Inject

abstract class FullscreenBottomSheetDialog<NavArgs, State, Action, VM>(
        @LayoutRes private val layoutId: Int,
        private val topPadding: Int = 0
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

    private val onShowListener by lazy {
        DialogInterface.OnShowListener { dialog ->
            (dialog as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                    ?.let { BottomSheetBehavior.from(it) }
                    ?.apply {
                        state = BottomSheetBehavior.STATE_EXPANDED
                        skipCollapsed = true
                    }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.RoundedCornersBottomSheetDialogTheme)
        (savedInstanceState ?: arguments)?.getParcelable<NavArgs>(MviFragment.INIT_ARGS_KEY)?.let { savedState ->
            state = savedState
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer(this::renderState))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

    override fun onStart() {
        super.onStart()

        val bottomSheet = dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheet?.setPadding(0, topPadding.px, 0, 0)
    }

    override fun addOnBackPressedCallback(action: Action) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                dispatchAction(action)
            }
        })
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        dialog.setOnShowListener(onShowListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(MviFragment.INIT_ARGS_KEY, state)
    }

}
