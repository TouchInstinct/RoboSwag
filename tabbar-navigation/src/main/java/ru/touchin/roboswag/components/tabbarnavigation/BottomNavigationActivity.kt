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

    private fun getNavigationContainer(fragmentManager: FragmentManager?): NavigationContainerFragment? =
            fragmentManager
                    ?.primaryNavigationFragment
                    ?.let { navigationFragment ->
                        navigationFragment as? NavigationContainerFragment
                                ?: getNavigationContainer(navigationFragment.childFragmentManager)
                    }

    fun navigateTo(@IdRes navigationTabId: Int, state: Parcelable? = null) =
            (supportFragmentManager.primaryNavigationFragment as? BottomNavigationFragment)?.navigateTo(navigationTabId, state)

}
