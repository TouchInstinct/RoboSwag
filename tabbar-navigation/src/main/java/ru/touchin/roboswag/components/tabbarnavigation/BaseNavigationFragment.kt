package ru.touchin.roboswag.components.tabbarnavigation

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.touchin.roboswag.components.navigation.activities.OnBackPressedListener
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewController

abstract class BaseNavigationFragment : Fragment() {

    private lateinit var bottomNavigationController: BottomNavigationController

    private val backPressedListener = OnBackPressedListener { bottomNavigationController.onBackPressed() }

    protected abstract fun getRootLayoutId(): Int

    protected abstract fun getNavigationContainerViewId(): Int

    protected abstract fun getContentContainerViewId(): Int

    protected abstract fun getContentContainerLayoutId(): Int

    protected abstract fun getTopLevelViewControllerId(): Int

    protected abstract fun wrapWithNavigationContainer(): Boolean

    protected abstract fun getNavigationViewControllers(): SparseArray<Pair<Class<out ViewController<*, *>>, Parcelable>>

    protected open fun getReselectListener(): (() -> Unit) = { getNavigationActivity().getInnerNavigation().up() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigationController = BottomNavigationController(
                context = requireContext(),
                fragmentManager = childFragmentManager,
                viewControllers = getNavigationViewControllers(),
                contentContainerViewId = getContentContainerViewId(),
                contentContainerLayoutId = getContentContainerLayoutId(),
                topLevelViewControllerId = getTopLevelViewControllerId(),
                wrapWithNavigationContainer = wrapWithNavigationContainer(),
                onReselectListener = getReselectListener()
        )
        if (savedInstanceState == null) {
            bottomNavigationController.navigateTo(getTopLevelViewControllerId())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(getRootLayoutId(), container, false)

        bottomNavigationController.attach(fragmentView.findViewById(getNavigationContainerViewId()))

        (activity as BaseNavigationActivity).addOnBackPressedListener(backPressedListener)

        return fragmentView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as BaseNavigationActivity).removeOnBackPressedListener(backPressedListener)
        bottomNavigationController.detach()
    }

    private fun getNavigationActivity() = requireActivity() as BaseNavigationActivity

}
