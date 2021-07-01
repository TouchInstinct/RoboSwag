package ru.touchin.roboswag.mvi_arch.core

import android.os.Parcelable

/**
 * Used for setting arguments and initial state into Fragments
 */
fun <NavArgs : Parcelable, State, Action, VM, TFragment : MviFragment<NavArgs, State, Action, VM>>
        TFragment.withArgs(navArgs: NavArgs) = apply { initArgs(navArgs) }
