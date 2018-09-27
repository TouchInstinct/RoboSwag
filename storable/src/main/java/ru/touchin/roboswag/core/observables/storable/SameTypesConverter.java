package ru.touchin.roboswag.core.observables.storable;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Type;

/**
 * Simple safe converter that is doing nothing on conversion.
 *
 * @param <T> Same type.
 */
public class SameTypesConverter<T> implements Converter<T, T> {

    @Nullable
    @Override
    public T toStoreObject(@NonNull final Type type1, @NonNull final Type type2, @Nullable final T object) {
        return object;
    }

    @Nullable
    @Override
    public T toObject(@NonNull final Type type1, @NonNull final Type type2, @Nullable final T object) {
        return object;
    }

}
