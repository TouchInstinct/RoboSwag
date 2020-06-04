package ru.touchin.lifecycle_viewcontroller.viewmodel

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ru.touchin.lifecycle.viewmodel.BaseLifecycleViewModelProviders
import ru.touchin.lifecycle.viewmodel.ViewModelFactoryProvider
import ru.touchin.roboswag.navigation_viewcontroller.viewcontrollers.ViewController

object LifecycleViewModelProviders : BaseLifecycleViewModelProviders() {

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
    override fun of(
            lifecycleOwner: LifecycleOwner,
            factory: ViewModelProvider.Factory
    ): ViewModelProvider =
            when (lifecycleOwner) {
                is ViewController<*, *> -> ViewModelProviders.of(lifecycleOwner.fragment, factory)
                else -> super.of(lifecycleOwner, factory)
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
    override fun getViewModelFactory(provider: Any): ViewModelProvider.Factory =
            when (provider) {
                is ViewController<*, *> -> getViewModelFactory(provider.fragment)
                else -> super.getViewModelFactory(provider)
            }

}
