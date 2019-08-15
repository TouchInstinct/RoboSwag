package ru.touchin.roboswag.components.tabbarnavigation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewControllerNavigation
import ru.touchin.templates.TouchinActivity

abstract class BaseNavigationActivity : TouchinActivity() {
    val navigation by lazy {
        ViewControllerNavigation<BaseNavigationActivity>(
                this,
                supportFragmentManager,
                fragmentContainerViewId,
                transition
        )
    }

    val innerNavigation by lazy {
        getNavigationContainer(supportFragmentManager)?.navigation ?: navigation
    }

    protected abstract val fragmentContainerViewId: Int

    protected open val transition = FragmentTransaction.TRANSIT_NONE

    private fun getNavigationContainer(fragmentManager: FragmentManager?): NavigationContainerFragment? =
            fragmentManager
                    ?.primaryNavigationFragment
                    ?.let { navigationFragment ->
                        navigationFragment as? NavigationContainerFragment
                                ?: getNavigationContainer(navigationFragment.childFragmentManager)
                    }
}
