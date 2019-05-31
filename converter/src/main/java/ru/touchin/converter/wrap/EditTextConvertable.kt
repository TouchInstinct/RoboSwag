package ru.touchin.converter.wrap

import android.text.TextWatcher
import android.text.method.KeyListener
import android.widget.EditText

open class EditTextConvertable(val editText: EditText) : Convertable() {

    override fun setText(charSequence: CharSequence) {
        editText.setText(charSequence)
    }

    override fun getText(): CharSequence = editText.text

    override fun addTextChangedListener(watcher: TextWatcher) {
        editText.addTextChangedListener(watcher)
    }

    override fun setKeyListener(keyListener: KeyListener) {
        editText.keyListener = keyListener
    }

    override fun isEnabled(): Boolean = editText.isEnabled

    override fun setEnabled(enabled: Boolean) {
        editText.isEnabled = enabled
    }

    override fun setTextColor(colorInt: Int) {
        editText.setTextColor(colorInt)
    }

    override fun isFocused(): Boolean = editText.isFocused

}
