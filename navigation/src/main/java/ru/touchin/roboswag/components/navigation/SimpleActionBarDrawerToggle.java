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

package ru.touchin.roboswag.components.navigation;

import android.animation.ValueAnimator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import ru.touchin.roboswag.components.navigation.activities.BaseActivity;
import ru.touchin.roboswag.components.navigation.activities.OnBackPressedListener;
import ru.touchin.roboswag.components.utils.UiUtils;

/**
 * Created by Gavriil Sitnikov on 11/03/16.
 * Simple realization of one-side {@link ActionBarDrawerToggle}.
 */
public class SimpleActionBarDrawerToggle extends ActionBarDrawerToggle
        implements FragmentManager.OnBackStackChangedListener, OnBackPressedListener {

    @NonNull
    private final BaseActivity activity;
    @NonNull
    private final DrawerLayout drawerLayout;
    @NonNull
    private final View sidebar;

    private boolean isInvalidateOptionsMenuSupported = true;

    private boolean hamburgerShowed;
    private boolean sidebarDisabled;

    private float slideOffset;
    private float slidePosition;

    @Nullable
    private ValueAnimator hamburgerAnimator;
    private boolean firstAnimation = true;

    public SimpleActionBarDrawerToggle(@NonNull final BaseActivity activity, @NonNull final DrawerLayout drawerLayout, @NonNull final View sidebar) {
        super(activity, drawerLayout, 0, 0);
        this.activity = activity;
        this.drawerLayout = drawerLayout;
        this.sidebar = sidebar;

        drawerLayout.addDrawerListener(this);
        activity.getSupportFragmentManager().addOnBackStackChangedListener(this);
        activity.addOnBackPressedListener(this);
    }

    /**
     * Returns base {@link DrawerLayout}.
     *
     * @return {@link DrawerLayout}.
     */
    @NonNull
    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    /**
     * Returns if sidebar is opened.
     *
     * @return True if sidebar is opened.
     */
    public boolean isOpened() {
        return drawerLayout.isDrawerOpen(sidebar);
    }

    private boolean shouldShowHamburger() {
        return !hamburgerShowed && !sidebarDisabled;
    }

    /**
     * Method to process clicking on hamburger. It is needed to be called from {@link android.app.Activity#onOptionsItemSelected(MenuItem)}.
     * If this method won't be called then opening-closing won't work.
     *
     * @param item Selected item.
     * @return True if item clicking processed.
     */
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        return shouldShowHamburger() && super.onOptionsItemSelected(item);
    }

    private void update() {
        setHamburgerState(shouldShowHamburger());
        drawerLayout.setDrawerLockMode(sidebarDisabled ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    /**
     * Disables sidebar. So it will be in closed state and couldn't be opened.
     */
    public void disableSidebar() {
        sidebarDisabled = true;
        close();
        update();
    }

    /**
     * Enables sidebar. So it could be opened.
     */
    public void enableSidebar() {
        sidebarDisabled = false;
        update();
    }

    /**
     * Hides hamburger icon. Use it if there are some fragments in activity's stack.
     */
    public void hideHamburger() {
        syncState();
        hamburgerShowed = true;
        update();
    }

    /**
     * Shows hamburger icon. Use it if there are no fragments in activity's stack or current fragment is like top.
     */
    public void showHamburger() {
        syncState();
        hamburgerShowed = false;
        update();
    }

    /**
     * Opens sidebar.
     */
    public void open() {
        if (!sidebarDisabled && !drawerLayout.isDrawerOpen(sidebar)) {
            drawerLayout.openDrawer(sidebar);
        }
    }

    /**
     * Closes sidebar.
     */
    public void close() {
        if (drawerLayout.isDrawerOpen(sidebar)) {
            drawerLayout.closeDrawer(sidebar);
        }
    }

    /**
     * Call it when back stack of activity's fragments have changed.
     */
    @Override
    public void onBackStackChanged() {
        close();
    }

    /**
     * Call it when system back button have pressed.
     */
    @Override
    public boolean onBackPressed() {
        if (drawerLayout.isDrawerOpen(sidebar)) {
            close();
            return true;
        }
        return false;
    }

    private void setHamburgerState(final boolean showHamburger) {
        if (!firstAnimation) {
            cancelAnimation();
            if (showHamburger) {
                hamburgerAnimator = ValueAnimator.ofFloat(slideOffset, 0f);
            } else {
                hamburgerAnimator = ValueAnimator.ofFloat(slideOffset, 1f);
            }
            hamburgerAnimator.addUpdateListener(animation -> onDrawerSlide(drawerLayout, (Float) animation.getAnimatedValue()));
            hamburgerAnimator.start();
        } else {
            slideOffset = showHamburger ? 0f : 1f;
            onDrawerSlide(drawerLayout, slideOffset);
        }
        slidePosition = showHamburger ? 0f : 1f;
        firstAnimation = false;
    }

    private void cancelAnimation() {
        if (hamburgerAnimator != null) {
            hamburgerAnimator.cancel();
        }
    }

    @Override
    public void onDrawerClosed(@NonNull final View view) {
        if (isInvalidateOptionsMenuSupported) {
            activity.invalidateOptionsMenu();
        }
    }

    /**
     * Call it at {@link android.app.Activity#onPostCreate(android.os.Bundle)}.
     */
    @Override
    public void syncState() {
        cancelAnimation();
        super.syncState();
    }

    @Override
    public void onDrawerOpened(@NonNull final View drawerView) {
        UiUtils.OfViews.hideSoftInput(activity);
        if (isInvalidateOptionsMenuSupported) {
            activity.invalidateOptionsMenu();
        }
    }

    /**
     * Set turn on/off invocation of supportInvalidateOptionsMenu
     *
     * @param isInvalidateOptionsMenuSupported flag for turning on/off invocation.
     */
    public void setInvalidateOptionsMenuSupported(final boolean isInvalidateOptionsMenuSupported) {
        this.isInvalidateOptionsMenuSupported = isInvalidateOptionsMenuSupported;
    }

    @Override
    public void onDrawerSlide(@NonNull final View drawerView, final float slideOffset) {
        if (slideOffset >= this.slideOffset && slideOffset <= this.slidePosition
                || slideOffset <= this.slideOffset && slideOffset >= this.slidePosition) {
            this.slideOffset = slideOffset;
        }
        super.onDrawerSlide(drawerView, this.slideOffset);
    }

}
