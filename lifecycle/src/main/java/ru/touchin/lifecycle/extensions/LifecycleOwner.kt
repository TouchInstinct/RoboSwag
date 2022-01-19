package ru.touchin.lifecycle.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import ru.touchin.lifecycle.OnLifecycle
import kotlin.properties.ReadOnlyProperty

fun <R : LifecycleOwner, T> R.onCreateEvent(
        initializer: (R) -> T
): ReadOnlyProperty<R, T> = OnLifecycle(this, Lifecycle.Event.ON_CREATE, initializer)

fun <R : LifecycleOwner, T> R.onStartEvent(
        initializer: (R) -> T
): ReadOnlyProperty<R, T> = OnLifecycle(this, Lifecycle.Event.ON_START, initializer)

fun <R : LifecycleOwner, T> R.onResumeEvent(
        initializer: (R) -> T
): ReadOnlyProperty<R, T> = OnLifecycle(this, Lifecycle.Event.ON_RESUME, initializer)

fun <R : LifecycleOwner, T> R.onLifecycle(
        initializeEvent: Lifecycle.Event,
        initializer: (R) -> T
): ReadOnlyProperty<R, T> = OnLifecycle(this, initializeEvent, initializer)
