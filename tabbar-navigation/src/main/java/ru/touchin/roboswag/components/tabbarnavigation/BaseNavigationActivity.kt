package ru.touchin.roboswag.components.tabbarnavigation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewControllerNavigation
import ru.touchin.templates.TouchinActivity

abstract class BaseNavigationActivity : TouchinActivity() {
    val screenNavigation by lazy {
        ViewControllerNavigation<BaseNavigationActivity>(
                this,
                supportFragmentManager,
                getFragmentContainerViewId(),
                getTransition()
        )
    }

    protected abstract fun getFragmentContainerViewId(): Int

    protected open fun getTransition() = FragmentTransaction.TRANSIT_NONE

    fun getInnerNavigation() = getNavigationContainer(supportFragmentManager)?.navigation ?: screenNavigation

    private fun getNavigationContainer(fragmentManager: FragmentManager?): NavigationContainerFragment? =
            fragmentManager
                    ?.primaryNavigationFragment
                    ?.let { navigationFragment ->
                        navigationFragment as? NavigationContainerFragment
                                ?: getNavigationContainer(navigationFragment.childFragmentManager)
                    }
}
