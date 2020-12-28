package ru.touchin.roboswag.navigation_cicerone

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder

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
