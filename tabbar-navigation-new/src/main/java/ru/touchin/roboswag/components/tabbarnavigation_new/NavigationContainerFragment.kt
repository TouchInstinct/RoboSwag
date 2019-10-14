package ru.touchin.roboswag.components.tabbarnavigation_new

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.touchin.roboswag.components.navigation_new.FragmentNavigation
import ru.touchin.roboswag.components.navigation_new.fragments.BaseFragment
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

class NavigationContainerFragment : Fragment() {

    companion object {
        private const val FRAGMENT_CLASS_ARG = "FRAGMENT_CLASS_ARG"
        private const val FRAGMENT_STATE_ARG = "FRAGMENT_STATE_ARG"
        private const val CONTAINER_VIEW_ID_ARG = "CONTAINER_VIEW_ID_ARG"
        private const val CONTAINER_LAYOUT_ID_ARG = "CONTAINER_LAYOUT_ID_ARG"
        private const val TRANSITION_ARG = "TRANSITION_ARG"

        fun args(
                cls: Class<out BaseFragment<*, *>>,
                state: Parcelable,
                @IdRes containerViewId: Int,
                @LayoutRes containerLayoutId: Int,
                transition: Int = FragmentTransaction.TRANSIT_NONE
        ) = Bundle().apply {
            putSerializable(FRAGMENT_CLASS_ARG, cls)
            putParcelable(FRAGMENT_STATE_ARG, state)
            putInt(CONTAINER_VIEW_ID_ARG, containerViewId)
            putInt(CONTAINER_LAYOUT_ID_ARG, containerLayoutId)
            putInt(TRANSITION_ARG, transition)
        }
    }

    val navigation by lazy {
        FragmentNavigation(
                requireContext(),
                childFragmentManager,
                containerViewId,
                transition
        )
    }

    @IdRes
    private var containerViewId = 0

    @LayoutRes
    private var containerLayoutId = 0

    private var transition = 0

    @Suppress("UNCHECKED_CAST")
    fun getFragmentClass(): Class<out BaseFragment<out BottomNavigationActivity, Parcelable>> =
            arguments?.getSerializable(FRAGMENT_CLASS_ARG) as Class<out BaseFragment<out BottomNavigationActivity, Parcelable>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            transition = getInt(TRANSITION_ARG)
            containerViewId = getInt(CONTAINER_VIEW_ID_ARG)
            containerLayoutId = getInt(CONTAINER_LAYOUT_ID_ARG)

            if (savedInstanceState == null) {
                navigation.setInitial(getFragmentClass(), BaseFragment.args(getParcelable(FRAGMENT_STATE_ARG)))
            }
        } ?: throw ShouldNotHappenException("Fragment is not instantiable without arguments")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(containerLayoutId, container, false)

}
