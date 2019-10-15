package com.bluelinelabs.logansquare

import java.lang.reflect.Type

fun Type.parameterizedTypeOf(): ParameterizedType<Any> = ParameterizedType.ConcreteParameterizedType(this)
