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

import okhttp3.ResponseBody
import okhttp3.internal.http2.StreamResetException
import retrofit2.Converter
import ru.touchin.roboswag.core.log.Lc
import ru.touchin.roboswag.logansquare.ApiModel
import java.io.IOException
import java.io.InterruptedIOException
import java.net.SocketException
import javax.net.ssl.SSLException

abstract class JsonResponseBodyConverter<T> : Converter<ResponseBody, T> {

    @SuppressWarnings("PMD.AvoidInstanceofChecksInCatchClause")
    //AvoidInstanceofChecksInCatchClause: we just don't need assertion on specific exceptions
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val result: T
        try {
            result = parseResponse(value)
        } catch (exception: IOException) {
            if (exception !is SocketException
                    && exception !is InterruptedIOException
                    && exception !is SSLException
                    && exception !is StreamResetException) {
                Lc.assertion(exception)
            }
            throw exception
        } finally {
            value.close()
        }

        (result as? ApiModel)?.let(::validateModel)
        (result as? Collection<*>)?.toMutableList()?.let(::validateCollection)
        (result as? Map<*, *>)?.values?.toMutableList()?.let(::validateCollection)

        return result
    }

    protected fun getValidateCollectionRule(): ApiModel.CollectionValidationRule = ApiModel.CollectionValidationRule.EXCEPTION_IF_ANY_INVALID

    /**
     * Parses response to specific object.
     *
     * @param value Response to parse;
     * @return Parsed object;
     * @throws IOException Throws during parsing.
     */
    @Throws(IOException::class)
    protected abstract fun parseResponse(value: ResponseBody): T

    @Throws(IOException::class)
    private fun validateModel(result: ApiModel) {
        try {
            result.validate()
        } catch (validationException: ApiModel.ValidationException) {
            Lc.assertion(validationException)
            throw validationException
        }
    }

    @Throws(IOException::class)
    private fun validateCollection(result: MutableCollection<*>) {
        try {
            ApiModel.validateCollection(result, getValidateCollectionRule())
        } catch (validationException: ApiModel.ValidationException) {
            Lc.assertion(validationException)
            throw validationException
        }

    }

}
