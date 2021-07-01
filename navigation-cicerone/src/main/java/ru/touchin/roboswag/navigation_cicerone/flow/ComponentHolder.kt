package ru.touchin.roboswag.navigation_cicerone.flow

import me.vponomarenko.injectionmanager.customlifecycle.StoredComponent
import ru.touchin.roboswag.navigation_base.scopes.FeatureScope
import javax.inject.Inject

@FeatureScope
class ComponentHolder<TComponent> @Inject constructor(){

    private var storedComponent: StoredComponent<TComponent>? = null

    fun setStoredComponent(storedComponent: StoredComponent<TComponent>) {
        this.storedComponent = storedComponent
    }

    fun destroy() {
        storedComponent?.lifecycle?.destroy()
    }

}
