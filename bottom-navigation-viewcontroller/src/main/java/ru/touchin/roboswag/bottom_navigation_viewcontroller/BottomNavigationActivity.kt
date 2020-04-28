package ru.touchin.roboswag.bottom_navigation_viewcontroller

import ru.touchin.roboswag.bottom_navigation_fragment.BaseBottomNavigationActivity
import ru.touchin.roboswag.navigation_viewcontroller.viewcontrollers.ViewControllerNavigation

abstract class BottomNavigationActivity :
        BaseBottomNavigationActivity<ViewControllerNavigation<BottomNavigationActivity>, BottomNavigationFragment, NavigationContainerFragment>() {

    final override val navigation by lazy {
        ViewControllerNavigation<BottomNavigationActivity>(
                this,
                supportFragmentManager,
                fragmentContainerViewId,
                transition
        )
    }

}
