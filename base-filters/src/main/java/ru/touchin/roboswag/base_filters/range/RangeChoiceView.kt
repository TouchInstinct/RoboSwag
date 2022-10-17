package ru.touchin.roboswag.base_filters.range

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.updateLayoutParams
import com.google.android.material.slider.RangeSlider
import ru.touchin.roboswag.base_filters.R
import ru.touchin.roboswag.base_filters.databinding.RangeChoiceViewBinding
import ru.touchin.roboswag.base_filters.range.model.FilterRangeItem
import ru.touchin.roboswag.base_filters.range.model.SelectedValues
import kotlin.properties.Delegates

typealias FilterRangeChanged = (FilterRangeItem) -> Unit

class RangeChoiceView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val defaultTheme = R.style.Theme_FilterRangeSlider
    private var binding: RangeChoiceViewBinding by Delegates.notNull()

    private var valueChangedAction: FilterRangeChanged? = null

    private var rangeItem: FilterRangeItem? = null
        set(value) {
            field = value

            binding.fromInput.inputText = value?.selectedValues?.min?.toString()
                    ?: value?.start?.toString().orEmpty()
            binding.toInput.inputText = value?.selectedValues?.max?.toString()
                    ?: value?.end?.toString().orEmpty()

            binding.rangeSlider.run {
                values = listOf(
                        value?.selectedValues?.min?.toFloat() ?: value?.start?.toFloat(),
                        value?.selectedValues?.max?.toFloat() ?: value?.end?.toFloat()
                )
            }
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.FilterRangeChoice, defStyleAttr, defStyleRes) {
            val theme = getResourceId(R.styleable.FilterRangeChoice_filterRange_theme, defaultTheme)
            val themeContext = ContextThemeWrapper(context, theme)
            binding = RangeChoiceViewBinding.inflate(LayoutInflater.from(themeContext), this@RangeChoiceView)

            binding.fromInput.setHint(getString(R.styleable.FilterRangeChoice_filterRange_startHint))
            binding.toInput.setHint(getString(R.styleable.FilterRangeChoice_filterRange_endHint))
            binding.rangeSliderGuideline.updateLayoutParams<MarginLayoutParams> {
                topMargin = getDimension(R.styleable.FilterRangeChoice_filterRange_sliderMargin, 0f).toInt()
            }
        }
    }

    fun setupRangeValues(
            rangeFilterItem: FilterRangeItem,
            onChangeCallback: FilterRangeChanged
    ) {
        rangeItem = rangeFilterItem
        valueChangedAction = onChangeCallback

        with(binding) {
            addChangeValuesListener()
            setupRangeSlider(rangeFilterItem)
        }
    }

    fun resetRangeValue() {
        rangeItem = rangeItem?.resetSelectedValues()
    }

    private fun addChangeValuesListener() {
        binding.fromInput.addChangeValueListener { rangeItem?.setValue(selectedMinValue = it.toIntOrNull()) }
        binding.toInput.addChangeValueListener { rangeItem?.setValue(selectedMaxValue = it.toIntOrNull()) }
    }

    private fun setupRangeSlider(rangeFilterItem: FilterRangeItem) {
        with(binding) {
            rangeSlider.apply {
                valueFrom = rangeFilterItem.start.toFloat()
                valueTo = rangeFilterItem.end.toFloat()
                points = rangeFilterItem.intermediates
            }

            rangeSlider.addOnChangeListener { _, _, _ ->
                fromInput.inputText = rangeSlider.values[0].toInt().toString()
                toInput.inputText = rangeSlider.values[1].toInt().toString()
            }

            rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
                @SuppressLint("RestrictedApi")
                override fun onStartTrackingTouch(slider: RangeSlider) = Unit

                @SuppressLint("RestrictedApi")
                override fun onStopTrackingTouch(slider: RangeSlider) {
                    binding.rangeSlider.apply {
                        when (focusedThumbIndex) {
                            0 -> {
                                rangeItem = rangeItem?.setValue(selectedMinValue = from().toInt())
                                rangeItem?.let { valueChangedAction?.invoke(it) }
                            }
                            1 -> {
                                rangeItem = rangeItem?.setValue(selectedMaxValue = to().toInt())
                                rangeItem?.let { valueChangedAction?.invoke(it) }
                            }
                        }
                    }
                }
            })
        }
    }

    private fun HintInputView.addChangeValueListener(updateValue: (String) -> FilterRangeItem?) {
        setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                rangeItem = updateValue(view.text.toString().filterNot { it.isWhitespace() })
                rangeItem?.let { valueChangedAction?.invoke(it) }
            }
            false
        }
    }

    private fun RangeSlider.from() = values[0].toInt().toString()

    private fun RangeSlider.to() = values[1].toInt().toString()

    @SuppressWarnings("detekt.ComplexMethod")
    private fun FilterRangeItem.setValue(
            selectedMaxValue: Int? = selectedValues?.max,
            selectedMinValue: Int? = selectedValues?.min
    ): FilterRangeItem {
        val isMaxValueUpdated = selectedMaxValue != selectedValues?.max
        val isMinValueUpdated = selectedMinValue != selectedValues?.min

        val isMinValueOutOfRange = selectedMinValue != null && isMinValueUpdated && selectedMinValue > (selectedMaxValue ?: end)
        val isMaxValueOutOfRange = selectedMaxValue != null && isMaxValueUpdated && selectedMaxValue < (selectedMinValue ?: start)

        val updatedValues = when {
            selectedMaxValue == end && selectedMinValue == start -> null
            isMinValueOutOfRange -> SelectedValues(
                    max = selectedMaxValue ?: end,
                    min = selectedMaxValue ?: end
            )
            isMaxValueOutOfRange -> SelectedValues(
                    max = selectedMinValue ?: start,
                    min = selectedMinValue ?: start
            )
            else -> SelectedValues(
                    max = selectedMaxValue?.takeIf { it < end } ?: end,
                    min = selectedMinValue?.takeIf { it > start } ?: start
            )
        }

        return copyWithSelectedValue(selectedValues = updatedValues)
    }

    private fun FilterRangeItem.resetSelectedValues() = copyWithSelectedValue(selectedValues = null)

}
