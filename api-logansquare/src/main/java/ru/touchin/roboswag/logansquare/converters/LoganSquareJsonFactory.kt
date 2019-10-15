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

import com.bluelinelabs.logansquare.LoganSquare
import com.bluelinelabs.logansquare.parameterizedTypeOf
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import ru.touchin.roboswag.logansquare.retrofit.JsonRequestBodyConverter
import ru.touchin.roboswag.logansquare.retrofit.JsonResponseBodyConverter
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.StringWriter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * LoganSquareConverter class to use with [Retrofit] to parse and generate models based on Logan Square library.
 */
class LoganSquareJsonFactory : Converter.Factory() {

    override fun responseBodyConverter(
            type: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
    ): Converter<ResponseBody, *> = LoganSquareJsonResponseBodyConverter<Any>(type)

    override fun requestBodyConverter(
            type: Type,
            parameterAnnotations: Array<Annotation>,
            methodAnnotations: Array<Annotation>,
            retrofit: Retrofit
    ): Converter<*, RequestBody> = LoganSquareRequestBodyConverter<Any>()

    override fun stringConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<*, String>? =
            if (type is Class<*> && type.superclass == Enum::class.java) {
                LoganSquareStringEnumConverter<Any>()
            } else {
                super.stringConverter(type, annotations, retrofit)
            }

    class LoganSquareJsonResponseBodyConverter<T>(private val type: Type) : JsonResponseBodyConverter<T>() {

        @Throws(IOException::class)
        override fun parseResponse(value: ResponseBody): T =
                if (type is ParameterizedType) {
                    val parameterizedType = type
                    val typeArguments = parameterizedType.actualTypeArguments
                    val firstType = typeArguments[0]

                    val rawType = parameterizedType.rawType
                    when {
                        rawType === Map::class.java -> {
                            LoganSquare.parseMap(value.byteStream(), typeArguments[1] as Class<*>) as T
                        }
                        rawType === List::class.java -> {
                            LoganSquare.parseList(value.byteStream(), firstType as Class<*>) as T
                        }
                        else -> {
                            // Generics
                            LoganSquare.parse(value.byteStream(), type.parameterizedTypeOf()) as T
                        }
                    }
                } else {
                    LoganSquare.parse(value.byteStream(), type as Class<*>) as T
                }
    }

    class LoganSquareRequestBodyConverter<T> : JsonRequestBodyConverter<T>() {

        @Throws(IOException::class)
        override fun writeValueToByteArray(value: T, byteArrayOutputStream: ByteArrayOutputStream) {
            LoganSquare.serialize(value, byteArrayOutputStream)
        }

    }

    class LoganSquareStringEnumConverter<T : Any> : Converter<T, String> {

        @Throws(IOException::class)
        override fun convert(value: T): String? {
            val writer = StringWriter()
            try {
                val generator = LoganSquare.JSON_FACTORY.createGenerator(writer)
                LoganSquare.typeConverterFor(value.javaClass).serialize(value, null, false, generator)
                generator.close()
                return writer.toString().replace("\"".toRegex(), "")
            } finally {
                writer.close()
            }
        }

    }

}
