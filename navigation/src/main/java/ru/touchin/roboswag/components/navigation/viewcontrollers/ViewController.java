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

package ru.touchin.roboswag.components.navigation.viewcontrollers;

import android.animation.Animator;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import ru.touchin.roboswag.components.navigation.fragments.ViewControllerFragment;
import ru.touchin.roboswag.components.utils.UiUtils;
import ru.touchin.roboswag.core.log.Lc;
import ru.touchin.roboswag.core.log.LcGroup;

/**
 * Created by Gavriil Sitnikov on 21/10/2015.
 * Class to control view of specific fragment, activity and application by logic bridge.
 *
 * @param <TActivity> Type of activity where such {@link ViewController} could be;
 * @param <TState>    Type of state;
 */
public class ViewController<TActivity extends FragmentActivity, TState extends Parcelable> implements LifecycleOwner {

    @NonNull
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    @NonNull
    private final TActivity activity;
    @NonNull
    private final ViewControllerFragment<TActivity, TState> fragment;
    @NonNull
    private final View view;

    @SuppressWarnings({"unchecked", "PMD.UnusedFormalParameter"})
    //UnusedFormalParameter: savedInstanceState could be used by children
    public ViewController(@NonNull final CreationContext creationContext, @Nullable final Bundle savedInstanceState, @LayoutRes final int layoutRes) {
        this.activity = (TActivity) creationContext.activity;
        this.fragment = creationContext.fragment;
        view = creationContext.inflater.inflate(layoutRes, creationContext.container, false);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    /**
     * Returns activity where {@link ViewController} could be.
     *
     * @return Returns activity.
     */
    @NonNull
    public final TActivity getActivity() {
        return activity;
    }

    /**
     * Returns fragment where {@link ViewController} could be.
     *
     * @return Returns fragment.
     */
    @NonNull
    public final ViewControllerFragment<TActivity, TState> getFragment() {
        return fragment;
    }

    /**
     * Returns state from fragment.
     *
     * @return Returns state.
     */
    @NonNull
    public final TState getState() {
        return fragment.getState();
    }

    /**
     * Returns view instantiated in {@link #getFragment()} fragment attached to {@link #getActivity()} activity.
     * Use it to inflate your views into at construction of this {@link ViewController}.
     *
     * @return Returns view.
     */
    @NonNull
    public final View getView() {
        return view;
    }

    /**
     * Look for a child view with the given id.  If this view has the given id, return this view.
     *
     * @param id The id to search for;
     * @return The view that has the given id in the hierarchy.
     */
    @NonNull
    public final <T extends View> T findViewById(@IdRes final int id) {
        return getView().findViewById(id);
    }

    /**
     * Return a localized, styled CharSequence from the application's package's
     * default string table.
     *
     * @param resId Resource id for the CharSequence text
     */
    @NonNull
    public final CharSequence getText(@StringRes final int resId) {
        return activity.getText(resId);
    }

    /**
     * Return a localized string from the application's package's default string table.
     *
     * @param resId Resource id for the string
     */
    @NonNull
    public final String getString(@StringRes final int resId) {
        return activity.getString(resId);
    }

    /**
     * Return a localized formatted string from the application's package's default string table, substituting the format arguments as defined in
     * {@link java.util.Formatter} and {@link java.lang.String#format}.
     *
     * @param resId      Resource id for the format string
     * @param formatArgs The format arguments that will be used for substitution.
     */
    @NonNull
    public final String getString(@StringRes final int resId, @NonNull final Object... formatArgs) {
        return activity.getString(resId, formatArgs);
    }

    /**
     * Return the color value associated with a particular resource ID.
     * Starting in {@link android.os.Build.VERSION_CODES#M}, the returned
     * color will be styled for the specified Context's theme.
     *
     * @param resId The resource id to search for data;
     * @return int A single color value in the form 0xAARRGGBB.
     */
    @ColorInt
    public final int getColor(@ColorRes final int resId) {
        return ContextCompat.getColor(activity, resId);
    }

    /**
     * Returns a color state list associated with a particular resource ID.
     *
     * <p>Starting in {@link android.os.Build.VERSION_CODES#M}, the returned
     * color state list will be styled for the specified Context's theme.
     *
     * @param resId The desired resource identifier, as generated by the aapt
     *              tool. This integer encodes the package, type, and resource
     *              entry. The value 0 is an invalid identifier.
     * @return A color state list, or {@code null} if the resource could not be resolved.
     * @throws android.content.res.Resources.NotFoundException if the given ID
     *                                                         does not exist.
     */
    @Nullable
    public final ColorStateList getColorStateList(@ColorRes final int resId) {
        return ContextCompat.getColorStateList(activity, resId);
    }

    /**
     * Returns a drawable object associated with a particular resource ID.
     * Starting in {@link android.os.Build.VERSION_CODES#LOLLIPOP}, the
     * returned drawable will be styled for the specified Context's theme.
     *
     * @param resId The resource id to search for data;
     * @return Drawable An object that can be used to draw this resource.
     */
    @Nullable
    public final Drawable getDrawable(@DrawableRes final int resId) {
        return ContextCompat.getDrawable(activity, resId);
    }

    public final void startActivity(@NonNull final Intent intent) {
        fragment.startActivity(intent);
    }

    public final void startActivityForResult(@NonNull final Intent intent, final int requestCode) {
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * Calls when activity configuring ActionBar, Toolbar, Sidebar etc.
     * If it will be called or not depends on {@link Fragment#hasOptionsMenu()} and {@link Fragment#isMenuVisible()}.
     *
     * @param menu     The options menu in which you place your items;
     * @param inflater Helper to inflate menu items.
     */
    public void onCreateOptionsMenu(@NonNull final Menu menu, @NonNull final MenuInflater inflater) {
        // do nothing
    }

    /**
     * Calls right after construction of {@link ViewController}.
     * Happens at {@link ViewControllerFragment#onActivityCreated(Bundle)}.
     */
    @CallSuper
    public void onCreate() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    /**
     * Called when a fragment loads an animation. Note that if
     * {@link FragmentTransaction#setCustomAnimations(int, int)} was called with
     * {@link Animator} resources instead of {@link Animation} resources, {@code nextAnim}
     * will be an animator resource.
     *
     * @param transit  The value set in {@link FragmentTransaction#setTransition(int)} or 0 if not
     *                 set.
     * @param enter    {@code true} when the fragment is added/attached/shown or {@code false} when
     *                 the fragment is removed/detached/hidden.
     * @param nextAnim The resource set in
     *                 {@link FragmentTransaction#setCustomAnimations(int, int)},
     *                 {@link FragmentTransaction#setCustomAnimations(int, int, int, int)}, or
     *                 0 if neither was called. The value will depend on the current operation.
     */
    @Nullable
    public Animation onCreateAnimation(final int transit, final boolean enter, final int nextAnim) {
        return null;
    }

    /**
     * Called when a fragment loads an animator. This will be called when
     * {@link #onCreateAnimation(int, boolean, int)} returns null. Note that if
     * {@link FragmentTransaction#setCustomAnimations(int, int)} was called with
     * {@link Animation} resources instead of {@link Animator} resources, {@code nextAnim}
     * will be an animation resource.
     *
     * @param transit  The value set in {@link FragmentTransaction#setTransition(int)} or 0 if not
     *                 set.
     * @param enter    {@code true} when the fragment is added/attached/shown or {@code false} when
     *                 the fragment is removed/detached/hidden.
     * @param nextAnim The resource set in
     *                 {@link FragmentTransaction#setCustomAnimations(int, int)},
     *                 {@link FragmentTransaction#setCustomAnimations(int, int, int, int)}, or
     *                 0 if neither was called. The value will depend on the current operation.
     */
    @Nullable
    public Animator onCreateAnimator(final int transit, final boolean enter, final int nextAnim) {
        return null;
    }

    /**
     * Calls when {@link ViewController} saved state has been restored into the view hierarchy.
     * Happens at {@link ViewControllerFragment#onViewStateRestored}.
     */
    public void onViewStateRestored(@Nullable final Bundle savedInstanceState) {
        // do nothing
    }

    /**
     * Calls when {@link ViewController} have started.
     * Happens at {@link ViewControllerFragment#onStart()}.
     */
    @CallSuper
    public void onStart() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        UiUtils.OfViews.hideSoftInput(getView());
    }

    /**
     * Called when fragment is moved in started state and it's {@link #getFragment().isMenuVisible()} sets to true.
     * Usually it is indicating that user can't see fragment on screen and useful to track analytics events.
     */
    public void onAppear() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
    }

    /**
     * Calls when {@link ViewController} have resumed.
     * Happens at {@link ViewControllerFragment#onResume()}.
     */
    @CallSuper
    public void onResume() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    /**
     * Calls when {@link ViewController} have goes near out of memory state.
     * Happens at {@link ViewControllerFragment#onLowMemory()}.
     */
    @CallSuper
    public void onLowMemory() {
        //do nothing
    }

    /**
     * Calls when {@link ViewController} have paused.
     * Happens at {@link ViewControllerFragment#onPause()}.
     */
    @CallSuper
    public void onPause() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    /**
     * Calls when {@link ViewController} should save it's state.
     * Happens at {@link ViewControllerFragment#onSaveInstanceState(Bundle)}.
     * Try not to use such method for saving state but use {@link ViewControllerFragment#getState()} from {@link #getFragment()}.
     */
    @CallSuper
    public void onSaveInstanceState(@NonNull final Bundle savedInstanceState) {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
    }

    /**
     * Called when fragment is moved in stopped state or it's {@link #getFragment().isMenuVisible()} sets to false.
     * Usually it is indicating that user can't see fragment on screen and useful to track analytics events.
     */
    public void onDisappear() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
    }

