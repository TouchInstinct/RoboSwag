package ru.touchin.roboswag.bottom_navigation_viewcontroller

import android.os.Parcelable
import ru.touchin.roboswag.bottom_navigation_base.BaseNavigationContainerFragment
import ru.touchin.roboswag.navigation_viewcontroller.viewcontrollers.ViewController
import ru.touchin.roboswag.navigation_viewcontroller.viewcontrollers.ViewControllerNavigation

class NavigationContainerFragment :
        BaseNavigationContainerFragment<
                ViewController<out BottomNavigationActivity, Parcelable>,
                ViewControllerNavigation<BottomNavigationActivity>>() {

    override val navigation by lazy {
        ViewControllerNavigation<BottomNavigationActivity>(
                requireContext(),
                childFragmentManager,
                containerViewId,
                transition
        )
    }

    override fun onContainerCreated() {
        navigation.setInitialViewController(getContainedClass(), arguments?.getParcelable(FRAGMENT_STATE_ARG)!!)
    }

}
