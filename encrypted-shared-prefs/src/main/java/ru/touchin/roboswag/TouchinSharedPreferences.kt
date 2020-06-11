package ru.touchin.roboswag

import android.content.Context
import android.content.SharedPreferences
import ru.touchin.roboswag.TouchinSharedPreferencesCryptoUtils.Companion.ENCRYPT_BASE64_STRING_LENGTH
import ru.touchin.roboswag.TouchinSharedPreferencesCryptoUtils.Companion.ENCRYPT_BLOCK_SIZE

class TouchinSharedPreferences(name: String, context: Context, val isEncryption: Boolean = false) : SharedPreferences {

    private val currentPreferences: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    private val cryptoUtils = TouchinSharedPreferencesCryptoUtils(context)

    override fun contains(key: String?) = currentPreferences.contains(key)

    override fun getBoolean(key: String?, defaultValue: Boolean) = get(key, defaultValue)

    override fun unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        currentPreferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    override fun getInt(key: String?, defaultValue: Int) = get(key, defaultValue)

    override fun getAll(): MutableMap<String, String> {
        return if (isEncryption) {
            currentPreferences.all.mapValues { it.value.toString().decrypt() }.toMutableMap()
        } else {
            currentPreferences.all.mapValues { it.value.toString() }.toMutableMap()
        }
    }

    override fun edit() = TouchinEditor()

    override fun getLong(key: String?, defaultValue: Long) = get(key, defaultValue)

    override fun getFloat(key: String?, defaultValue: Float) = get(key, defaultValue)

    override fun getString(key: String?, defaultValue: String?): String = get(key, defaultValue ?: "")

    override fun getStringSet(key: String?, set: MutableSet<String>?): MutableSet<String>? {
        return if (isEncryption) {
            val value = currentPreferences.getStringSet(key, set)
            if (value == set) {
                set
            } else {
                value?.map { it.decrypt() }?.toMutableSet()
            }
        } else {
            currentPreferences.getStringSet(key, set)
        }
    }

    override fun registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        currentPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    private fun <T> get(key: String?, defaultValue: T): T {
        if (!currentPreferences.contains(key)) return defaultValue
        val value = currentPreferences.getString(key, "")
        var resultValue = ""
        value?.let {
            var currentValue = ""
            var pos = 0
            while (pos < value.length) {
                currentValue += value[pos]
                pos++
                if (currentValue.length == ENCRYPT_BASE64_STRING_LENGTH) {
                    resultValue += currentValue.decrypt()
                    currentValue = ""
                }
            }
            if (currentValue.isNotEmpty()) {
                resultValue += currentValue.decrypt()
            }
        }
        return when (defaultValue) {
            is Boolean -> resultValue.toBoolean() as? T
            is Long -> resultValue.toLong() as? T
            is String -> resultValue as? T
            is Int -> resultValue.toInt() as? T
            is Float -> resultValue.toFloat() as? T
            else -> resultValue as? T
        } ?: defaultValue
    }

    private fun String.decrypt() = if (isEncryption) cryptoUtils.decrypt(this) else this

    inner class TouchinEditor : SharedPreferences.Editor {

        override fun clear() = currentPreferences.edit().clear()

        override fun putLong(key: String?, value: Long) = put(key, value)

        override fun putInt(key: String?, value: Int) = put(key, value)

        override fun remove(key: String?) = currentPreferences.edit().remove(key)

        override fun putBoolean(key: String?, value: Boolean) = put(key, value)

        override fun putStringSet(key: String?, value: MutableSet<String>?): SharedPreferences.Editor {
            return if (isEncryption) {
                currentPreferences.edit().putStringSet(key, value?.map { it.encrypt() }?.toMutableSet())
            } else {
                currentPreferences.edit().putStringSet(key, value)
            }
        }

        override fun commit() = currentPreferences.edit().commit()

        override fun putFloat(key: String?, value: Float) = put(key, value)

        override fun apply() = currentPreferences.edit().apply()

        override fun putString(key: String?, value: String?) = put(key, value)

        private fun <T> put(key: String?, value: T): SharedPreferences.Editor {
            val value = value?.toString()
            var resultValue = ""
            value?.let {
                var currentValue = ""
                var pos = 0
                while (pos < value.length) {
                    if (currentValue.length == ENCRYPT_BLOCK_SIZE) {
                        resultValue += currentValue.encrypt()
                        currentValue = ""
                    }
                    currentValue += value[pos]
                    pos++
                }
                if (currentValue.isNotEmpty()) {
                    resultValue += currentValue.encrypt()
                }
            }
            return currentPreferences.edit().putString(key, resultValue)
        }

        private fun String.encrypt() = if (isEncryption) cryptoUtils.encrypt(this) else this

    }

}
