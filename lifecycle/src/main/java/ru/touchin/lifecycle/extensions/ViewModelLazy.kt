package ru.touchin.lifecycle.extensions

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import ru.touchin.lifecycle.viewmodel.LifecycleViewModelProviders

@MainThread
inline fun <reified VM : ViewModel> Fragment.viewModels(
        noinline ownerProducer: () -> ViewModelStoreOwner = { this },
        noinline factoryProducer: () -> ViewModelProvider.Factory = { LifecycleViewModelProviders.getViewModelFactory(this) }
) = viewModels<VM>(ownerProducer, factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> Fragment.parentViewModels(
        noinline ownerProducer: () -> ViewModelStoreOwner = { parentFragment!! },
        noinline factoryProducer: () -> ViewModelProvider.Factory = { LifecycleViewModelProviders.getViewModelFactory(parentFragment!!) }
) = viewModels<VM>(ownerProducer, factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> Fragment.targetViewModels(
        noinline ownerProducer: () -> ViewModelStoreOwner = { targetFragment!! },
        noinline factoryProducer: () -> ViewModelProvider.Factory = { LifecycleViewModelProviders.getViewModelFactory(targetFragment!!) }
) = viewModels<VM>(ownerProducer, factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> Fragment.activityViewModels(
        noinline factoryProducer: () -> ViewModelProvider.Factory = { LifecycleViewModelProviders.getViewModelFactory(requireActivity()) }
) = activityViewModels<VM>(factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.viewModels(
        noinline factoryProducer: () -> ViewModelProvider.Factory = { LifecycleViewModelProviders.getViewModelFactory(this) }
): Lazy<VM> = viewModels(factoryProducer)
