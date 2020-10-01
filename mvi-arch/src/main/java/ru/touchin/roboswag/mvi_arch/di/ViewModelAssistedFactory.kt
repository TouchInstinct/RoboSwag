package ru.touchin.roboswag.mvi_arch.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * For transmission SavedStateHandle of viewModel's fragment.
 *
 * ViewModel should have inner interface, that implements ViewModelAssistedFactory
 * You should use @AssistedInject in viewModel's constructor and @Assisted in argument to assist.
 */

interface ViewModelAssistedFactory<VM : ViewModel> {
    fun create(handle: SavedStateHandle): VM
}
