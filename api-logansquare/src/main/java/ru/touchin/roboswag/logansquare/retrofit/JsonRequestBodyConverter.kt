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

package ru.touchin.roboswag.logansquare.retrofit

import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Converter
import ru.touchin.roboswag.logansquare.ApiModel
import java.io.ByteArrayOutputStream
import java.io.IOException

abstract class JsonRequestBodyConverter<T> : Converter<T, RequestBody> {

    companion object {
        private val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")
    }

    @Throws(IOException::class)
    override fun convert(value: T): RequestBody {
        (value as? ApiModel)?.validate()
        val byteArrayOutputStream = ByteArrayOutputStream()
        writeValueToByteArray(value, byteArrayOutputStream)
        return RequestBody.create(MEDIA_TYPE, byteArrayOutputStream.toByteArray())
    }

    /**
     * Serializing value to byte stream.
     *
     * @param value                 Value to serialize;
     * @param byteArrayOutputStream Byte stream to write serialized bytes;
     * @throws IOException Throws on serialization.
     */
    @Throws(IOException::class)
    protected abstract fun writeValueToByteArray(value: T, byteArrayOutputStream: ByteArrayOutputStream)

}
