package ru.touchin.roboswag.components.tabbarnavigation

import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import ru.touchin.roboswag.components.navigation.activities.NavigationActivity
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewControllerNavigation

/**
 * Created by Daniil Borisovskii on 15/08/2019.
 * Activity to manage tab container navigation.
 */
abstract class BottomNavigationActivity : NavigationActivity() {

    val innerNavigation: ViewControllerNavigation<BottomNavigationActivity>
        get() = getNavigationContainer(supportFragmentManager)?.navigation ?: navigation as ViewControllerNavigation<BottomNavigationActivity>

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

            (primaryNavigationFragment as? BottomNavigationFragment)?.navigateTo(navigationTabId, state)
        }
    }

    private fun getNavigationContainer(fragmentManager: FragmentManager?): NavigationContainerFragment? =
            fragmentManager
                    ?.primaryNavigationFragment
                    ?.let { navigationFragment ->
                        navigationFragment as? NavigationContainerFragment
                                ?: getNavigationContainer(navigationFragment.childFragmentManager)
                    }

}
