package ru.touchin.lifecycle_viewcontroller.viewmodel

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.touchin.lifecycle.viewmodel.BaseLifecycleViewModelProviders
import ru.touchin.lifecycle.viewmodel.ViewModelFactoryProvider
import ru.touchin.roboswag.navigation_viewcontroller.viewcontrollers.ViewController

object LifecycleViewModelProviders : BaseLifecycleViewModelProviders() {

    /**
     * Returns ViewModelProvider.Factory instance from current lifecycleOwner.
     * Search #ViewModelFactoryProvider are produced according to priorities:
     * 1. View controller;
     * 2. Fragment;
     * 3. Parent fragment recursively;
     * 4. Activity;
     * 5. Application.
     */
    override fun getViewModelFactory(provider: Any): ViewModelProvider.Factory =
            when (provider) {
                is ViewModelFactoryProvider -> provider.viewModelFactory
                is ViewController<*, *> -> getViewModelFactory(provider.fragment)
                is Fragment -> getViewModelFactory(provider.parentFragment ?: provider.requireActivity())
                is Activity -> getViewModelFactory(provider.application)
                else -> throw IllegalArgumentException("View model factory not found.")
            }

}
