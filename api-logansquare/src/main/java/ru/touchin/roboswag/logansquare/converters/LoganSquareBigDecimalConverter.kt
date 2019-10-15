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

import com.bluelinelabs.logansquare.typeconverters.TypeConverter
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import ru.touchin.roboswag.core.log.Lc
import java.io.IOException
import java.math.BigDecimal

/**
 * LoganSquare converter for java.math.BigDecimal
 */
// similar to LoganSquareJodaTimeConverter
class LoganSquareBigDecimalConverter : TypeConverter<BigDecimal> {

    @Throws(IOException::class)
    override fun parse(jsonParser: JsonParser): BigDecimal? {
        val dateString = jsonParser.valueAsString ?: return null
        return try {
            BigDecimal(dateString)
        } catch (exception: RuntimeException) {
            Lc.assertion(exception)
            null
        }
    }

    @Throws(IOException::class)
    override fun serialize(value: BigDecimal?, fieldName: String?, writeFieldNameForObject: Boolean, jsonGenerator: JsonGenerator) {
        if (fieldName != null) {
            jsonGenerator.writeStringField(fieldName, value?.toString())
        } else {
            jsonGenerator.writeString(value?.toString())
        }
    }

}