    /**
     * Calls when {@link ViewController} have stopped.
     * Happens at {@link ViewControllerFragment#onStop()}.
     */
    @CallSuper
    public void onStop() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    /**
     * Calls when {@link ViewController} have destroyed.
     * Happens usually at {@link ViewControllerFragment#onDestroyView()}. In some cases at {@link ViewControllerFragment#onDestroy()}.
     */
    @CallSuper
    public void onDestroy() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    /**
     * Calls when {@link ViewController} have requested permissions results.
     * Happens at {@link ViewControllerFragment#onRequestPermissionsResult(int, String[], int[])} ()}.
     */
    @SuppressWarnings("PMD.UseVarargs")
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        //do nothing
    }

    /**
     * Callback from parent fragment.
     */
    public void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        // do nothing
    }

    /**
     * Similar to {@link ViewControllerFragment#onOptionsItemSelected(MenuItem)}.
     *
     * @param item Selected menu item;
     * @return True if selection processed.
     */
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        return false;
    }

    /**
     * Helper class to simplify constructor override.
     */
    public static class CreationContext {

        @NonNull
        private final FragmentActivity activity;
        @NonNull
        private final ViewControllerFragment fragment;
        @NonNull
        private final LayoutInflater inflater;
        @Nullable
        private final ViewGroup container;

        public CreationContext(
                @NonNull final FragmentActivity activity,
                @NonNull final ViewControllerFragment fragment,
                @NonNull final LayoutInflater inflater,
                @Nullable final ViewGroup container
        ) {
            this.activity = activity;
            this.fragment = fragment;
            this.inflater = inflater;
            this.container = container;
        }

    }

}
