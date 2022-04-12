package ru.touchin.network.utils

import okhttp3.Request
import retrofit2.Invocation

fun <T: Annotation> Request.getAnnotation(annotation: Class<T>) = tag(Invocation::class.java)?.method()?.getAnnotation(annotation)
