package ru.touchin.roboswag.views.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.withStyledAttributes
import ru.touchin.roboswag.extensions.observable
import ru.touchin.roboswag.extensions.setOnRippleClickListener
import ru.touchin.roboswag.components.views.R
import ru.touchin.roboswag.components.views.databinding.ProgressViewBinding
import kotlin.properties.Delegates

//TODO make customizable views list and views style
class LoadingContentView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : Switcher(context, attrs) {

    private val binding = ProgressViewBinding.inflate(LayoutInflater.from(context), this)

    var state by Delegates.observable<State>(State.Loading, this::updateView)

    init {
        if (attrs != null) {
            context.withStyledAttributes(attrs, R.styleable.LoadingContentView, defStyleAttr, 0) {
                if (hasValue(R.styleable.LoadingContentView_stubText)) {
                    setStubText(getString(R.styleable.LoadingContentView_stubText))
                }
            }
        }
    }

    private fun setStubText(text: String?) {
        binding.textStub.text = text
    }

    private fun updateView(state: State) {
        if (state == State.ShowContent) {
            getChildAt(childCount - 1)?.let { showChild(it.id) }
        } else {
            when (state) {
                is State.Stub -> {
                    setStubText(state.stubText)
                    showChild(R.id.text_stub)
                }
                is State.Loading -> {
                    showChild(R.id.progress_bar)
                }
                is State.Error -> {
                    binding.apply {
                        errorText.text = state.errorText
                        errorRepeatButton.setOnRippleClickListener { state.action.invoke() }
                        errorRepeatButton.text = state.repeatButtonText
                        showChild(R.id.error_with_repeat)
                    }
                }
            }
        }
    }

    sealed class State {
        object ShowContent : State()
        data class Stub(val stubText: String) : State()
        object Loading : State()
        data class Error(val action: () -> Unit, val errorText: String, val repeatButtonText: String) : State()
    }

}
