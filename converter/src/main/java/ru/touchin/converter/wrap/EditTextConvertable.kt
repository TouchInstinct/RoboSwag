package ru.touchin.converter.wrap

import android.text.TextWatcher
import android.text.method.KeyListener
import android.widget.EditText

/**
 * [Convertable] implementation for EditText class
 */
open class EditTextConvertable(val editText: EditText) : Convertable {

    /**
     * Set text to EditText with cursor handling
     * @param charSequence text
     * @param placeCursorToTheEnd is cursor must be moved to the end of text
     * @param incrementCursorPosition increment cursor position if it's in the middle of text
     */
    override fun setText(charSequence: CharSequence, placeCursorToTheEnd: Boolean, incrementCursorPosition: Boolean) {
        val cursorPositionFromEnd = getText().length - editText.selectionEnd
        editText.setText(charSequence)
        if (editText.isFocused) {
            if (placeCursorToTheEnd || cursorPositionFromEnd > getText().length) {
                editText.setSelection(getText().length)
            } else if (incrementCursorPosition && cursorPositionFromEnd > 0) {
                editText.setSelection(getText().length - cursorPositionFromEnd + 1)
            } else {
                editText.setSelection(getText().length - cursorPositionFromEnd)
            }
        }
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

    override fun addOnFocusChangedListener(onFocusChangedListener: (Boolean) -> Unit) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            onFocusChangedListener(hasFocus)
        }
    }

}
