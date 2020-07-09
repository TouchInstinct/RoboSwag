package ru.touchin.roboswag.components.utils

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics

private const val MAX_METRICS_TRIES_COUNT = 5

/**
 * Returns right metrics with non-zero height/width.
 * It is common bug when metrics are calling at [Application.onCreate] method and it returns metrics with zero height/width.
 *
 * @param context [Context] of metrics;
 * @return [DisplayMetrics].
 */
fun Context.getDisplayMetrics(): DisplayMetrics {
    var result = resources.displayMetrics
    // it is needed to avoid bug with invalid metrics when user restore application from other application
    var metricsTryNumber = 0
    while (metricsTryNumber < MAX_METRICS_TRIES_COUNT && (result.heightPixels <= 0 || result.widthPixels <= 0)) {
        try {
            Thread.sleep(500)
        } catch (ignored: InterruptedException) {
            return result
        }

        result = resources.displayMetrics
        metricsTryNumber++
    }
    return result
}

/**
 * Simply converts Dp to pixels.
 *
 * @return Size in pixels.
 */
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Simply converts Dp to pixels.
 *
 * @return Size in pixels.
 */
val Float.px: Float
    get() = this * Resources.getSystem().displayMetrics.density

/**
 * Simply converts pixels to Dp.
 *
 * @return Size in dp.
 */
val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
