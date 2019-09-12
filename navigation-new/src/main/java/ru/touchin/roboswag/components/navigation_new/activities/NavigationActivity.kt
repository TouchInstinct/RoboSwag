package ru.touchin.roboswag.components.navigation_new.activities

import androidx.fragment.app.FragmentTransaction
import ru.touchin.roboswag.components.navigation_new.viewcontrollers.ViewControllerNavigation

/**
 * Created by Daniil Borisovskii on 15/08/2019.
 * Base activity with nested navigation.
 */
abstract class NavigationActivity : BaseActivity() {

    protected abstract val fragmentContainerViewId: Int

    protected open val transition = FragmentTransaction.TRANSIT_NONE

    open val navigation by lazy {
        ViewControllerNavigation<NavigationActivity>(
                this,
                supportFragmentManager,
                fragmentContainerViewId,
                transition
        )
    }

}
