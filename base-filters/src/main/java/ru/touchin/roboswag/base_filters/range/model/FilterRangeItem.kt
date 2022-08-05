package ru.touchin.roboswag.base_filters.range.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class FilterRangeItem(
        val id: String,
        val start: Int,
        val end: Int,
        val title: String,
        val intermediates: List<Int>? = null,
        val step: Int? = null,
        val selectedValues: SelectedValues? = null
) : Parcelable {

    fun isCorrectValues() = end > start

    fun copyWithSelectedValue(selectedValues: SelectedValues?) = FilterRangeItem(
            id = id,
            start = start,
            end = end,
            title = title,
            intermediates = intermediates,
            step = step,
            selectedValues = selectedValues
    )
}

@Parcelize
data class SelectedValues(
        val max: Int,
        val min: Int
) : Parcelable
