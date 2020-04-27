package ru.touchin.roboswag.navigation_base.fragments

import android.os.Parcel
import android.os.Parcelable

object EmptyState : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) = Unit

    override fun describeContents() = 0

    @JvmField
    val CREATOR = object : Parcelable.Creator<EmptyState> {
        override fun createFromParcel(parcel: Parcel) = EmptyState

        override fun newArray(size: Int): Array<EmptyState?> = arrayOfNulls(size)
    }

}
