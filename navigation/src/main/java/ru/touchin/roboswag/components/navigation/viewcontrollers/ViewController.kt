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

package ru.touchin.roboswag.components.navigation.viewcontrollers

import android.animation.Animator
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import ru.touchin.roboswag.components.navigation.fragments.ViewControllerFragment
import ru.touchin.roboswag.components.utils.UiUtils
import ru.touchin.roboswag.core.log.Lc
import ru.touchin.roboswag.core.log.LcGroup

/**
 * Created by Gavriil Sitnikov on 21/10/2015.
 * Class to control view of specific fragment, activity and application by logic bridge.
 *
 * @param <TActivity> Type of activity where such [ViewController] could be;
 * @param <TState>    Type of state;
</TState></TActivity> */
@Suppress("PMD.UnusedFormalParameter", "UNCHECKED_CAST")
open class ViewController<TActivity : FragmentActivity, TState : Parcelable>(
        creationContext: CreationContext,
        savedInstanceState: Bundle?,
        @LayoutRes layoutRes: Int
) : LifecycleOwner {

    val activity: TActivity = creationContext.activity as TActivity
    val fragment: ViewControllerFragment<out TActivity, out TState> = creationContext.fragment as ViewControllerFragment<out TActivity, out TState>
    val state = fragment.state
    val view: View = creationContext.inflater.inflate(layoutRes, creationContext.container, false)

    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    /**
     * Look for a child view with the given id.  If this view has the given id, return this view.
     *
     * @param id The id to search for;
     * @return The view that has the given id in the hierarchy.
     */
    fun <T : View> findViewById(@IdRes id: Int): T = view.findViewById(id)

    /**
     * Return a localized, styled CharSequence from the application's package's
     * default string table.
     *
     * @param resId Resource id for the CharSequence text
     */
    fun getText(@StringRes resId: Int): CharSequence = activity.getText(resId)

    /**
     * Return a localized string from the application's package's default string table.
     *
     * @param resId Resource id for the string
     */
    fun getString(@StringRes resId: Int): String = activity.getString(resId)

    /**
     * Return a localized formatted string from the application's package's default string table, substituting the format arguments as defined in
     * [java.util.Formatter] and [java.lang.String.format].
     *
     * @param resId      Resource id for the format string
     * @param formatArgs The format arguments that will be used for substitution.
     */
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String = activity.getString(resId, *formatArgs)

    /**
     * Return the color value associated with a particular resource ID.
     * Starting in [android.os.Build.VERSION_CODES.M], the returned
     * color will be styled for the specified Context's theme.
     *
     * @param resId The resource id to search for data;
     * @return int A single color value in the form 0xAARRGGBB.
     */
    @ColorInt
    fun getColor(@ColorRes resId: Int): Int = ContextCompat.getColor(activity, resId)

    /**
     * Returns a color state list associated with a particular resource ID.
     *
     *
     * Starting in [android.os.Build.VERSION_CODES.M], the returned
     * color state list will be styled for the specified Context's theme.
     *
     * @param resId The desired resource identifier, as generated by the aapt
     * tool. This integer encodes the package, type, and resource
     * entry. The value 0 is an invalid identifier.
     * @return A color state list, or `null` if the resource could not be resolved.
     * @throws android.content.res.Resources.NotFoundException if the given ID
     * does not exist.
     */
    fun getColorStateList(@ColorRes resId: Int): ColorStateList? = ContextCompat.getColorStateList(activity, resId)

    /**
     * Returns a drawable object associated with a particular resource ID.
     * Starting in [android.os.Build.VERSION_CODES.LOLLIPOP], the
     * returned drawable will be styled for the specified Context's theme.
     *
     * @param resId The resource id to search for data;
     * @return Drawable An object that can be used to draw this resource.
     */
    fun getDrawable(@DrawableRes resId: Int): Drawable? = ContextCompat.getDrawable(activity, resId)

    fun startActivity(intent: Intent) {
        fragment.startActivity(intent)
    }

    fun startActivityForResult(intent: Intent, requestCode: Int) {
        fragment.startActivityForResult(intent, requestCode)
    }

    /**
     * Calls when activity configuring ActionBar, Toolbar, Sidebar etc.
     * If it will be called or not depends on [Fragment.hasOptionsMenu] and [Fragment.isMenuVisible].
     *
     * @param menu     The options menu in which you place your items;
     * @param inflater Helper to inflate menu items.
     */
    open fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = Unit

    /**
     * Calls right after construction of [ViewController].
     * Happens at [ViewControllerFragment.onActivityCreated].
     */
    @CallSuper
    open fun onCreate() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    /**
     * Called when a fragment loads an animation. Note that if
     * [FragmentTransaction.setCustomAnimations] was called with
     * [Animator] resources instead of [Animation] resources, `nextAnim`
     * will be an animator resource.
     *
     * @param transit  The value set in [FragmentTransaction.setTransition] or 0 if not
     * set.
     * @param enter    `true` when the fragment is added/attached/shown or `false` when
     * the fragment is removed/detached/hidden.
     * @param nextAnim The resource set in
     * [FragmentTransaction.setCustomAnimations],
     * [FragmentTransaction.setCustomAnimations], or
     * 0 if neither was called. The value will depend on the current operation.
     */
    fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? = null

    /**
     * Called when a fragment loads an animator. This will be called when
     * [.onCreateAnimation] returns null. Note that if
     * [FragmentTransaction.setCustomAnimations] was called with
     * [Animation] resources instead of [Animator] resources, `nextAnim`
     * will be an animation resource.
     *
     * @param transit  The value set in [FragmentTransaction.setTransition] or 0 if not
     * set.
     * @param enter    `true` when the fragment is added/attached/shown or `false` when
     * the fragment is removed/detached/hidden.
     * @param nextAnim The resource set in
     * [FragmentTransaction.setCustomAnimations],
     * [FragmentTransaction.setCustomAnimations], or
     * 0 if neither was called. The value will depend on the current operation.
     */
    fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? = null

    /**
     * Calls when [ViewController] saved state has been restored into the view hierarchy.
     * Happens at [ViewControllerFragment.onViewStateRestored].
     */
    fun onViewStateRestored(savedInstanceState: Bundle?) = Unit

    /**
     * Calls when [ViewController] have started.
     * Happens at [ViewControllerFragment.onStart].
     */
    @CallSuper
    open fun onStart() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        UiUtils.OfViews.hideSoftInput(view)
    }

    /**
     * Called when fragment is moved in started state and it's [.getFragment] sets to true.
     * Usually it is indicating that user can't see fragment on screen and useful to track analytics events.
     */
    open fun onAppear() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
    }

    /**
     * Calls when [ViewController] have resumed.
     * Happens at [ViewControllerFragment.onResume].
     */
    @CallSuper
    open fun onResume() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    /**
     * Calls when [ViewController] have goes near out of memory state.
     * Happens at [ViewControllerFragment.onLowMemory].
     */
    @CallSuper
    open fun onLowMemory() = Unit

    /**
     * Calls when [ViewController] have paused.
     * Happens at [ViewControllerFragment.onPause].
     */
    @CallSuper
    open fun onPause() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    /**
     * Calls when [ViewController] should save it's state.
     * Happens at [ViewControllerFragment.onSaveInstanceState].
     * Try not to use such method for saving state but use [ViewControllerFragment.state] from [.getFragment].
     */
    @CallSuper
    fun onSaveInstanceState(savedInstanceState: Bundle) {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
    }

    /**
     * Called when fragment is moved in stopped state or it's [.getFragment] sets to false.
     * Usually it is indicating that user can't see fragment on screen and useful to track analytics events.
     */
    fun onDisappear() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
    }

    /**
     * Calls when [ViewController] have stopped.
     * Happens at [ViewControllerFragment.onStop].
     */
    @CallSuper
    open fun onStop() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    /**
     * Calls when [ViewController] have destroyed.
     * Happens usually at [ViewControllerFragment.onDestroyView]. In some cases at [ViewControllerFragment.onDestroy].
     */
    @CallSuper
    open fun onDestroy() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this))
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    /**
     * Calls when [ViewController] have requested permissions results.
     * Happens at [ViewControllerFragment.onRequestPermissionsResult] ()}.
     */
    open fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) = Unit

    /**
     * Callback from parent fragment.
     */
    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) = Unit

    /**
     * Similar to [ViewControllerFragment.onOptionsItemSelected].
     *
     * @param item Selected menu item;
     * @return True if selection processed.
     */
    open fun onOptionsItemSelected(item: MenuItem): Boolean = false

    /**
     * Helper class to simplify constructor override.
     */
    data class CreationContext(
            val activity: FragmentActivity,
            val fragment: ViewControllerFragment<*, *>,
            val inflater: LayoutInflater,
            val container: ViewGroup?
    )

}
