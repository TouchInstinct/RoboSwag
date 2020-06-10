package ru.touchin.roboswag

import android.content.SharedPreferences

fun TouchinSharedPreferences.migrateFromSharedPreferences(from: SharedPreferences, key: String): SharedPreferences {
    if (!from.contains(key)) return this
    edit().putString(key, from.getString(key, "") ?: "").apply()
    return this
}
