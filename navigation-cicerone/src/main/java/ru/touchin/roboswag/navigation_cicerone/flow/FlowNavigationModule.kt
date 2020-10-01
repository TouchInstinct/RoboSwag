package ru.touchin.roboswag.navigation_cicerone.flow

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.touchin.roboswag.navigation_base.scopes.FeatureScope

/**
 * Module to provide Cicerone.
 *
 * You should add it to @Component annotation of your feature's component.
 */

@Module
class FlowNavigationModule {

    @Provides
    @FlowNavigation
    @FeatureScope
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @FlowNavigation
    fun provideNavigatorHolder(@FlowNavigation cicerone: Cicerone<Router>): NavigatorHolder = cicerone.navigatorHolder

    @Provides
    @FlowNavigation
    fun provideRouter(@FlowNavigation cicerone: Cicerone<Router>): Router = cicerone.router

}
