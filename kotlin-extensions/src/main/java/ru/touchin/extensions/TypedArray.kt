package ru.touchin.extensions

import android.content.res.TypedArray
import androidx.annotation.StyleableRes

private const val NOT_FOUND_VALUE = -1

fun TypedArray.getResourceIdOrNull(@StyleableRes index: Int) = getResourceId(index, NOT_FOUND_VALUE)
        .takeIf { it != NOT_FOUND_VALUE }

fun TypedArray.getColorOrNull(@StyleableRes index: Int) = getColor(index, NOT_FOUND_VALUE)
        .takeIf { it != NOT_FOUND_VALUE }
