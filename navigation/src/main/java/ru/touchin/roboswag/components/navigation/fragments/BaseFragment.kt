package ru.touchin.roboswag.components.navigation.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import butterknife.ButterKnife
import ru.touchin.roboswag.components.navigation.BuildConfig
import ru.touchin.roboswag.components.navigation.viewcontrollers.LifecycleLoggingObserver

open class BaseFragment<TActivity : FragmentActivity, TState : Parcelable> constructor() : Fragment() {

    companion object {
        private const val BASE_FRAGMENT_STATE_EXTRA = "BASE_FRAGMENT_STATE_EXTRA"
        private const val BASE_FRAGMENT_LAYOUT_RES_EXTRA = "BASE_FRAGMENT_LAYOUT_RES_EXTRA"

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

    protected lateinit var view: View
        @JvmName("requireView") get
        private set

    protected lateinit var activity: TActivity
        @JvmName("getTypedActivity") get
        private set

    protected lateinit var context: Context
        @JvmName("requireApplicationContext") get
        private set

    protected lateinit var state: TState
        private set

    @LayoutRes
    private var fragmentLayoutRes: Int? = null

    constructor(@LayoutRes layoutRes: Int) : this() {
        fragmentLayoutRes = layoutRes
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        activity = requireActivity() as TActivity
        this.context = requireContext().applicationContext
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        savedInstanceState?.getInt(BASE_FRAGMENT_LAYOUT_RES_EXTRA)?.takeIf { it != 0 }?.let { fragmentLayoutRes = it }
        state = savedInstanceState?.getParcelable<TState>(BASE_FRAGMENT_STATE_EXTRA)
                ?: arguments?.getParcelable(BASE_FRAGMENT_STATE_EXTRA)
                        ?: throw IllegalStateException("Fragment state can't be null")

        if (BuildConfig.DEBUG) {
            state = reserialize(state)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(
                fragmentLayoutRes ?: throw IllegalStateException("Layout res can't be null"),
                container,
                false
        )

        ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(LifecycleLoggingObserver())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(BASE_FRAGMENT_STATE_EXTRA, state)
        fragmentLayoutRes?.let { outState.putInt(BASE_FRAGMENT_LAYOUT_RES_EXTRA, it) }
    }

    fun <T : View> findViewById(@IdRes id: Int): T = view.findViewById(id)

    @ColorInt
    fun getColor(@ColorRes resId: Int): Int = ContextCompat.getColor(requireContext(), resId)

    fun getColorStateList(@ColorRes resId: Int): ColorStateList? = ContextCompat.getColorStateList(context, resId)

    fun getDrawable(@DrawableRes resId: Int): Drawable? = ContextCompat.getDrawable(context, resId)

}
