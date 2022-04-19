package ru.touchin.network.blocking

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.IllegalStateException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class BlockingRequestCallAdapter private constructor(
        private val responseType: Type
) : CallAdapter<Any, Any> {

    companion object {
        fun create() = object : CallAdapter.Factory() {
            override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
                return when {
                    getRawType(returnType) != Call::class.java -> null
                    returnType !is ParameterizedType -> throw IllegalStateException("return type must be parametrized")
                    else -> BlockingRequestCallAdapter(responseType = returnType.actualTypeArguments[0])
                }
            }
        }
    }

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<Any>): Any = BlockingCall(call)
}
