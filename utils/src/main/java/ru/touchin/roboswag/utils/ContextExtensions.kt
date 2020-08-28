package ru.touchin.roboswag.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.getColorSimple(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)

fun Context.getDrawableSimple(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(this, id)
