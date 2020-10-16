package ru.touchin.roboswag.bottom_navigation_base

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
import ru.touchin.roboswag.core.utils.ShouldNotHappenException
import ru.touchin.roboswag.navigation_base.fragments.StatefulFragment

abstract class BaseBottomNavigationController<TNavigationTab : BaseNavigationTab>(
        private val tabs: SparseArray<TNavigationTab>,
        private val context: Context,
        private val fragmentManager: FragmentManager,
        @LayoutRes private val contentContainerLayoutId: Int,
        @IdRes private val contentContainerViewId: Int,
        @IdRes private val defaultTabId: Int = 0, // If it zero back press with empty fragment back stack would close the app
        private val wrapWithNavigationContainer: Boolean = false,
        private val onReselectListener: (() -> Unit)? = null
) {

    private var callback: FragmentManager.FragmentLifecycleCallbacks? = null

    private var tabsContainer: ViewGroup? = null

    private var selectedTabId: Int? = null

    fun attach(tabsContainer: ViewGroup) {
        detach()

        this.tabsContainer = tabsContainer

        initializeCallback()

        tabsContainer.children.forEach { itemView ->
            tabs[itemView.id]?.let { tab ->
                itemView.setOnClickListener { buttonView ->
                    if (isTabClass(tab, fragmentManager.primaryNavigationFragment)) {
                        onTabReselected()
                    } else {
                        navigateTo(buttonView.id)
                    }
                }
            }
        }
    }

    fun detach() {
        callback?.let(fragmentManager::unregisterFragmentLifecycleCallbacks)

        callback = null
        tabsContainer = null
    }

    fun navigateTo(@IdRes itemId: Int, state: Parcelable? = null) {
        // Find fragment class that needs to open
        val tabClass = tabs[itemId]?.cls ?: return
        val defaultFragmentState = tabs[itemId]?.state ?: return

        if (state != null && state::class != defaultFragmentState::class) {
            throw ShouldNotHappenException(
                    "Incorrect state type for navigation tab root Fragment. Should be ${defaultFragmentState::class}"
            )
        }

        val transaction = fragmentManager.beginTransaction()

        // Detach current primary fragment
        fragmentManager.primaryNavigationFragment?.let(transaction::detach)

        val fragmentName = tabClass.canonicalName
        var fragment = fragmentManager.findFragmentByTag(fragmentName)

        if (state == null && fragment != null) {
            transaction.attach(fragment)
        } else {
            // If fragment already exist remove it first
            if (fragment != null) transaction.remove(fragment)

            fragment = instantiateFragment(tabClass, state ?: defaultFragmentState)

            transaction.add(contentContainerViewId, fragment, fragmentName)
        }

        transaction
                .setPrimaryNavigationFragment(fragment)
                .setReorderingAllowed(true)
                .commit()

        selectedTabId = itemId
    }

    // When you are in any tab instead of main you firstly navigate to main tab before exit application
    fun onBackPressed() =
            if (fragmentManager.primaryNavigationFragment?.childFragmentManager?.backStackEntryCount == 0
                    && defaultTabId != 0
                    && selectedTabId != defaultTabId) {

                navigateTo(defaultTabId)

                true
            } else {
                false
            }

    protected open fun getNavigationContainerClass(): Class<out BaseNavigationContainerFragment<*, *>> = BaseNavigationContainerFragment::class.java

    protected open fun onTabReselected() {
        onReselectListener?.invoke()
    }

    protected open fun isTabClass(tab: TNavigationTab, fragment: Fragment?) = if (wrapWithNavigationContainer) {
        (fragment as BaseNavigationContainerFragment<*, *>).getContainedClass()
    } else {
        fragment?.javaClass
    } == tab.cls

    protected open fun instantiateFragment(clazz: Class<*>, state: Parcelable): Fragment =
            if (wrapWithNavigationContainer) {
                Fragment.instantiate(
                        context,
                        getNavigationContainerClass().name,
                        BaseNavigationContainerFragment.args(clazz, state, contentContainerViewId, contentContainerLayoutId)
                )
            } else {
                Fragment.instantiate(
                        context,
                        clazz.name,
                        StatefulFragment.args(state)
                )
            }

    private fun initializeCallback() {
        callback = TabFragmentChangedCallback()

        fragmentManager.registerFragmentLifecycleCallbacks(callback!!, false)
    }

    private inner class TabFragmentChangedCallback : FragmentManager.FragmentLifecycleCallbacks() {

        // Set selected tab active, disabling all others. Used for styling
        override fun onFragmentViewCreated(
                fragmentManager: FragmentManager,
                fragment: Fragment,
                view: View,
                savedInstanceState: Bundle?
        ) {
            tabs.forEach { itemId, tab ->
                if (isTabClass(tab, fragment)) {
                    tabsContainer!!.children.forEach { itemView -> itemView.isActivated = itemView.id == itemId }
                }
            }
        }

    }

}
