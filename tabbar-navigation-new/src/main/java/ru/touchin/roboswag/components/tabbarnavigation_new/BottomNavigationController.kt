package ru.touchin.roboswag.components.tabbarnavigation_new

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
import ru.touchin.roboswag.components.navigation_new.fragments.BaseFragment
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

class BottomNavigationController(
        private val context: Context,
        private val fragmentManager: FragmentManager,
        private val fragments: SparseArray<Pair<Class<out BaseFragment<*, *>>, Parcelable>>,
        @IdRes private val contentContainerViewId: Int,
        @LayoutRes private val contentContainerLayoutId: Int,
        private val wrapWithNavigationContainer: Boolean = false,
        @IdRes private val topLevelFragmentId: Int = 0, // If it zero back press with empty fragment back stack would close the app
        private val onReselectListener: (() -> Unit)? = null
) {

    private var callback: FragmentManager.FragmentLifecycleCallbacks? = null

    private var currentFragmentId = -1

    fun attach(navigationTabsContainer: ViewGroup) {
        detach()

        //This is provides to set pressed tab status to isActivated providing an opportunity to specify custom style
        callback = object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(fragmentManager: FragmentManager, fragment: Fragment, view: View, savedInstanceState: Bundle?) {
                fragments.forEach { itemId, (fragmentClass, _) ->
                    if (isFragment(fragment, fragmentClass)) {
                        navigationTabsContainer.children.forEach { itemView -> itemView.isActivated = itemView.id == itemId }
                    }
                }
            }
        }
        fragmentManager.registerFragmentLifecycleCallbacks(callback!!, false)

        navigationTabsContainer.children.forEach { itemView ->
            fragments[itemView.id]?.let { (fragmentClass, _) ->
                itemView.setOnClickListener {
                    if (!isFragment(fragmentManager.primaryNavigationFragment, fragmentClass)) {
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
        // Find fragment class that needs to open
        val (fragmentClass, defaultFragmentState) = fragments[itemId] ?: return
        if (state != null && state::class != defaultFragmentState::class) {
            throw ShouldNotHappenException(
                    "Incorrect state type for navigation tab root Fragment. Should be ${defaultFragmentState::class}"
            )
        }
        val fragmentState = state ?: defaultFragmentState
        val transaction = fragmentManager.beginTransaction()
        // Detach current primary fragment
        fragmentManager.primaryNavigationFragment?.let(transaction::detach)
        val fragmentName = fragmentClass.canonicalName
        var fragment = fragmentManager.findFragmentByTag(fragmentName)

        if (state == null && fragment != null) {
            transaction.attach(fragment)
        } else {
            // If fragment already exist remove it first
            if (fragment != null) transaction.remove(fragment)

            fragment = if (wrapWithNavigationContainer) {
                Fragment.instantiate(
                        context,
                        NavigationContainerFragment::class.java.name,
                        NavigationContainerFragment.args(fragmentClass, fragmentState, contentContainerViewId, contentContainerLayoutId)
                )
            } else {
                Fragment.instantiate(
                        context,
                        fragmentClass.name,
                        BaseFragment.args(fragmentState)
                )
            }
            transaction.add(contentContainerViewId, fragment, fragmentName)
        }

        transaction
                .setPrimaryNavigationFragment(fragment)
                .setReorderingAllowed(true)
                .commit()

        currentFragmentId = itemId
    }

    // When you are in any tab instead of main you firstly navigate to main tab before exit application
    fun onBackPressed() =
            if (fragmentManager.primaryNavigationFragment?.childFragmentManager?.backStackEntryCount == 0
                    && topLevelFragmentId != 0
                    && currentFragmentId != topLevelFragmentId) {
                navigateTo(topLevelFragmentId)
                true
            } else {
                false
            }

    private fun isFragment(fragment: Fragment?, fragmentClass: Class<out BaseFragment<*, *>>) =
            if (wrapWithNavigationContainer) {
                (fragment as NavigationContainerFragment).getFragmentClass()
            } else {
                (fragment as BaseFragment<*, *>).javaClass
            } === fragmentClass
}
