package ru.touchin.roboswag.components.navigation_base.activities

import androidx.fragment.app.FragmentTransaction
import ru.touchin.roboswag.components.navigation_base.FragmentNavigation

/**
 * Created by Daniil Borisovskii on 15/08/2019.
 * Base activity with nested navigation.
 */
abstract class NavigationActivity : BaseActivity() {

    protected abstract val fragmentContainerViewId: Int

    protected open val transition = FragmentTransaction.TRANSIT_NONE

    open val navigation by lazy {
        FragmentNavigation(
                this,
                supportFragmentManager,
                fragmentContainerViewId,
                transition
        )
    }

}
