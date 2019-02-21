package ru.touchin.extensions

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.TextView

var TextView.drawableStart: Drawable?
    get() = drawables[0]
    set(value) = setDrawables(value, drawableTop, drawableEnd, drawableBottom)

var TextView.drawableTop: Drawable?
    get() = drawables[1]
    set(value) = setDrawables(drawableStart, value, drawableEnd, drawableBottom)

var TextView.drawableEnd: Drawable?
    get() = drawables[2]
    set(value) = setDrawables(drawableStart, drawableTop, value, drawableBottom)

var TextView.drawableBottom: Drawable?
    get() = drawables[3]
    set(value) = setDrawables(drawableStart, drawableTop, drawableEnd, value)

private inline val TextView.drawables: Array<Drawable?>
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) compoundDrawablesRelative else compoundDrawables

private fun TextView.setDrawables(start: Drawable?, top: Drawable?, end: Drawable?, bottom: Drawable?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
    } else {
        setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
    }
}
