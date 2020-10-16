package ru.touchin.roboswag.bottom_navigation_fragment

import android.os.Parcelable
import ru.touchin.roboswag.bottom_navigation_base.BaseNavigationTab
import ru.touchin.roboswag.navigation_base.fragments.StatefulFragment

class NavigationTab(
        override val cls: Class<out StatefulFragment<*, *>>,
        state: Parcelable,
        saveStateOnSwitching: Boolean = true
) : BaseNavigationTab(cls, state, saveStateOnSwitching)
