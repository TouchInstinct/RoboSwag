package ru.touchin.utils

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BundleExtractorDelegate<R, T>(private val initializer: (R, KProperty<*>) -> T) : ReadWriteProperty<R, T> {

    private object EMPTY

    private var value: Any? = EMPTY

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        this.value = value
    }

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        if (value == EMPTY) {
            value = initializer(thisRef, property)
        }

        @Suppress("UNCHECKED_CAST")
        return value as T
    }

}
