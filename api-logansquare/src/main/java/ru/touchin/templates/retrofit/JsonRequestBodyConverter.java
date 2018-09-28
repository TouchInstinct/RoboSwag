/*
 *  Copyright (c) 2017 Touch Instinct
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

package ru.touchin.templates.retrofit;


import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;
import ru.touchin.templates.ApiModel;

/**
 * Created by Gavriil Sitnikov on 14/02/2017.
 * Object to serialize bodies of remote requests for Retrofit.
 *
 * @param <T> Type of body object.
 */
public abstract class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @NonNull
    @Override
    public RequestBody convert(@NonNull final T value) throws IOException {
        if (value instanceof ApiModel) {
            ((ApiModel) value).validate();
        }
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        writeValueToByteArray(value, byteArrayOutputStream);
        return RequestBody.create(MEDIA_TYPE, byteArrayOutputStream.toByteArray());
    }

    /**
     * Serializing value to byte stream.
     *
     * @param value                 Value to serialize;
     * @param byteArrayOutputStream Byte stream to write serialized bytes;
     * @throws IOException Throws on serialization.
     */
    protected abstract void writeValueToByteArray(@NonNull T value, @NonNull ByteArrayOutputStream byteArrayOutputStream) throws IOException;

}
