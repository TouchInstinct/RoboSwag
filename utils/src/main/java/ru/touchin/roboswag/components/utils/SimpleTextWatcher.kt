package ru.touchin.roboswag.components.utils

import android.text.Editable
import android.text.TextWatcher

class SimpleTextWatcher(private val onTextChanged: (Editable) -> Unit) : TextWatcher {

    private var ignore = false

    override fun afterTextChanged(s: Editable) {
        doPreventListenerLoop { onTextChanged(s) }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

    private fun doPreventListenerLoop(callback: () -> Unit) {
        if (ignore) return
        ignore = true
        callback()
        ignore = false
    }
}
