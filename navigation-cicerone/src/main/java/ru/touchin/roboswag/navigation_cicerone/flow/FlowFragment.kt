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

/**
 * Base parent fragment for fragments of whole feature. FlowFragment has own navigator based on childFragmentManager.
 * FlowFragment is responsible for handling of back button press.
 *
 * You should connect FlowNavigationModule to your Dagger component and add inject method for your flow fragment.
 */
abstract class FlowFragment : Fragment(R.layout.fragment_flow) {

    @Inject
    @FlowNavigation
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    @FlowNavigation
    lateinit var router: Router

    private val exitRouterOnBackPressed = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            router.exit()
        }
    }

    open fun createNavigator(): Navigator = SupportAppNavigator(
            requireActivity(),
            childFragmentManager,
            getFragmentContainerId()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectComponent()
        if (childFragmentManager.fragments.isEmpty()) {
            router.newRootScreen(getLaunchScreen())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(
                CiceroneTuner(navigatorHolder = navigatorHolder, navigator = createNavigator())
        )

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, exitRouterOnBackPressed)
    }

    @IdRes
    protected fun getFragmentContainerId(): Int = R.id.flow_parent

    abstract fun injectComponent()

    abstract fun getLaunchScreen(): SupportAppScreen

}
