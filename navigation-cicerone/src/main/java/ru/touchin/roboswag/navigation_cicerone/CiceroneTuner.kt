package ru.touchin.roboswag.navigation_cicerone

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder

/**
 * CiceroneTuner is responsible for adding Navigator to NavigatorHolder in onResume and
 * removing Navigator in onPause.
 *
 * You should add CiceroneTuner like an Observer of SingleActivity or FlowFragment lifecycle.
 *
 * @param navigatorHolder - interface to connect a {@link Navigator} to the {@link Cicerone};
 * @param navigator - {@link Navigator} implementation;
 */

class CiceroneTuner(
        private val navigatorHolder: NavigatorHolder,
        private val navigator: Navigator
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun addNavigator() {
        navigatorHolder.setNavigator(navigator)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun removeNavigator() {
        navigatorHolder.removeNavigator()
    }

}
