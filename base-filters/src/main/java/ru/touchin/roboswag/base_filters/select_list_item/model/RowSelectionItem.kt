package ru.touchin.roboswag.base_filters.select_list_item.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RowSelectionItem(
        val id: Int,
        val title: String,
        val isSelected: Boolean = false
): Parcelable
