package ru.touchin.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import ru.touchin.roboswag.core.log.Lc

fun Context.safeStartActivity(intent: Intent, options: Bundle? = null) =
        try {
            startActivity(intent, options)
        } catch (e: ActivityNotFoundException) {
            Lc.e(e, "Couldn't find activity with this parameters")
        }

fun Context.openBrowser(url: String) = Intent(Intent.ACTION_VIEW)
        .setData(Uri.parse(url))
        .let { intent -> safeStartActivity(intent) }

fun Context.callToPhoneNumber(phoneNumber: String) = Intent(Intent.ACTION_VIEW)
        .setData(Uri.parse("tel:$phoneNumber"))
        .let { intent -> safeStartActivity(intent) }
