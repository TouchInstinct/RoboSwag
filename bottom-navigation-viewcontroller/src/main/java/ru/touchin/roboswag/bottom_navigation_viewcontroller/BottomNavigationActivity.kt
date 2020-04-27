package ru.touchin.roboswag.components.tabbarnavigation

import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import ru.touchin.roboswag.navigation.activities.NavigationActivity
import ru.touchin.roboswag.navigation.viewcontrollers.ViewControllerNavigation
import ru.touchin.roboswag.bottom_navigation_fragment.BottomNavigationActivity as FragmentBottomNavigationActivity

abstract class BottomNavigationActivity : FragmentBottomNavigationActivity() {

    final override val innerNavigation: ViewControllerNavigation<BottomNavigationActivity>
        get() = getNavigationContainer(supportFragmentManager)?.navigation
                ?: navigation as ViewControllerNavigation<BottomNavigationActivity>

}
