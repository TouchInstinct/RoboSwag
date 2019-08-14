package ru.touchin.tabbarnavigation

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewController
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewControllerNavigation

abstract class NavigationContainerFragment : Fragment() {

    companion object {
        private const val VIEW_CONTROLLER_CLASS_ARG = "VIEW_CONTROLLER_CLASS_ARG"
        private const val VIEW_CONTROLLER_STATE_ARG = "VIEW_CONTROLLER_STATE_ARG"

        fun args(cls: Class<out ViewController<*, *>>, state: Parcelable) = Bundle().apply {
            putSerializable(VIEW_CONTROLLER_CLASS_ARG, cls)
            putParcelable(VIEW_CONTROLLER_STATE_ARG, state)
        }
    }

    val navigation by lazy {
        ViewControllerNavigation<NavigationActivity>(
                requireContext(),
                childFragmentManager,
                getContainerViewId(),
                getTransition()
        )
    }

    abstract fun getContainerViewId(): Int

    open fun getTransition() = FragmentTransaction.TRANSIT_NONE

    @Suppress("UNCHECKED_CAST")
    fun getViewControllerClass(): Class<out ViewController<out NavigationActivity, Parcelable>> =
            arguments?.getSerializable(VIEW_CONTROLLER_CLASS_ARG) as Class<out ViewController<out NavigationActivity, Parcelable>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val args = arguments ?: return
            navigation.setInitialViewController(getViewControllerClass(), args.getParcelable(VIEW_CONTROLLER_STATE_ARG))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(getContainerViewId(), container, false)

}
