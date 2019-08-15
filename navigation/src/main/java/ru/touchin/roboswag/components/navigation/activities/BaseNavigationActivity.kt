package ru.touchin.roboswag.components.navigation.activities

import androidx.fragment.app.FragmentTransaction
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewControllerNavigation

/**
 * Created by Daniil Borisovskii on 15/08/2019.
 * Base activity with nested navigation.
 */
abstract class BaseNavigationActivity : BaseActivity() {

    protected abstract val fragmentContainerViewId: Int

    protected open val transition = FragmentTransaction.TRANSIT_NONE

    val navigation by lazy {
        ViewControllerNavigation<BaseNavigationActivity>(
                this,
                supportFragmentManager,
                fragmentContainerViewId,
                transition
        )
    }

}
