package ru.touchin.roboswag.bottom_navigation_viewcontroller

import android.os.Parcelable
import ru.touchin.roboswag.bottom_navigation_fragment.BaseNavigationTab
import ru.touchin.roboswag.navigation_viewcontroller.viewcontrollers.ViewController

class NavigationTab(
        override val cls: Class<out ViewController<*, *>>,
        state: Parcelable,
        /**
         * It can be useful in some cases when it is necessary to create ViewController
         * with initial state every time when tab opens.
         */
        saveStateOnSwitching: Boolean = true
) : BaseNavigationTab(cls, state, saveStateOnSwitching)
