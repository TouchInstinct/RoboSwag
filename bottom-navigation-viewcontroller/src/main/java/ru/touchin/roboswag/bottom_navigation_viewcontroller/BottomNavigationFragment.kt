package ru.touchin.roboswag.bottom_navigation_viewcontroller

import ru.touchin.roboswag.bottom_navigation_base.BaseBottomNavigationFragment

abstract class BottomNavigationFragment : BaseBottomNavigationFragment<NavigationTab>() {

    override fun createNavigationController() = BottomNavigationController(
            context = requireContext(),
            fragmentManager = childFragmentManager,
            viewControllers = tabs,
            defaultTabId = defaultTabId,
            contentContainerViewId = contentContainerViewId,
            contentContainerLayoutId = contentContainerLayoutId,
            wrapWithNavigationContainer = wrapWithNavigationContainer,
            onReselectListener = reselectListener
    )

}

