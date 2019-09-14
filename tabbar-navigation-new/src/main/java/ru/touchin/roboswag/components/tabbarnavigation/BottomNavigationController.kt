package ru.touchin.roboswag.components.tabbarnavigation

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.util.forEach
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.touchin.roboswag.components.navigation.fragments.ViewControllerFragment
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewController
import ru.touchin.roboswag.components.navigation_new.fragments.BaseFragment
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

class BottomNavigationController(
        private val context: Context,
        private val fragmentManager: FragmentManager,
        private val viewControllers: SparseArray<Pair<Class<out BaseFragment<*, *>>, Parcelable>>,
        @IdRes private val contentContainerViewId: Int,
        @LayoutRes private val contentContainerLayoutId: Int,
        private val wrapWithNavigationContainer: Boolean = false,
        @IdRes private val topLevelViewControllerId: Int = 0, // If it zero back press with empty fragment back stack would close the app
        private val onReselectListener: (() -> Unit)? = null
) {

    private var callback: FragmentManager.FragmentLifecycleCallbacks? = null

    private var currentViewControllerId = -1

    fun attach(navigationTabsContainer: ViewGroup) {
        detach()

        //This is provides to set pressed tab status to isActivated providing an opportunity to specify custom style
        callback = object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(fragmentManager: FragmentManager, fragment: Fragment, view: View, savedInstanceState: Bundle?) {
                viewControllers.forEach { itemId, (viewControllerClass, _) ->
                    if (isViewControllerFragment(fragment, viewControllerClass)) {
                        navigationTabsContainer.children.forEach { itemView -> itemView.isActivated = itemView.id == itemId }
                    }
                }
            }
        }
        fragmentManager.registerFragmentLifecycleCallbacks(callback!!, false)

        navigationTabsContainer.children.forEach { itemView ->
            viewControllers[itemView.id]?.let { (viewControllerClass, _) ->
                itemView.setOnClickListener {
                    if (!isViewControllerFragment(fragmentManager.primaryNavigationFragment, viewControllerClass)) {
                        navigateTo(itemView.id)
                    } else {
                        onReselectListener?.invoke()
                    }
                }
            }
        }
    }

    fun detach() = callback?.let(fragmentManager::unregisterFragmentLifecycleCallbacks)

    fun navigateTo(@IdRes itemId: Int, state: Parcelable? = null) {
        // Find view controller class that needs to open
        val (viewControllerClass, defaultViewControllerState) = viewControllers[itemId] ?: return
        if (state != null && state::class != defaultViewControllerState::class) {
            throw ShouldNotHappenException(
                    "Incorrect state type for navigation tab root ViewController. Should be ${defaultViewControllerState::class}"
            )
        }
        val viewControllerState = state ?: defaultViewControllerState
        val transaction = fragmentManager.beginTransaction()
        // Detach current primary fragment
        fragmentManager.primaryNavigationFragment?.let(transaction::detach)
        val viewControllerName = viewControllerClass.canonicalName
        var fragment = fragmentManager.findFragmentByTag(viewControllerName)

        if (state == null && fragment != null) {
            transaction.attach(fragment)
        } else {
            // If fragment already exist remove it first
            if (fragment != null) transaction.remove(fragment)

            fragment = if (wrapWithNavigationContainer) {
                Fragment.instantiate(
                        context,
                        viewControllerClass.name,
                        NavigationContainerFragment.args(viewControllerClass, viewControllerState, contentContainerViewId, contentContainerLayoutId)
                )
            } else {
                Fragment.instantiate(
                        context,
                        viewControllerClass.name,
                        BaseFragment.args(viewControllerState)
                )
            }
            transaction.add(contentContainerViewId, fragment, viewControllerName)
        }

        transaction
                .setPrimaryNavigationFragment(fragment)
                .setReorderingAllowed(true)
                .commit()

        currentViewControllerId = itemId
    }

    // When you are in any tab instead of main you firstly navigate to main tab before exit application
    fun onBackPressed() =
            if (fragmentManager.primaryNavigationFragment?.childFragmentManager?.backStackEntryCount == 0
                    && topLevelViewControllerId != 0
                    && currentViewControllerId != topLevelViewControllerId) {
                navigateTo(topLevelViewControllerId)
                true
            } else {
                false
            }

    private fun isViewControllerFragment(fragment: Fragment?, viewControllerClass: Class<out BaseFragment<*, *>>) =
            if (wrapWithNavigationContainer) {
                (fragment as NavigationContainerFragment).getViewControllerClass()
            } else {
                (fragment as ViewControllerFragment<*, *>).viewControllerClass
            } === viewControllerClass
}
