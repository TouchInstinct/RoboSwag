package ru.touchin.extensions

import android.content.res.TypedArray
import androidx.annotation.StyleableRes
import androidx.core.content.res.getResourceIdOrThrow

fun TypedArray.getResourceIdOrNull(@StyleableRes index: Int) = runCatching { getResourceIdOrThrow(index) }.getOrNull()
