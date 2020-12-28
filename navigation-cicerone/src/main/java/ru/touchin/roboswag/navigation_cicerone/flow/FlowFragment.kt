package ru.touchin.roboswag.navigation_cicerone.flow

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.touchin.mvi_arch.core_nav.R
import ru.touchin.roboswag.navigation_cicerone.CiceroneTuner
import javax.inject.Inject

abstract class FlowFragment : Fragment(R.layout.fragment_flow) {

    @Inject
    @FlowNavigation
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    @FlowNavigation
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectComponent()
        if (childFragmentManager.fragments.isEmpty()) {
            router.newRootScreen(getLaunchScreen())
        }
    }

    abstract fun injectComponent()

    private val exitRouterOnBackPressed = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            router.exit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(
                CiceroneTuner(navigatorHolder = navigatorHolder, navigator = createNavigator())
        )

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, exitRouterOnBackPressed)
    }

    open fun createNavigator(): Navigator = SupportAppNavigator(
            requireActivity(),
            childFragmentManager,
            getFragmentContainerId()
    )

    @IdRes
    protected fun getFragmentContainerId(): Int = R.id.flow_parent

    abstract fun getLaunchScreen(): SupportAppScreen
}
