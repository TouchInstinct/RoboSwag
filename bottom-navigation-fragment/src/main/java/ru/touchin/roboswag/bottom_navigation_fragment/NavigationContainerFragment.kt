package ru.touchin.roboswag.bottom_navigation_fragment

import android.os.Parcelable
import ru.touchin.roboswag.bottom_navigation_base.BaseNavigationContainerFragment
import ru.touchin.roboswag.navigation_base.FragmentNavigation
import ru.touchin.roboswag.navigation_base.fragments.StatefulFragment

class NavigationContainerFragment :
        BaseNavigationContainerFragment<StatefulFragment<out BottomNavigationActivity, Parcelable>, FragmentNavigation>() {

    override val navigation by lazy {
        FragmentNavigation(
                requireContext(),
                childFragmentManager,
                containerViewId,
                transition
        )
    }

    override fun onContainerCreated() {
        navigation.setInitial(getContainedClass().kotlin, arguments?.getParcelable(FRAGMENT_STATE_ARG)!!)
    }

}
