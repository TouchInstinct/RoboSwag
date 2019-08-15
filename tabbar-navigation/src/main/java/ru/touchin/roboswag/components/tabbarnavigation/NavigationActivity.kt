package ru.touchin.roboswag.components.tabbarnavigation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewControllerNavigation
import ru.touchin.templates.TouchinActivity

abstract class NavigationActivity : TouchinActivity() {
    val screenNavigation by lazy {
        ViewControllerNavigation<NavigationActivity>(
                this,
                supportFragmentManager,
                getContainerViewId(),
                getTransition()
        )
    }

    abstract fun getContainerViewId(): Int

    open fun getTransition() = FragmentTransaction.TRANSIT_NONE

    fun getInnerNavigation() = getNavigationContainer(supportFragmentManager)?.navigation ?: screenNavigation

    private fun getNavigationContainer(fragmentManager: FragmentManager?): NavigationContainerFragment? =
            fragmentManager
                    ?.primaryNavigationFragment
                    ?.let { navigationFragment ->
                        navigationFragment as? NavigationContainerFragment
                                ?: getNavigationContainer(navigationFragment.childFragmentManager)
                    }
}
