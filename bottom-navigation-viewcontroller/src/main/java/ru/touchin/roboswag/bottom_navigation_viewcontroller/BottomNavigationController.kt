package ru.touchin.roboswag.bottom_navigation_viewcontroller

import android.content.Context
import android.util.SparseArray
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.touchin.roboswag.bottom_navigation_base.BaseBottomNavigationController
import ru.touchin.roboswag.navigation_viewcontroller.fragments.ViewControllerFragment

class BottomNavigationController(
        context: Context,
        fragmentManager: FragmentManager,
        viewControllers: SparseArray<NavigationTab>,
        private val wrapWithNavigationContainer: Boolean = false,
        @IdRes private val defaultTabId: Int = 0, // If it zero back press with empty fragment back stack would close the app
        @IdRes private val contentContainerViewId: Int,
        @LayoutRes private val contentContainerLayoutId: Int,
        private val onReselectListener: (() -> Unit)? = null
) : BaseBottomNavigationController<NavigationTab>(
        context = context,
        fragmentManager = fragmentManager,
        tabs = viewControllers,
        defaultTabId = defaultTabId,
        contentContainerViewId = contentContainerViewId,
        contentContainerLayoutId = contentContainerLayoutId,
        wrapWithNavigationContainer = wrapWithNavigationContainer
) {

    override fun onTabReselected() {
        onReselectListener?.invoke()
    }

    override fun getNavigationContainerClass() = NavigationContainerFragment::class.java

    override fun isTabClass(tab: NavigationTab, fragment: Fragment?): Boolean =
            if (wrapWithNavigationContainer) {
                super.isTabClass(tab, fragment)
            } else {
                (fragment as ViewControllerFragment<*, *>).viewControllerClass === tab.cls
            }

}

