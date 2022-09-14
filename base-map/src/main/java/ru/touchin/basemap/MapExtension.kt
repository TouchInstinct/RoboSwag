package ru.touchin.basemap

import android.util.SparseArray

inline fun <K, V> MutableMap<K, V>.getOrPutIfNotNull(key: K, defaultValue: () -> V?): V? =
        get(key) ?: defaultValue()?.also { value ->
            put(key, value)
        }

inline fun <V> SparseArray<V>.getOrPutIfNotNull(key: Int, defaultValue: () -> V?): V? =
        get(key) ?: defaultValue()?.also { value ->
            put(key, value)
        }
