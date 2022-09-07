package ru.touchin.basemap

import android.util.SparseArray

inline fun <K, V> MutableMap<K, V>.getOrPutIfNotNull(key: K, defaultValue: () -> V?): V? {
    val value = get(key)
    return if (value == null) {
        val value = defaultValue()
        when {
            value != null -> {
                put(key, value)
                value
            }
            else -> null
        }
    } else {
        value
    }
}

inline fun <V> SparseArray<V>.getOrPutIfNotNull(key: Int, defaultValue: () -> V?): V? {
    val value = get(key)
    return if (value == null) {
        val value = defaultValue()
        when {
            value != null -> {
                put(key, value)
                value
            }
            else -> null
        }
    } else {
        value
    }
}
