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

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

/**
 * LoganSquare converter from String to Enum.
 */
class LoganSquareEnumConverter<T> @JvmOverloads constructor(
        private val enumValues: Array<T>,
        private val defaultValue: T? = null
) : StringBasedTypeConverter<T>() where T : Enum<*>, T : LoganSquareEnum {

    override fun getFromString(string: String?): T? {
        string ?: return defaultValue

        for (value in enumValues) {
            if (value.valueName == string) {
                return value
            }
        }

        if (defaultValue != null) return defaultValue

        throw ShouldNotHappenException("Enum parsing exception for value: $string")
    }

    override fun convertToString(value: T?): String? = value?.valueName

}
