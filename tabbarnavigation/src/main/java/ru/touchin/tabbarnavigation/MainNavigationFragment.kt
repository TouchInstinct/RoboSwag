package ru.touchin.tabbarnavigation

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.touchin.roboswag.components.navigation.activities.OnBackPressedListener
import ru.touchin.roboswag.components.navigation.viewcontrollers.ViewController

abstract class MainNavigationFragment : Fragment() {

    private lateinit var bottomNavigationController: BottomNavigationController

    private val backPressedListener = OnBackPressedListener { bottomNavigationController.onBackPressed() }

    abstract fun getRootViewId(): Int

    abstract fun getNavigationContainerId(): Int

    abstract fun getContentContainerId(): Int

    abstract fun getTopLevelViewControllerId(): Int

    abstract fun wrapWithNavigationContainer(): Boolean

    protected abstract fun getNavigationViewControllers(): SparseArray<Pair<Class<out ViewController<*, *>>, Parcelable>>

    open fun getReselectListener(): (() -> Unit) = { getNavigationActivity().getInnerNavigation().up() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigationController = BottomNavigationController(
                context = requireContext(),
                fragmentManager = childFragmentManager,
                viewControllers = getNavigationViewControllers(),
                contentContainerViewId = getContentContainerId(),
                topLevelViewControllerId = getTopLevelViewControllerId(),
                wrapWithNavigationContainer = wrapWithNavigationContainer(),
                onReselectListener = getReselectListener()
        )
        if (savedInstanceState == null) {
            bottomNavigationController.navigateTo(getTopLevelViewControllerId())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(getRootViewId(), container, false)

        bottomNavigationController.attach(fragmentView.findViewById(getNavigationContainerId()))

        (activity as NavigationActivity).addOnBackPressedListener(backPressedListener)

        return fragmentView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as NavigationActivity).removeOnBackPressedListener(backPressedListener)
        bottomNavigationController.detach()
    }

    private fun getNavigationActivity() = requireActivity() as NavigationActivity

}
