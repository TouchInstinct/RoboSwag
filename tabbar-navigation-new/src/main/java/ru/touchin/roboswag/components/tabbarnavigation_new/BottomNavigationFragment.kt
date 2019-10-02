package ru.touchin.roboswag.components.tabbarnavigation_new

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import ru.touchin.roboswag.components.navigation.activities.OnBackPressedListener
import ru.touchin.roboswag.components.navigation_new.fragments.BaseFragment

abstract class BottomNavigationFragment : Fragment() {

    private lateinit var bottomNavigationController: BottomNavigationController

    private val backPressedListener = OnBackPressedListener { bottomNavigationController.onBackPressed() }

    protected abstract val rootLayoutId: Int

    protected abstract val navigationContainerViewId: Int

    protected abstract val contentContainerViewId: Int

    protected abstract val contentContainerLayoutId: Int

    protected abstract val topLevelFragmentId: Int

    protected abstract val wrapWithNavigationContainer: Boolean

    protected abstract val navigationFragments: SparseArray<Pair<Class<out BaseFragment<*, *>>, Parcelable>>

    protected open val reselectListener: (() -> Unit) = { getNavigationActivity().innerNavigation.up(inclusive = true) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigationController = BottomNavigationController(
                context = requireContext(),
                fragmentManager = childFragmentManager,
                fragments = navigationFragments,
                contentContainerViewId = contentContainerViewId,
                contentContainerLayoutId = contentContainerLayoutId,
                topLevelFragmentId = topLevelFragmentId,
                wrapWithNavigationContainer = wrapWithNavigationContainer,
                onReselectListener = reselectListener
        )
        if (savedInstanceState == null) {
            bottomNavigationController.navigateTo(topLevelFragmentId)
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

}
