package ru.touchin.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.lang.IllegalStateException
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Delegate that allows to lazily initialize value on certain lifecycle event
 * @param initializeEvent is event when value should be initialize
 * @param initializer callback that handles value initialization
 */

class OnLifecycle<R : LifecycleOwner, T>(
        private val lifecycleOwner: R,
        private val initializeEvent: Lifecycle.Event,
        private val initializer: (R) -> T
) : ReadOnlyProperty<R, T> {

    private var value: T? = null

    init {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (initializeEvent == event && value == null) {
                    value = initializer.invoke(lifecycleOwner)
                }
            }
        })
    }

    override fun getValue(thisRef: R, property: KProperty<*>) = value
            ?: throw IllegalStateException("Can't get access to value before $initializeEvent. Current is ${thisRef.lifecycle.currentState}")

}
