package ru.touchin.converter.wrap

import android.text.TextWatcher
import android.text.method.KeyListener
import androidx.annotation.ColorInt

abstract class Convertable {

    abstract fun setText(charSequence: CharSequence)

    abstract fun getText(): CharSequence

    abstract fun addTextChangedListener(watcher: TextWatcher)

    abstract fun setKeyListener(keyListener: KeyListener)

    abstract fun isEnabled(): Boolean

    abstract fun setEnabled(enabled: Boolean)

    abstract fun setTextColor(@ColorInt colorInt: Int)

    abstract fun isFocused(): Boolean

}
