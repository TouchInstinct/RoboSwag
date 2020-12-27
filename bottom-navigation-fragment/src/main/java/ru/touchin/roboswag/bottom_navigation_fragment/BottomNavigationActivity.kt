package ru.touchin.roboswag.bottom_navigation_fragment

import ru.touchin.roboswag.bottom_navigation_base.BaseBottomNavigationActivity
import ru.touchin.roboswag.navigation_base.FragmentNavigation

abstract class BottomNavigationActivity :
        BaseBottomNavigationActivity<FragmentNavigation, BottomNavigationFragment, NavigationContainerFragment>() {

    override val navigation by lazy {
        FragmentNavigation(
                this,
                supportFragmentManager,
                fragmentContainerViewId,
                transition
        )
    }

}
