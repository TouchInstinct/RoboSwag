package ru.touchin.roboswag.base_filters.tags.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class FilterItem(
        val id: Int,
        val title: String,
        val properties: List<FilterProperty>
) : Parcelable

@Parcelize
open class FilterProperty(
        val id: Int,
        val title: String,
        val excludes: List<PropertyExcludingValue>,
        val isSelected: Boolean = false
) : Parcelable {

    open fun copyWithSelected(isSelected: Boolean) = FilterProperty(
            id = id,
            title = title,
            excludes = excludes,
            isSelected = isSelected
    )
}

@Parcelize
open class PropertyExcludingValue(
        val id: Int
) : Parcelable
