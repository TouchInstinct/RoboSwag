package ru.touchin.roboswag.navigation_base.extensions

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import ru.touchin.roboswag.navigation_base.fragments.EmptyState

// This method used to check unique state of each fragment.
// If two fragments share same class for state, you should not pass state instance of current fragment to the one you transition to
@SuppressLint("Recycle")
fun <T : Parcelable> Parcelable.reserialize(): T {
    var parcel = Parcel.obtain()

    parcel.writeParcelable(this, 0)

    val serializableBytes = parcel.marshall()

    parcel.recycle()

    parcel = Parcel.obtain().apply {
        unmarshall(serializableBytes, 0, serializableBytes.size)
        setDataPosition(0)
    }

    val result = parcel.readParcelable<T>(Thread.currentThread().contextClassLoader)
            ?: throw IllegalStateException("It must not be null")

    parcel.recycle()

    return result
}

@SuppressLint("Recycle")
fun Parcelable.copy(): Parcelable =
        if (this is EmptyState) {
            EmptyState
        } else {
            val parcel = Parcel.obtain()

            parcel.writeParcelable(this, 0)
            parcel.setDataPosition(0)

            val result = parcel.readParcelable<Parcelable>(
                    javaClass.classLoader ?: Thread.currentThread().contextClassLoader
            ) ?: throw IllegalStateException("Failed to copy tab state")

            parcel.recycle()

            result
        }
