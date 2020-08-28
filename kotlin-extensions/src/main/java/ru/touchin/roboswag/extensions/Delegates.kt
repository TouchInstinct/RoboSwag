package ru.touchin.roboswag.extensions

import kotlin.properties.Delegates
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Simple observable delegate only for notification of new value.
 */
inline fun <T> Delegates.observable(
        initialValue: T,
        crossinline onChange: (newValue: T) -> Unit
): ReadWriteProperty<Any?, T> = object : ObservableProperty<T>(initialValue) {
    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) = onChange(newValue)
}

inline fun <T> Delegates.distinctUntilChanged(
        initialValue: T,
        crossinline onChange: (newValue: T) -> Unit
): ReadWriteProperty<Any?, T> = object : ObservableProperty<T>(initialValue) {
    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) =
            if (newValue != null && oldValue != newValue) onChange(newValue) else Unit
}
