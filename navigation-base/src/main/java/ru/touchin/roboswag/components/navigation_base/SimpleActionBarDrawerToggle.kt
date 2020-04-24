/*
 *  Copyright (c) 2015 RoboSwag (Gavriil Sitnikov, Vsevolod Ivanov)
 *
 *  This file is part of RoboSwag library.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package ru.touchin.roboswag.components.navigation_base

import android.animation.ValueAnimator
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import ru.touchin.roboswag.components.navigation_base.activities.BaseActivity
import ru.touchin.roboswag.components.navigation_base.activities.OnBackPressedListener
import ru.touchin.roboswag.components.utils.UiUtils

/**
 * Created by Gavriil Sitnikov on 11/03/16.
 * Simple realization of one-side [ActionBarDrawerToggle].
 */
class SimpleActionBarDrawerToggle(
        private val activity: BaseActivity,
        val drawerLayout: DrawerLayout,
        private val sidebar: View
) : ActionBarDrawerToggle(activity, drawerLayout, 0, 0), FragmentManager.OnBackStackChangedListener, OnBackPressedListener {

    private var isInvalidateOptionsMenuSupported = true

    private var hamburgerShowed: Boolean = false
    private var sidebarDisabled: Boolean = false

    private var slideOffset: Float = 0f
    private var slidePosition: Float = 0f

    private var hamburgerAnimator: ValueAnimator? = null
    private var firstAnimation = true

    init {
        drawerLayout.addDrawerListener(this)
        activity.supportFragmentManager.addOnBackStackChangedListener(this)
        activity.addOnBackPressedListener(this)
    }

    /**
     * Set turn on/off invocation of supportInvalidateOptionsMenu
     *
     * @param isInvalidateOptionsMenuSupported flag for turning on/off invocation.
     */
    fun setInvalidateOptionsMenuSupported(isInvalidateOptionsMenuSupported: Boolean) {
        this.isInvalidateOptionsMenuSupported = isInvalidateOptionsMenuSupported
    }

    /**
     * Returns if sidebar is opened.
     *
     * @return True if sidebar is opened.
     */
    fun isOpened(): Boolean = drawerLayout.isDrawerOpen(sidebar)

    /**
     * Disables sidebar. So it will be in closed state and couldn't be opened.
     */
    fun disableSidebar() {
        sidebarDisabled = true
        close()
        update()
    }

    /**
     * Enables sidebar. So it could be opened.
     */
    fun enableSidebar() {
        sidebarDisabled = false
        update()
    }

    /**
     * Hides hamburger icon. Use it if there are some fragments in activity's stack.
     */
    fun hideHamburger() {
        syncState()
        hamburgerShowed = true
        update()
    }

    /**
     * Shows hamburger icon. Use it if there are no fragments in activity's stack or current fragment is like top.
     */
    fun showHamburger() {
        syncState()
        hamburgerShowed = false
        update()
    }

    /**
     * Opens sidebar.
     */
    fun open() {
        if (!sidebarDisabled && !drawerLayout.isDrawerOpen(sidebar)) {
            drawerLayout.openDrawer(sidebar)
        }
    }

    /**
     * Closes sidebar.
     */
    fun close() {
        if (drawerLayout.isDrawerOpen(sidebar)) {
            drawerLayout.closeDrawer(sidebar)
        }
    }

    /**
     * Method to process clicking on hamburger. It is needed to be called from [android.app.Activity.onOptionsItemSelected].
     * If this method won't be called then opening-closing won't work.
     *
     * @param item Selected item.
     * @return True if item clicking processed.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean = shouldShowHamburger() && super.onOptionsItemSelected(item)

    /**
     * Call it when back stack of activity's fragments have changed.
     */
    override fun onBackStackChanged() {
        close()
    }

    /**
     * Call it when system back button have pressed.
     */
    override fun onBackPressed(): Boolean = if (isOpened()) {
        close()
        true
    } else {
        false
    }

    override fun onDrawerClosed(view: View) {
        if (isInvalidateOptionsMenuSupported) {
            activity.invalidateOptionsMenu()
        }
    }

    override fun onDrawerSlide(drawerView: View, offset: Float) {
        if (offset in slideOffset..slidePosition
                || offset in slidePosition..slideOffset) {
            slideOffset = offset
        }
        super.onDrawerSlide(drawerView, slideOffset)
    }

    /**
     * Call it at [android.app.Activity.onPostCreate].
     */
    override fun syncState() {
        cancelAnimation()
        super.syncState()
    }

    override fun onDrawerOpened(drawerView: View) {
        UiUtils.OfViews.hideSoftInput(activity)
        if (isInvalidateOptionsMenuSupported) {
            activity.invalidateOptionsMenu()
        }
    }

    private fun shouldShowHamburger(): Boolean = !hamburgerShowed && !sidebarDisabled

    private fun update() {
        setHamburgerState(shouldShowHamburger())
        drawerLayout.setDrawerLockMode(if (sidebarDisabled) DrawerLayout.LOCK_MODE_LOCKED_CLOSED else DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    private fun setHamburgerState(showHamburger: Boolean) {
        if (!firstAnimation) {
            cancelAnimation()
            hamburgerAnimator = ValueAnimator.ofFloat(slideOffset, if (showHamburger) 0f else 1f)
            hamburgerAnimator!!.addUpdateListener { animation -> onDrawerSlide(drawerLayout, animation.animatedValue as Float) }
            hamburgerAnimator!!.start()
        } else {
            slideOffset = if (showHamburger) 0f else 1f
            onDrawerSlide(drawerLayout, slideOffset)
        }
        slidePosition = if (showHamburger) 0f else 1f
        firstAnimation = false
    }

    private fun cancelAnimation() {
        hamburgerAnimator?.cancel()
    }

}
