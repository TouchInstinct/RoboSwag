package ru.touchin.roboswag.mvi_arch.core

import android.os.Parcelable
import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.touchin.roboswag.mvi_arch.marker.ViewAction
import ru.touchin.roboswag.mvi_arch.marker.ViewState
import ru.touchin.roboswag.mvi_arch.mediator.MediatorStore

/**
 *  Base [ViewModel] to use in MVI architecture.
 *
 *  @param NavArgs Type of arguments class of this screen.
 *  It must implement [NavArgs] interface provided by navigation library that is a part of Google Jetpack.
 *  An instance of this class is generated by [SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args)
 *  plugin according to related configuration file in navigation resource folder of your project.
 *
 *  @param State Type of view state class of this screen.
 *  It must implement [ViewState] interface. Usually it's a data class that presents full state of current screen's view.
 *  @see [ViewState] for more information.
 *
 *  @param Action Type of view actions class of this screen.
 *  It must implement [Action] interface. Usually it's a sealed class that contains classes and objects representing
 *  view actions of this view, e.g. button clicks, text changes, etc.
 *  @see [Action] for more information.
 *
 * @author Created by Max Bachinsky and Ivan Vlasov at Touch Instinct.
 */

abstract class MviViewModel<NavArgs : Parcelable, Action : ViewAction, State : ViewState>(
        private val initialState: State,
        protected val handle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val STATE_KEY = "STATE_KEY"
    }

    private val mediatorStore = MediatorStore(
        listOfNotNull(
//                    Min api 24
//                    https://github.com/TouchInstinct/RoboSwag/issues/180
//                    LoggingMediator(this::class.simpleName!!).takeIf { BuildConfig.DEBUG }
        )
    )

    protected val navArgs: NavArgs = handle.get(MviFragment.INIT_ARGS_KEY) ?: throw IllegalStateException("Nav args mustn't be null")
    protected val savedState: State = handle.get(STATE_KEY) ?: initialState

    @SuppressWarnings("detekt.VariableNaming")
    protected val _state = handle.getLiveData<State>(STATE_KEY, initialState)
    internal val state = Transformations.distinctUntilChanged(_state)

    protected val currentState: State
        get() = _state.value ?: initialState

    private val stateMediatorObserver = Observer<State>(mediatorStore::onNewState)

    init {
        viewModelScope.launch {
             if (!handle.contains(STATE_KEY)){
                 _state.value = initialState
             }
            state.observeForever(stateMediatorObserver)
        }
    }

    @CallSuper
    open fun dispatchAction(action: Action) {
        mediatorStore.onAction(action)
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        state.removeObserver(stateMediatorObserver)
    }

}
