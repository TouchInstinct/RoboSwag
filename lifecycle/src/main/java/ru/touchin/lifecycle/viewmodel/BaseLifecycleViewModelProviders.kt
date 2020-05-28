package ru.touchin.lifecycle.viewmodel

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider

abstract class BaseLifecycleViewModelProviders {

    /**
     * Creates a {@link ViewModelProvider}, which retains ViewModels while a scope of given
     * {@code lifecycleOwner} is alive. More detailed explanation is in {@link ViewModel}.
     * <p>
     * It uses the given {@link Factory} to instantiate new ViewModels.
     *
     * @param lifecycleOwner a lifecycle owner, in whose scope ViewModels should be retained (ViewController, Fragment, Activity)
     * @param factory  a {@code Factory} to instantiate new ViewModels
     * @return a ViewModelProvider instance
     */
    open fun of(
            lifecycleOwner: LifecycleOwner,
            factory: ViewModelProvider.Factory = LifecycleViewModelProviders.getViewModelFactory(lifecycleOwner)
    ): ViewModelProvider =
            when (lifecycleOwner) {
                is Fragment -> ViewModelProvider(lifecycleOwner, factory)
                is FragmentActivity -> ViewModelProvider(lifecycleOwner, factory)
                else -> throw IllegalArgumentException("Not supported LifecycleOwner.")
            }

    /**
     * Returns ViewModelProvider.Factory instance from current lifecycleOwner.
     * Search #ViewModelFactoryProvider are produced according to priorities:
     * 1. Fragment;
     * 2. Parent fragment recursively;
     * 3. Activity;
     * 4. Application.
     */
    open fun getViewModelFactory(provider: Any): ViewModelProvider.Factory =
            when (provider) {
                is ViewModelFactoryProvider -> provider.viewModelFactory
                is Fragment -> LifecycleViewModelProviders.getViewModelFactory(provider.parentFragment ?: provider.requireActivity())
                is Activity -> LifecycleViewModelProviders.getViewModelFactory(provider.application)
                else -> throw IllegalArgumentException("View model factory not found.")
            }

}
