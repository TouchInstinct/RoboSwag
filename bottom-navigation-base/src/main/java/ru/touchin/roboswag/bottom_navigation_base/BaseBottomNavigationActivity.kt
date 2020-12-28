package ru.touchin.roboswag.bottom_navigation_base

import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import ru.touchin.roboswag.navigation_base.FragmentNavigation
import ru.touchin.roboswag.navigation_base.activities.NavigationActivity

abstract class BaseBottomNavigationActivity<TNavigation, TNavigationFragment, TNavigationContainer> : NavigationActivity<TNavigation>()
        where TNavigation : FragmentNavigation,
              TNavigationFragment : BaseBottomNavigationFragment<*>,
              TNavigationContainer : BaseNavigationContainerFragment<*, TNavigation>
{

    val innerNavigation: TNavigation
    get() = getNavigationContainer(supportFragmentManager)?.navigation ?: navigation

    /**
     * Navigates to the given navigation tab.
     * Can be called from any node of navigation graph so all back stack will be cleared.
     *
     * @param navigationTabId Id of navigation tab.
     * @param state State of the given tab. If not null tab's fragment will be recreated, otherwise only in case it has not been created before.
     */
    fun navigateTo(@IdRes navigationTabId: Int, state: Parcelable? = null) {
        supportFragmentManager.run {
            // Clear all navigation stack unto the main bottom navigation (tagged as top)
            popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            (primaryNavigationFragment as? TNavigationFragment)?.navigateTo(navigationTabId, state)
        }
    }

    protected fun getNavigationContainer(fragmentManager: FragmentManager?): TNavigationContainer? =
            fragmentManager
                    ?.primaryNavigationFragment
                    ?.let { navigationFragment ->
                        navigationFragment as? TNavigationContainer
                                ?: getNavigationContainer(navigationFragment.childFragmentManager)
                    }

}
