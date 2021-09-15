package ru.touchin.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Browser

fun Context.safeStartActivity(intent: Intent, options: Bundle? = null, resolveFlags: Int = 0): Boolean =
        packageManager.resolveActivity(intent, resolveFlags)?.let { startActivity(intent, options) } != null

fun Context.openBrowser(url: String) = Intent(Intent.ACTION_VIEW)
        .setData(Uri.parse(url))
        .let { intent -> safeStartActivity(intent) }

fun Context.openBrowserWithHeaders(url: String, headersMap: Map<String, String>) = Intent(Intent.ACTION_VIEW)
        .setData(Uri.parse(url))
        .let { intent ->
            val bundle = Bundle().apply {
                headersMap.forEach { (key, value) ->
                    putString(key, value)
                }
            }
            intent.putExtra(Browser.EXTRA_HEADERS, bundle)
            safeStartActivity(intent)
        }

fun Context.callToPhoneNumber(phoneNumber: String) = Intent(Intent.ACTION_VIEW)
        .setData(Uri.parse("tel:$phoneNumber"))
        .let { intent -> safeStartActivity(intent) }

