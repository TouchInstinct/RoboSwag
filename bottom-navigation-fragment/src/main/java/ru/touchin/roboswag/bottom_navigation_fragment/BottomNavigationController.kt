package ru.touchin.roboswag.bottom_navigation_fragment

import android.content.Context
import android.util.SparseArray
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager
import ru.touchin.roboswag.bottom_navigation_base.BaseBottomNavigationController

class BottomNavigationController(
        context: Context,
        fragments: SparseArray<NavigationTab>,
        fragmentManager: FragmentManager,
        wrapWithNavigationContainer: Boolean = false,
        @LayoutRes private val contentContainerLayoutId: Int,
        @IdRes private val contentContainerViewId: Int,
        @IdRes private val defaultTabId: Int = 0, // If it zero back press with empty fragment back stack would close the app
        onReselectListener: (() -> Unit)? = null
) : BaseBottomNavigationController<NavigationTab>(
        tabs = fragments,
        context = context,
        fragmentManager = fragmentManager,
        defaultTabId = defaultTabId,
        onReselectListener = onReselectListener,
        contentContainerViewId = contentContainerViewId,
        contentContainerLayoutId = contentContainerLayoutId,
        wrapWithNavigationContainer = wrapWithNavigationContainer
) {

    override fun getNavigationContainerClass() = NavigationContainerFragment::class.java

}
