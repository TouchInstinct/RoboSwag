package ru.touchin.roboswag.navigation_viewcontroller.viewcontrollers

import android.os.Parcelable
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewControllerViewBindingDelegate<T : ViewBinding, TActivity : FragmentActivity, TState : Parcelable>(
        val viewController: ViewController<TActivity, TState>,
        val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<ViewController<TActivity, TState>, T> {
    private var binding: T? = null

    init {
        viewController.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                viewController.fragment.viewLifecycleOwnerLiveData.observe(viewController) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: ViewController<TActivity, TState>, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) {
            return binding
        }

        return viewBindingFactory(viewController.view).also { this.binding = it }
    }
}

fun <T : ViewBinding, TActivity : FragmentActivity, TState : Parcelable>
        ViewController<TActivity, TState>.viewBinding(viewBindingFactory: (View) -> T) =
        ViewControllerViewBindingDelegate(this, viewBindingFactory)
