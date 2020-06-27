package ru.touchin.roboswag.navigation_cicerone

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class CiceroneTuner(
        private val activity: FragmentActivity,
        private val navigatorHolder: NavigatorHolder,
        @IdRes private val fragmentContainerId: Int,
        private val fragmentManager: FragmentManager? = null
) : LifecycleObserver {

    val navigator by lazy(this::createNavigator)

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun addNavigator() {
        navigatorHolder.setNavigator(navigator)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun removeNavigator() {
        navigatorHolder.removeNavigator()
    }

    private fun createNavigator() = if (fragmentManager != null) {
        SupportAppNavigator(
                activity,
                fragmentManager,
                fragmentContainerId
        )
    } else {
        SupportAppNavigator(
                activity,
                fragmentContainerId
        )
    }

}
