package com.bluelinelabs.logansquare;

import androidx.annotation.NonNull;

import java.lang.reflect.Type;

/**
 * Utility class for the {@link ru.touchin.templates.logansquare.LoganSquareJsonFactory}. This resides in LoganSquare's
 * main package in order to take advantage of the package-visible ConcreteParameterizedType class, which is essential
 * to the support of generic classes in the Retrofit converter.
 */
public final class ConverterUtils {

    @NonNull
    public static ParameterizedType parameterizedTypeOf(@NonNull final Type type) {
        return new ParameterizedType.ConcreteParameterizedType(type);
    }

    private ConverterUtils() {
    }

}
