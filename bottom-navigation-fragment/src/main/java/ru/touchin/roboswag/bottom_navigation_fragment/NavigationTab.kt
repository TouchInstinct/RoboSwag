package ru.touchin.roboswag.bottom_navigation_fragment

import android.os.Parcelable
import ru.touchin.roboswag.navigation_base.fragments.BaseFragment

class NavigationTab(
        override val cls: Class<out BaseFragment<*, *>>,
        state: Parcelable,
        saveStateOnSwitching: Boolean = true
) : BaseNavigationTab(cls, state, saveStateOnSwitching)
