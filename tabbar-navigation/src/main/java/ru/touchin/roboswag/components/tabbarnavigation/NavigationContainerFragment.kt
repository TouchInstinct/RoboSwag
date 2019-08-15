package ru.touchin.roboswag.components.tabbarnavigation

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewController
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewControllerNavigation
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

class NavigationContainerFragment : Fragment() {

    companion object {
        private const val VIEW_CONTROLLER_CLASS_ARG = "VIEW_CONTROLLER_CLASS_ARG"
        private const val VIEW_CONTROLLER_STATE_ARG = "VIEW_CONTROLLER_STATE_ARG"
        private const val CONTAINER_VIEW_ID_ARG = "CONTAINER_VIEW_ID_ARG"
        private const val CONTAINER_LAYOUT_ID_ARG = "CONTAINER_LAYOUT_ID_ARG"
        private const val TRANSITION_ARG = "TRANSITION_ARG"

        fun args(
                cls: Class<out ViewController<*, *>>,
                state: Parcelable,
                @IdRes containerViewId: Int,
                @LayoutRes containerLayoutId: Int,
                transition: Int = FragmentTransaction.TRANSIT_NONE
        ) = Bundle().apply {
            putSerializable(VIEW_CONTROLLER_CLASS_ARG, cls)
            putParcelable(VIEW_CONTROLLER_STATE_ARG, state)
            putInt(CONTAINER_VIEW_ID_ARG, containerViewId)
            putInt(CONTAINER_LAYOUT_ID_ARG, containerLayoutId)
            putInt(TRANSITION_ARG, transition)
        }
    }

    val navigation by lazy {
        ViewControllerNavigation<BaseNavigationActivity>(
                requireContext(),
                childFragmentManager,
                containerViewId,
                transition
        )
    }

    private var containerViewId = 0

    private var containerLayoutId = 0

    private var transition = 0

    @Suppress("UNCHECKED_CAST")
    fun getViewControllerClass(): Class<out ViewController<out BaseNavigationActivity, Parcelable>> =
            arguments?.getSerializable(VIEW_CONTROLLER_CLASS_ARG) as Class<out ViewController<out BaseNavigationActivity, Parcelable>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val args = arguments ?: throw ShouldNotHappenException("Fragment is not instantiable without arguments")
            with(args) {
                containerViewId = getInt(CONTAINER_VIEW_ID_ARG)
                containerLayoutId = getInt(CONTAINER_LAYOUT_ID_ARG)
                transition = getInt(TRANSITION_ARG)
            }
            navigation.setInitialViewController(getViewControllerClass(), args.getParcelable(VIEW_CONTROLLER_STATE_ARG))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(containerLayoutId, container, false)

}
