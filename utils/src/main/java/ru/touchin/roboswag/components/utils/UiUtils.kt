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

package ru.touchin.roboswag.components.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.DisplayMetrics
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import ru.touchin.roboswag.components.utils.spans.getSpannedTextWithUrls

/**
 * Created by Gavriil Sitnikov on 13/11/2015.
 * General utilities related to UI (Inflation, Views, Metrics, Activities etc.).
 */
object UiUtils {

    /**
     * Method to inflate view with right layout parameters based on container and add inflated view as a child to it.
     *
     * @param layoutId Id of layout resource;
     * @param parent   Container to rightly resolve layout parameters of view in XML;
     * @return Inflated view.
     */
    fun inflateAndAdd(@LayoutRes layoutId: Int, parent: ViewGroup): View {
        LayoutInflater.from(parent.context).inflate(layoutId, parent, true)
        return parent.getChildAt(parent.childCount - 1)
    }

    /**
     * Method to inflate view with right layout parameters based on container but not adding inflated view as a child to it.
     *
     * @param layoutId Id of layout resource;
     * @param parent   Container to rightly resolve layout parameters of view in XML;
     * @return Inflated view.
     */
    fun inflate(@LayoutRes layoutId: Int, parent: ViewGroup): View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

    /**
     * Convert text with 'href' tags and raw links to spanned text with clickable URLSpan.
     */
    @Deprecated(
            "use extension in SpanUtils",
            ReplaceWith("text.getSpannedTextWithUrls(removeUnderline = false)", "ru.touchin.roboswag.components.utils.spans.getSpannedTextWithUrls")
    )
    fun getSpannedTextWithUrls(text: String) = text.getSpannedTextWithUrls(removeUnderline = false)

    /**
     * Utilities methods related to metrics.
     */
    object OfMetrics {

        private const val MAX_METRICS_TRIES_COUNT = 5

        /**
         * Returns right metrics with non-zero height/width.
         * It is common bug when metrics are calling at [Application.onCreate] method and it returns metrics with zero height/width.
         *
         * @param context [Context] of metrics;
         * @return [DisplayMetrics].
         */
        @Deprecated(
                message = "Use extension instead",
                replaceWith = ReplaceWith("context.getDisplayMetrics()")
        )
        fun getDisplayMetrics(context: Context): DisplayMetrics = context.getDisplayMetrics()

        /**
         * Simply converts DP to pixels.
         *
         * @param context  [Context] of metrics;
         * @param sizeInDp Size in DP;
         * @return Size in pixels.
         */
        @Deprecated(
                message = "Use extension instead",
                replaceWith = ReplaceWith("sizeInDp.toPixels()")
        )
        fun dpToPixels(context: Context, sizeInDp: Float): Float = sizeInDp.px

        @Deprecated(
                message = "Use extension instead",
                replaceWith = ReplaceWith("pixels.toDp()")
        )
        fun pixelsToDp(context: Context, pixels: Int): Int = pixels.dp

    }

    /**
     * Utilities methods related to activities and it'sintents.
     */
    object OfActivities {

        /**
         * Returns action bar (on top like toolbar or appbar) common height (56dp).
         *
         * @param activity Activity of action bar;
         * @return Height of action bar.
         */
        fun getActionBarHeight(activity: Activity): Int = OfMetrics.dpToPixels(activity, 56f).toInt()

        /**
         * Returns status bar (on top where system info is) common height.
         *
         * @param activity Activity of status bar;
         * @return Height of status bar.
         */
        fun getStatusBarHeight(activity: Activity): Int {
            val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
            return if (resourceId > 0) activity.resources.getDimensionPixelSize(resourceId) else 0
        }

        /**
         * Returns navigation bar (on bottom where system buttons are) common height.
         * Be aware that some devices have no software keyboard (check it by [.hasSoftKeys]) but this method will return you
         * size like they are showed.
         *
         * @param activity Activity of navigation bar;
         * @return Height of navigation bar.
         */
        fun getNavigationBarHeight(activity: Activity): Int {
            if (hasSoftKeys(activity)) {
                val resourceId = activity.resources.getIdentifier("navigation_bar_height", "dimen", "android")
                return if (resourceId > 0) activity.resources.getDimensionPixelSize(resourceId) else 0
            }
            return 0
        }

        /**
         * Returns if device has software keyboard at navigation bar or not.
         *
         * @param activity Activity of navigation bar;
         * @return True if software keyboard is showing at navigation bar.
         */
        //http://stackoverflow.com/questions/14853039/how-to-tell-whether-an-android-device-has-hard-keys/14871974#14871974
        fun hasSoftKeys(activity: Activity): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                val display = activity.windowManager.defaultDisplay

                val realDisplayMetrics = DisplayMetrics().also(display::getRealMetrics)

                val displayMetrics = DisplayMetrics().also(display::getMetrics)


                return realDisplayMetrics.widthPixels - displayMetrics.widthPixels > 0
                        || realDisplayMetrics.heightPixels - displayMetrics.heightPixels > 0
            }

            val hasMenuKey = ViewConfiguration.get(activity).hasPermanentMenuKey()
            val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            return !hasMenuKey && !hasBackKey
        }

        /**
         * Returns if [Intent] could be handled by system.
         *
         * @param context [Context] of application;
         * @param intent  [Intent] to be handled;
         * @return True if there are handlers for [Intent] (e.g. browser could handle URI intent).
         */
        fun isIntentAbleToHandle(context: Context, intent: Intent): Boolean = context.packageManager.queryIntentActivities(intent, 0).isNotEmpty()

    }

    /**
     * Utilities methods related to views.
     */
    object OfViews {

        /**
         * Returns string representation of [View]'s ID.
         *
         * @param view [View] to get ID from;
         * @return Readable ID.
         */
        @Deprecated(
                message = "Use extension instead",
                replaceWith = ReplaceWith("view.getViewIdString()")
        )
        fun getViewIdString(view: View): String = view.getViewIdString()

        /**
         * Hides device keyboard for target activity.
         */
        @Deprecated(
                message = "Use extension instead",
                replaceWith = ReplaceWith("activity.hideSoftInput()")
        )
        fun hideSoftInput(activity: Activity) = activity.hideSoftInput()


        /**
         * Hides device keyboard for target view.
         */
        @Deprecated(
                message = "Use extension instead",
                replaceWith = ReplaceWith("view.hideSoftInput()")
        )
        fun hideSoftInput(view: View) = view.hideSoftInput()

        /**
         * Shows device keyboard over [Activity] and focuses [View].
         * Do NOT use it if keyboard is over [android.app.Dialog] - it won't work as they have different [Activity.getWindow].
         * Do NOT use it if you are not sure that view is already added on screen.
         *
         * @param view View to get focus for input from keyboard.
         */
        @Deprecated(
                message = "Use extension instead",
                replaceWith = ReplaceWith("view.showSoftInput()")
        )
        fun showSoftInput(view: View) = view.showSoftInput()

    }

}
