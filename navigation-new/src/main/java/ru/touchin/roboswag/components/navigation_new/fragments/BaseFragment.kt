package ru.touchin.roboswag.components.navigation_new.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import ru.touchin.roboswag.components.navigation_new.BuildConfig
import ru.touchin.roboswag.components.navigation.viewcontrollers.LifecycleLoggingObserver

open class BaseFragment<TActivity : FragmentActivity, TState : Parcelable>(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    companion object {
        private const val BASE_FRAGMENT_STATE_EXTRA = "BASE_FRAGMENT_STATE_EXTRA"

        fun args(state: Parcelable?): Bundle = Bundle().also { it.putParcelable(BASE_FRAGMENT_STATE_EXTRA, state) }

        // This method used to check unique state of each fragment.
        // If two fragments share same class for state, you should not pass state instance of current fragment to the one you transition to
        private fun <T : Parcelable> reserialize(parcelable: T): T {
            var parcel = Parcel.obtain()
            parcel.writeParcelable(parcelable, 0)
            val serializableBytes = parcel.marshall()
            parcel.recycle()
            parcel = Parcel.obtain()
            parcel.unmarshall(serializableBytes, 0, serializableBytes.size)
            parcel.setDataPosition(0)
            val result = parcel.readParcelable<T>(Thread.currentThread().contextClassLoader) ?: throw IllegalStateException("It must not be null")
            parcel.recycle()
            return result
        }
    }

    protected val view: View
        @JvmName("requireViewKtx") get() = requireView()

    protected val activity: TActivity
        @JvmName("requireActivityKtx") get() = requireActivity() as TActivity

    protected val context: Context
        @JvmName("requireContextKtx") get() = requireContext()

    protected lateinit var state: TState
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        state = savedInstanceState?.getParcelable<TState>(BASE_FRAGMENT_STATE_EXTRA)
                ?: arguments?.getParcelable(BASE_FRAGMENT_STATE_EXTRA)
                        ?: throw IllegalStateException("Fragment state can't be null")

        if (BuildConfig.DEBUG) {
            state = reserialize(state)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(LifecycleLoggingObserver(this))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(BASE_FRAGMENT_STATE_EXTRA, state)
    }

    fun <T : View> findViewById(@IdRes id: Int): T = view.findViewById(id)

    @ColorInt
    fun getColor(@ColorRes resId: Int): Int = ContextCompat.getColor(requireContext(), resId)

    fun getColorStateList(@ColorRes resId: Int): ColorStateList? = ContextCompat.getColorStateList(context, resId)

    fun getDrawable(@DrawableRes resId: Int): Drawable? = ContextCompat.getDrawable(context, resId)

}
