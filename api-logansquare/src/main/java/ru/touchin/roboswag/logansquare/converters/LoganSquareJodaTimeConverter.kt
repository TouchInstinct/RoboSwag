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
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import ru.touchin.roboswag.core.log.Lc
import java.io.IOException

/**
 * LoganSquare converter for joda.time.DateTime
 */
class LoganSquareJodaTimeConverter : TypeConverter<DateTime> {

    private val formatter: DateTimeFormatter?

    constructor() {
        this.formatter = null
    }

    constructor(formatter: DateTimeFormatter?) {
        this.formatter = formatter
    }

    @Throws(IOException::class)
    override fun parse(jsonParser: JsonParser): DateTime? {
        val dateString = jsonParser.valueAsString
        if (dateString == null || dateString.isEmpty()) {
            return null
        }
        try {
            return DateTime.parse(dateString)
        } catch (exception: RuntimeException) {
            Lc.assertion(exception)
        }

        return null
    }

    @Throws(IOException::class)
    override fun serialize(value: DateTime?, fieldName: String?, writeFieldNameForObject: Boolean, jsonGenerator: JsonGenerator) {
        val serializedValue = value?.toString(formatter)?.takeIf(String::isNotEmpty)
        if (fieldName != null) {
            jsonGenerator.writeStringField(fieldName, serializedValue)
        } else {
            jsonGenerator.writeString(serializedValue)
        }
    }

}
