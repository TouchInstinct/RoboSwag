/*
 *  Copyright (c) 2016 Touch Instinct
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


package ru.touchin.templates.logansquare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.bluelinelabs.logansquare.typeconverters.TypeConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

import ru.touchin.roboswag.core.log.Lc;

/**
 * LoganSquare converter for joda.time.DateTime
 */
public class LoganSquareJodaTimeConverter implements TypeConverter<DateTime> {

    @Nullable
    private final DateTimeFormatter formatter;

    public LoganSquareJodaTimeConverter() {
        this.formatter = null;
    }

    public LoganSquareJodaTimeConverter(@Nullable final DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Nullable
    @Override
    public DateTime parse(@NonNull final JsonParser jsonParser) throws IOException {
        final String dateString = jsonParser.getValueAsString();
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try {
            return DateTime.parse(dateString);
        } catch (final RuntimeException exception) {
            Lc.assertion(exception);
        }
        return null;
    }

    @Override
    public void serialize(
            @Nullable final DateTime object,
            @Nullable final String fieldName,
            final boolean writeFieldNameForObject,
            @NonNull final JsonGenerator jsonGenerator
    ) throws IOException {
        final String serializedValue = object != null ? object.toString(formatter) : null;
        if (fieldName != null) {
            jsonGenerator.writeStringField(fieldName, !TextUtils.isEmpty(serializedValue) ? serializedValue : null);
        } else {
            jsonGenerator.writeString(!TextUtils.isEmpty(serializedValue) ? serializedValue : null);
        }
    }

}
