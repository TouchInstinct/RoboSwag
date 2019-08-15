package ru.touchin.extensions

import android.app.Activity
import android.app.ActivityManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Activity.safeStartActivityForResult(intent: Intent, requestCode: Int, options: Bundle? = null, resolveFlags: Int = 0): Boolean =
        packageManager.resolveActivity(intent, resolveFlags)?.let { startActivityForResult(intent, requestCode, options) } != null

/**
 * Setup task description of application for Android 5.0 and later. It is showing when user opens task bar.
 *
 * @param label           Name of application to show in task bar;
 * @param iconRes         Icon of application to show in task bar;
 * @param primaryColorRes Color of application to show in task bar.
 */
fun Activity.setupTaskDescriptor(label: String, @DrawableRes iconRes: Int, @ColorRes primaryColorRes: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val taskDescription = ActivityManager.TaskDescription(
                label,
                iconRes,
                ContextCompat.getColor(this, primaryColorRes)
        )
        setTaskDescription(taskDescription)
    }
}