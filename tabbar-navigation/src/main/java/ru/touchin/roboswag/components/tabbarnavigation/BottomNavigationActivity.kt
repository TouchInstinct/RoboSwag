package ru.touchin.roboswag.components.tabbarnavigation

import androidx.fragment.app.FragmentManager
import ru.touchin.roboswag.components.navigation.activities.NavigationActivity
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewControllerNavigation

/**
 * Created by Daniil Borisovskii on 15/08/2019.
 * Activity to manage tab container navigation.
 */
abstract class BottomNavigationActivity : NavigationActivity() {

    val innerNavigation by lazy {
        getNavigationContainer(supportFragmentManager)?.navigation ?: navigation as ViewControllerNavigation<BottomNavigationActivity>
    }

    private fun getNavigationContainer(fragmentManager: FragmentManager?): NavigationContainerFragment? =
            fragmentManager
                    ?.primaryNavigationFragment
                    ?.let { navigationFragment ->
                        navigationFragment as? NavigationContainerFragment
                                ?: getNavigationContainer(navigationFragment.childFragmentManager)
                    }

}
