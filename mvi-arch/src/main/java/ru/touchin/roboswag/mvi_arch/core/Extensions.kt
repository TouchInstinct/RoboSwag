package ru.touchin.roboswag.mvi_arch.core

import android.os.Parcelable
import ru.touchin.roboswag.mvi_arch.marker.ViewAction
import ru.touchin.roboswag.mvi_arch.marker.ViewState

/**
 * Used for setting arguments and initial state into Fragments
 */
fun <NavArgs : Parcelable,
        State : ViewState,
        Action : ViewAction,
        VM : MviViewModel<NavArgs, Action, State>,
        TFragment : MviFragment<NavArgs, State, Action, VM>>
        TFragment.withArgs(navArgs: NavArgs) = apply { initArgs(navArgs) }
