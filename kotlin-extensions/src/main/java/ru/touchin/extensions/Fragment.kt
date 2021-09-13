package ru.touchin.extensions

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import ru.touchin.utils.BundleExtractorDelegate
import kotlin.properties.ReadWriteProperty

inline fun <reified T> Fragment.args(
        key: String? = null,
        defaultValue: T? = null
): ReadWriteProperty<Fragment, T> {
    return BundleExtractorDelegate { thisRef, property ->
        val bundleKey = key ?: property.name
        extractFromBundle(thisRef.arguments, bundleKey, defaultValue)
    }
}

fun <T : Fragment> T.withArgs(receiver: Bundle.() -> Unit): T {
    arguments = Bundle().apply(receiver)
    return this
}

fun <T : Fragment> T.withParcelable(key: String, parcelable: Parcelable): T = withArgs {
    putParcelable(key, parcelable)
}

inline fun <reified T> extractFromBundle(
        bundle: Bundle?,
        key: String? = null,
        defaultValue: T? = null
): T {
    val result = bundle?.get(key) ?: defaultValue
    if (result != null && result !is T) {
        throw ClassCastException("Property for $key has different class type")
    }
    return result as T
}
