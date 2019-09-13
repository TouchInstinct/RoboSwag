package ru.touchin.edittextformatters

import android.text.TextWatcher
import android.text.method.KeyListener
import ru.touchin.edittextformatters.converter.wrap.Convertible

class MockConvertible : Convertible {

    var text = ""
    var focused: Boolean = true
    var enable: Boolean = true

    override fun setText(charSequence: CharSequence, placeCursorToTheEnd: Boolean, incrementCursorPosition: Boolean) {
        text = charSequence.toString()
    }

    override fun getText(): CharSequence = text

    override fun addTextChangedListener(watcher: TextWatcher) {
    }

    override fun setKeyListener(keyListener: KeyListener) {
    }

    override fun isEnabled(): Boolean = enable

    override fun setEnabled(enabled: Boolean) {
        enable = enabled
    }

    override fun setTextColor(colorInt: Int) {
    }

    override fun addOnFocusChangedListener(onFocusChangedListener: (Boolean) -> Unit) {
    }

    override fun isFocused(): Boolean = focused

}