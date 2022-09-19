package ru.touchin.roboswag.base_filters.range

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import ru.touchin.roboswag.base_filters.databinding.ViewHintInputBinding

class HintInputView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewHintInputBinding.inflate(LayoutInflater.from(context), this)

    var inputText: String = ""
        set(value) {
            setText(value)
            field = value
        }

    fun setHint(value: String?) {
        binding.startHint.text = value.orEmpty()
    }

    private fun setText(value: String) {
        binding.editText.run {
            setText(value)
            setSelection(text?.length ?: 0)
        }
    }

    fun getEditText() = binding.editText

}
