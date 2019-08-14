package ru.touchin.tabbarnavigation

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.util.forEach
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.touchin.roboswag.components.navigation.fragments.ViewControllerFragment
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewController
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

class BottomNavigationController(
        private val context: Context,
        private val fragmentManager: FragmentManager,
        private val viewControllers: SparseArray<Pair<Class<out ViewController<*, *>>, Parcelable>>,
        private val contentContainerViewId: Int,
        private val wrapWithNavigationContainer: Boolean = false,
        @IdRes private val topLevelViewControllerId: Int = 0, // If it zero back press with empty fragment back stack would close the app
        private val onReselectListener: (() -> Unit)? = null
) {

    private lateinit var callback: FragmentManager.FragmentLifecycleCallbacks

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
        fragmentManager.registerFragmentLifecycleCallbacks(callback, false)

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

    fun detach() {
        if (::callback.isInitialized) {
            fragmentManager.unregisterFragmentLifecycleCallbacks(callback)
        }
    }

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

        //TODO: figure out do we need to remove exists fragment before instantiate him one more time
        if (fragment != null) {
            transaction.attach(fragment)
        } else {
            fragment = if (wrapWithNavigationContainer) {
                Fragment.instantiate(
                        context,
                        NavigationContainerFragment::class.java.name,
                        NavigationContainerFragment.args(viewControllerClass, viewControllerState)
                )
            } else {
                Fragment.instantiate(
                        context,
                        ViewControllerFragment::class.java.name,
                        ViewControllerFragment.args(viewControllerClass, viewControllerState)
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

    // If current fragment top and it's not the top level view controller open to top level view controller
    fun onBackPressed() =
            if (fragmentManager.primaryNavigationFragment?.childFragmentManager?.backStackEntryCount == 0
                    && topLevelViewControllerId != 0
                    && currentViewControllerId != topLevelViewControllerId) {
                navigateTo(topLevelViewControllerId)
                true
            } else {
                false
            }

    private fun isViewControllerFragment(fragment: Fragment?, viewControllerClass: Class<out ViewController<*, *>>) =
            if (wrapWithNavigationContainer) {
                (fragment as NavigationContainerFragment).getViewControllerClass()
            } else {
                (fragment as ViewControllerFragment<*, *>).viewControllerClass
            } === viewControllerClass
}
