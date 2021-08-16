package ru.touchin.roboswag.mvi_arch.core

import android.os.Parcelable
import android.view.View
import ru.touchin.extensions.setOnRippleClickListener
import ru.touchin.roboswag.mvi_arch.marker.ViewAction
import ru.touchin.roboswag.mvi_arch.marker.ViewState

/**
 * Interface with the main MVI methods and fields
 */
interface IMvi<NavArgs, Action, State, VM>
        where NavArgs : Parcelable,
              State : ViewState,
              Action : ViewAction,
              VM : MviViewModel<NavArgs, Action, State> {

    /**
     * Use [viewModel] extension to get an instance of your view model class.
     */
    val viewModel: VM

    /**
     * Use this method to subscribe on view state changes.
     *
     * You should render view state here.
     *
     * Must not be called before [onAttach] and after [onDetach].
     */
    fun renderState(viewState: State) {}

    /**
     * Use this method to dispatch view actions to view model.
     */
    fun dispatchAction(actionProvider: () -> Action) {
        viewModel.dispatchAction(actionProvider.invoke())
    }

    /**
     * Use this method to dispatch view actions to view model.
     */
    fun dispatchAction(action: Action) {
        viewModel.dispatchAction(action)
    }

    fun addOnBackPressedCallback(actionProvider: () -> Action) {
        addOnBackPressedCallback(actionProvider.invoke())
    }

    fun addOnBackPressedCallback(action: Action)

    /**
     * Simple extension for dispatching view events to view model with on click.
     */
    fun View.dispatchActionOnClick(actionProvider: () -> Action) {
        setOnClickListener { dispatchAction(actionProvider) }
    }

    /**
     * Simple extension for dispatching view events to view model with on click.
     */
    fun View.dispatchActionOnClick(action: Action) {
        setOnClickListener { dispatchAction(action) }
    }

    /**
     * Simple extension for dispatching view events to view model with on ripple click.
     */
    fun View.dispatchActionOnRippleClick(actionProvider: () -> Action) {
        setOnRippleClickListener { dispatchAction(actionProvider) }
    }

    /**
     * Simple extension for dispatching view events to view model with on ripple click.
     */
    fun View.dispatchActionOnRippleClick(action: Action) {
        setOnRippleClickListener { dispatchAction(action) }
    }

}
