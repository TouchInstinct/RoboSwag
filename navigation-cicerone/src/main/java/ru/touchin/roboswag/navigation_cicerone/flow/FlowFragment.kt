package ru.touchin.roboswag.navigation_cicerone.flow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import me.vponomarenko.injectionmanager.customlifecycle.StoredComponent
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.touchin.mvi_arch.core_nav.R
import ru.touchin.mvi_arch.di.FeatureScope
import ru.touchin.roboswag.navigation_base.activities.BaseActivity
import ru.touchin.roboswag.navigation_base.activities.OnBackPressedListener
import ru.touchin.roboswag.navigation_cicerone.CiceroneTuner
import javax.inject.Inject

abstract class FlowFragment<TComponent> : Fragment(R.layout.fragment_flow), OnBackPressedListener {

    @Inject
    @FlowNavigation
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    @FlowNavigation
    lateinit var router: Router

    @Inject
    @FeatureScope
    lateinit var componentHolder: ComponentHolder<TComponent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!injectExistedComponent()) {
            val storedComponent = injectComponent()
            componentHolder.setStoredComponent(storedComponent)
        }

        if (childFragmentManager.fragments.isEmpty()) {
            router.newRootScreen(getLaunchScreen())
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? BaseActivity)?.addOnBackPressedListener(this)
    }

    override fun onStop() {
        super.onStop()
        (activity as? BaseActivity)?.removeOnBackPressedListener(this)
    }

    abstract fun injectComponent(): StoredComponent<TComponent>

    abstract fun injectExistedComponent(): Boolean

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(
                CiceroneTuner(
                        activity = requireActivity(),
                        navigatorHolder = navigatorHolder,
                        fragmentContainerId = R.id.flow_parent,
                        fragmentManager = childFragmentManager
                )
        )
    }

    override fun onBackPressed(): Boolean {
        componentHolder.destroy()
        return false
    }

    abstract fun getLaunchScreen(): SupportAppScreen
}
