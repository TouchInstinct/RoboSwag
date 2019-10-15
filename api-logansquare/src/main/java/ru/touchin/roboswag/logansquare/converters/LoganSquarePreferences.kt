/*
 *  Copyright (c) 2019 Touch Instinct
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

package ru.touchin.roboswag.logansquare.converters

import android.content.SharedPreferences

import com.bluelinelabs.logansquare.LoganSquare

import java.io.IOException
import java.lang.reflect.Type
import ru.touchin.roboswag.components.utils.storables.PreferenceStore
import ru.touchin.roboswag.core.observables.storable.BaseStorable
import ru.touchin.roboswag.core.observables.storable.Converter
import ru.touchin.roboswag.core.observables.storable.NonNullStorable
import ru.touchin.roboswag.core.observables.storable.Storable
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

/**
 * Utility class to get [Storable] that is storing LoganSquare (Json) generated object into preferences.
 */
//CPD: it is same code as in GoogleJsonPreferences
object LoganSquarePreferences {

    fun <T> jsonStorable(
            name: String,
            jsonClass: Class<T>,
            preferences: SharedPreferences
    ): Storable<String, T, String> = Storable.Builder(name, jsonClass, String::class.java, PreferenceStore(preferences), JsonConverter<T>())
            .setObserveStrategy(BaseStorable.ObserveStrategy.CACHE_ACTUAL_VALUE)
            .build()

    fun <T> jsonStorable(
            name: String,
            jsonClass: Class<T>,
            preferences: SharedPreferences,
            defaultValue: T
    ): NonNullStorable<String, T, String> = Storable.Builder(name, jsonClass, String::class.java, PreferenceStore(preferences), JsonConverter<T>())
            .setObserveStrategy(BaseStorable.ObserveStrategy.CACHE_ACTUAL_VALUE)
            .setDefaultValue(defaultValue)
            .build()

    fun <T> jsonListStorable(
            name: String,
            jsonListItemClass: Class<T>,
            preferences: SharedPreferences
    ): Storable<String, List<T>, String> =
            Storable.Builder(name, List::class.java, String::class.java, PreferenceStore(preferences), JsonListConverter(jsonListItemClass))
            .setObserveStrategy(BaseStorable.ObserveStrategy.CACHE_ACTUAL_VALUE)
            .build()

    fun <T> jsonListStorable(
            name: String,
            jsonListItemClass: Class<T>,
            preferences: SharedPreferences,
            defaultValue: List<T>
    ): NonNullStorable<String, List<T>, String> =
            Storable.Builder(name, List::class.java, String::class.java, PreferenceStore(preferences), JsonListConverter(jsonListItemClass))
            .setObserveStrategy(BaseStorable.ObserveStrategy.CACHE_ACTUAL_VALUE)
            .setDefaultValue(defaultValue)
            .build()

    class JsonConverter<TJsonObject> : Converter<TJsonObject, String> {

        override fun toStoreObject(jsonObjectType: Type, stringType: Type, `object`: TJsonObject?): String? {
            `object` ?: return null
            return try {
                LoganSquare.serialize(`object`)
            } catch (exception: IOException) {
                throw ShouldNotHappenException(exception)
            }
        }

        override fun toObject(jsonObjectClass: Type, storeObjectType: Type, storeValue: String?): TJsonObject? {
            storeValue ?: return null
            return try {
                LoganSquare.parse(storeValue, jsonObjectClass as Class<TJsonObject>)
            } catch (exception: IOException) {
                throw ShouldNotHappenException(exception)
            }
        }

    }

    class JsonListConverter<T>(private val itemClass: Class<T>) : Converter<List<T>, String> {

        override fun toStoreObject(jsonObjectType: Type, stringType: Type, `object`: List<T>?): String? {
            `object` ?: return null
            return try {
                LoganSquare.serialize(`object`, itemClass)
            } catch (exception: IOException) {
                throw ShouldNotHappenException(exception)
            }
        }

        override fun toObject(jsonObjectType: Type, stringType: Type, storeValue: String?): List<T>? {
            storeValue ?: return null
            return try {
                LoganSquare.parseList(storeValue, itemClass)
            } catch (exception: IOException) {
                throw ShouldNotHappenException(exception)
            }
        }

    }

}
