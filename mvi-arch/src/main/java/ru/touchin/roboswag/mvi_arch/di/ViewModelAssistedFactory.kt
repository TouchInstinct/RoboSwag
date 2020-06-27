package ru.touchin.roboswag.mvi_arch.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface ViewModelAssistedFactory<VM : ViewModel> {
    fun create(handle: SavedStateHandle): VM
}
