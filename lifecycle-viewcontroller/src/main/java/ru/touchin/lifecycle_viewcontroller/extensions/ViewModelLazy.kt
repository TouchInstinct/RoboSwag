package ru.touchin.lifecycle_viewcontroller.extensions

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import ru.touchin.lifecycle_viewcontroller.viewmodel.LifecycleViewModelProviders
import ru.touchin.roboswag.navigation_viewcontroller.viewcontrollers.ViewController
import androidx.fragment.app.activityViewModels as androidActivityViewModels
import androidx.fragment.app.viewModels as androidViewModels

@MainThread
inline fun <reified VM : ViewModel> ViewController<*, *>.viewModels(
        noinline ownerProducer: () -> ViewModelStoreOwner = { this.fragment },
        noinline factoryProducer: () -> ViewModelProvider.Factory = { LifecycleViewModelProviders.getViewModelFactory(this) }
) = this.fragment.androidViewModels<VM>(ownerProducer, factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> ViewController<*, *>.parentViewModels(
        noinline ownerProducer: () -> ViewModelStoreOwner = { this.fragment.parentFragment!! },
        noinline factoryProducer: () -> ViewModelProvider.Factory = {
            LifecycleViewModelProviders.getViewModelFactory(this.fragment.parentFragment!!)
        }
) = viewModels<VM>(ownerProducer, factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> ViewController<*, *>.targetViewModels(
        noinline ownerProducer: () -> ViewModelStoreOwner = { this.fragment.targetFragment!! },
        noinline factoryProducer: () -> ViewModelProvider.Factory = {
            LifecycleViewModelProviders.getViewModelFactory(this.fragment.targetFragment!!)
        }
) = viewModels<VM>(ownerProducer, factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> ViewController<*, *>.activityViewModels(
        noinline factoryProducer: () -> ViewModelProvider.Factory = { LifecycleViewModelProviders.getViewModelFactory(activity) }
) = this.fragment.androidActivityViewModels<VM>(factoryProducer)
