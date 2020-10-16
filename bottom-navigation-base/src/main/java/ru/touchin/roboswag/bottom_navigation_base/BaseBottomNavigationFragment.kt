package ru.touchin.roboswag.bottom_navigation_base

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import ru.touchin.roboswag.navigation_base.activities.BaseActivity
import ru.touchin.roboswag.navigation_base.activities.OnBackPressedListener

abstract class BaseBottomNavigationFragment<TNavigationType: BaseNavigationTab> : Fragment() {

    protected abstract val rootLayoutId: Int

    protected abstract val navigationContainerViewId: Int

    protected abstract val contentContainerViewId: Int

    protected abstract val contentContainerLayoutId: Int

    protected abstract val defaultTabId: Int

    protected abstract val wrapWithNavigationContainer: Boolean

    protected abstract val tabs: SparseArray<TNavigationType>

    protected open val backPressedListener = OnBackPressedListener { bottomNavigationController.onBackPressed() }

    protected open val reselectListener: (() -> Unit) = { getNavigationActivity().innerNavigation.up(inclusive = true) }

    private lateinit var bottomNavigationController: BaseBottomNavigationController<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomNavigationController = createNavigationController()

        if (savedInstanceState == null) {
            bottomNavigationController.navigateTo(defaultTabId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(rootLayoutId, container, false)

        bottomNavigationController.attach(fragmentView.findViewById(navigationContainerViewId))

        (activity as BaseActivity).addOnBackPressedListener(backPressedListener)

        return fragmentView
    }

    override fun onDestroyView() {
        (activity as BaseActivity).removeOnBackPressedListener(backPressedListener)

        bottomNavigationController.detach()

        super.onDestroyView()
    }

    fun navigateTo(@IdRes navigationTabId: Int, state: Parcelable? = null) {
        bottomNavigationController.navigateTo(navigationTabId, state)
    }

    protected abstract fun createNavigationController(): BaseBottomNavigationController<*>

    protected fun getNavigationActivity() = requireActivity() as BaseBottomNavigationActivity<*, *, *>

}
