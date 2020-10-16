package ru.touchin.roboswag.bottom_navigation_base

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.touchin.roboswag.core.utils.ShouldNotHappenException
import ru.touchin.roboswag.navigation_base.FragmentNavigation

abstract class BaseNavigationContainerFragment<TContained, TNavigation : FragmentNavigation> : Fragment() {

    companion object {
        const val TRANSITION_ARG = "TRANSITION_ARG"
        const val FRAGMENT_STATE_ARG = "FRAGMENT_STATE_ARG"
        const val CONTAINED_CLASS_ARG = "FRAGMENT_CLASS_ARG"
        const val CONTAINER_VIEW_ID_ARG = "CONTAINER_VIEW_ID_ARG"
        const val CONTAINER_LAYOUT_ID_ARG = "CONTAINER_LAYOUT_ID_ARG"

        fun args(
                cls: Class<*>,
                state: Parcelable,
                @IdRes containerViewId: Int,
                @LayoutRes containerLayoutId: Int,
                transition: Int = FragmentTransaction.TRANSIT_NONE
        ) = Bundle().apply {
            putInt(TRANSITION_ARG, transition)
            putInt(CONTAINER_VIEW_ID_ARG, containerViewId)
            putInt(CONTAINER_LAYOUT_ID_ARG, containerLayoutId)
            putParcelable(FRAGMENT_STATE_ARG, state)
            putSerializable(CONTAINED_CLASS_ARG, cls)
        }
    }

    abstract val navigation: TNavigation

    @IdRes
    protected var containerViewId = 0
        private set

    @LayoutRes
    protected var containerLayoutId = 0
        private set

    protected var transition = 0
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            transition = args.getInt(TRANSITION_ARG)
            containerViewId = args.getInt(CONTAINER_VIEW_ID_ARG)
            containerLayoutId = args.getInt(CONTAINER_LAYOUT_ID_ARG)

            if (savedInstanceState == null) {
                onContainerCreated()
            }
        } ?: throw ShouldNotHappenException("Fragment is not instantiable without arguments")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(containerLayoutId, container, false)

    protected abstract fun onContainerCreated()

    @Suppress("UNCHECKED_CAST")
    fun getContainedClass(): Class<TContained> =
            arguments?.getSerializable(CONTAINED_CLASS_ARG) as Class<TContained>

}
