package ru.touchin.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle

fun Context.safeStartActivity(intent: Intent, options: Bundle? = null, resolveFlags: Int = 0): Boolean =
        packageManager.resolveActivity(intent, resolveFlags)?.let { startActivity(intent, options) } != null
