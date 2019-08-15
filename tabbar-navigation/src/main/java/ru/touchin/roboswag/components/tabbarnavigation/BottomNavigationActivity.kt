package ru.touchin.roboswag.components.tabbarnavigation

import androidx.fragment.app.FragmentManager
import ru.touchin.roboswag.components.navigation.activities.BaseNavigationActivity

/**
 * Created by Daniil Borisovskii on 15/08/2019.
 * Activity to manage tab container navigation.
 */
abstract class BottomNavigationActivity : BaseNavigationActivity() {

    val innerNavigation by lazy {
        getNavigationContainer(supportFragmentManager)?.navigation ?: navigation
    }

    private fun getNavigationContainer(fragmentManager: FragmentManager?): NavigationContainerFragment? =
            fragmentManager
                    ?.primaryNavigationFragment
                    ?.let { navigationFragment ->
                        navigationFragment as? NavigationContainerFragment
                                ?: getNavigationContainer(navigationFragment.childFragmentManager)
                    }

}
