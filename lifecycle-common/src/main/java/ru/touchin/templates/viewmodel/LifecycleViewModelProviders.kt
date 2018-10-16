package ru.touchin.templates.viewmodel

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewController

object LifecycleViewModelProviders {

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
    fun of(lifecycleOwner: LifecycleOwner, factory: ViewModelProvider.Factory = getViewModelFactory(lifecycleOwner)): ViewModelProvider =
            when (lifecycleOwner) {
                is ViewController<*, *> -> ViewModelProviders.of(lifecycleOwner.fragment, factory)
                is Fragment -> ViewModelProviders.of(lifecycleOwner, factory)
                is FragmentActivity -> ViewModelProviders.of(lifecycleOwner, factory)
                else -> throw IllegalArgumentException("Not supported LifecycleOwner.")
            }

    /**
     * Returns ViewModelProvider.Factory instance from current lifecycleOwner.
     * Search #ViewModelFactoryProvider are produced according to priorities:
     * 1. View controller;
     * 2. Fragment;
     * 3. Parent fragment recursively;
     * 4. Activity;
     * 5. Application.
     */
    fun getViewModelFactory(provider: Any): ViewModelProvider.Factory =
            when (provider) {
                is ViewModelFactoryProvider -> provider.viewModelFactory
                is ViewController<*, *> -> getViewModelFactory(provider.fragment)
                is Fragment -> getViewModelFactory(provider.parentFragment ?: provider.requireActivity())
                is Activity -> getViewModelFactory(provider.application)
                else -> throw IllegalArgumentException("View model factory not found.")
            }

}
