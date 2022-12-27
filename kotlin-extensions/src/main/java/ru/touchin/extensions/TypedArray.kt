package ru.touchin.extensions

import android.content.res.TypedArray
import androidx.annotation.StyleableRes

private const val DEFAULT_VALUE = -1

fun TypedArray.getResourceIdOrNull(@StyleableRes index: Int) = getResourceId(index, DEFAULT_VALUE)
        .takeIf { it != DEFAULT_VALUE }

fun TypedArray.getColorOrNull(@StyleableRes index: Int) = getColor(index, DEFAULT_VALUE)
        .takeIf { it != DEFAULT_VALUE }
