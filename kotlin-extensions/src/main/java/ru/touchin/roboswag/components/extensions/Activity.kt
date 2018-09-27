package ru.touchin.roboswag.components.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle

fun Activity.safeStartActivityForResult(intent: Intent, requestCode: Int, options: Bundle? = null, resolveFlags: Int = 0): Boolean =
        packageManager.resolveActivity(intent, resolveFlags)?.let { startActivityForResult(intent, requestCode, options) } != null
