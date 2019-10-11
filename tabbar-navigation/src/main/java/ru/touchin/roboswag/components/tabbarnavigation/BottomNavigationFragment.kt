package ru.touchin.roboswag.components.tabbarnavigation

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import ru.touchin.roboswag.components.navigation.activities.OnBackPressedListener
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewController

abstract class BottomNavigationFragment : Fragment() {

    private lateinit var bottomNavigationController: BottomNavigationController

    private val backPressedListener = OnBackPressedListener { bottomNavigationController.onBackPressed() }

    protected abstract val rootLayoutId: Int

    protected abstract val navigationContainerViewId: Int

    protected abstract val contentContainerViewId: Int

    protected abstract val contentContainerLayoutId: Int

    protected abstract val topLevelViewControllerId: Int

    protected abstract val wrapWithNavigationContainer: Boolean

    protected abstract val navigationViewControllers: SparseArray<TabData>

    protected open val reselectListener: (() -> Unit) = { getNavigationActivity().innerNavigation.up(inclusive = true) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigationController = BottomNavigationController(
                context = requireContext(),
                fragmentManager = childFragmentManager,
                viewControllers = navigationViewControllers,
                contentContainerViewId = contentContainerViewId,
                contentContainerLayoutId = contentContainerLayoutId,
                topLevelViewControllerId = topLevelViewControllerId,
                wrapWithNavigationContainer = wrapWithNavigationContainer,
                onReselectListener = reselectListener
        )
        if (savedInstanceState == null) {
            bottomNavigationController.navigateTo(topLevelViewControllerId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(rootLayoutId, container, false)

        bottomNavigationController.attach(fragmentView.findViewById(navigationContainerViewId))

        (activity as BottomNavigationActivity).addOnBackPressedListener(backPressedListener)

        return fragmentView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as BottomNavigationActivity).removeOnBackPressedListener(backPressedListener)
        bottomNavigationController.detach()
    }

    fun navigateTo(@IdRes navigationTabId: Int, state: Parcelable? = null) {
        bottomNavigationController.navigateTo(navigationTabId, state)
    }

    private fun getNavigationActivity() = requireActivity() as BottomNavigationActivity

    class TabData(
            val viewControllerClass: Class<out ViewController<*, *>>,
            viewControllerState: Parcelable,
            /**
             * It can be useful in some cases when it is necessary to create ViewController
             * with initial state every time when tab opens.
             */
            val saveStateOnSwitching: Boolean = true
    ) {

        /**
         * It is value as class body field with lazy delegate instead of value as constructor parameter
         * to specify custom getter of this field which returns new instance of Parcelable every time it be invoked.
         * This is necessary to avoid modifying this value if it would be a value as constructor parameter and
         * every getting of this value would return the same instance.
        */
        val viewControllerState: Parcelable by lazy {
            viewControllerState
        }

        operator fun component1() = viewControllerClass

        operator fun component2() = viewControllerState

        operator fun component3() = saveStateOnSwitching

    }

}
