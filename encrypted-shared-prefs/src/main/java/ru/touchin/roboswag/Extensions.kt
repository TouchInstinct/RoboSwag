package ru.touchin.roboswag

import android.content.SharedPreferences

fun CipherSharedPreferences.migrateFromSharedPreferences(from: SharedPreferences, key: String): SharedPreferences {
    if (!from.contains(key)) return this
    edit().putString(key, from.getString(key, "") ?: "").apply()
    from.edit().remove(key).apply()
    return this
}
