package ru.touchin.roboswag.mvi_arch.di

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

/**
 * Factory that will be used by [ViewModelProvider] to instantiate viewModel in [MviFragment].
 *
 */
class ViewModelFactory(
        private val viewModelMap: MutableMap<Class<out ViewModel>, ViewModelAssistedFactory<out ViewModel>>,
        owner: SavedStateRegistryOwner,
        arguments: Bundle
) : AbstractSavedStateViewModelFactory(owner, arguments) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T =
            viewModelMap[modelClass]?.create(handle) as? T ?: throw IllegalStateException("Unknown ViewModel class")

}
