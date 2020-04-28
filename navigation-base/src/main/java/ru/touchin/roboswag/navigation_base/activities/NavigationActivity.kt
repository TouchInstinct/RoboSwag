package ru.touchin.roboswag.navigation_base.activities

import androidx.fragment.app.FragmentTransaction
import ru.touchin.roboswag.navigation_base.FragmentNavigation

abstract class NavigationActivity<TNavigation : FragmentNavigation> : BaseActivity() {

    protected abstract val fragmentContainerViewId: Int

    protected open val transition = FragmentTransaction.TRANSIT_NONE

    abstract val navigation: TNavigation

}
