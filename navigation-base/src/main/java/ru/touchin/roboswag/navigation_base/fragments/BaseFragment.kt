package ru.touchin.roboswag.navigation_base.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

open class BaseFragment<TActivity : FragmentActivity> : Fragment {

    constructor() : super()

    constructor(@LayoutRes layoutRes: Int) : super(layoutRes)

    protected val view: View
        @JvmName("requireViewKtx") get() = requireView()

    protected val activity: TActivity
        @JvmName("requireActivityKtx") get() = requireActivity() as TActivity

    protected val context: Context
        @JvmName("requireContextKtx") get() = requireContext()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(LifecycleLoggingObserver(this))
    }

    fun <T : View> findViewById(@IdRes id: Int): T = view.findViewById(id)

    @ColorInt
    fun getColor(@ColorRes resId: Int): Int = ContextCompat.getColor(requireContext(), resId)

    fun getColorStateList(@ColorRes resId: Int): ColorStateList? = ContextCompat.getColorStateList(context, resId)

    fun getDrawable(@DrawableRes resId: Int): Drawable? = ContextCompat.getDrawable(context, resId)

}
