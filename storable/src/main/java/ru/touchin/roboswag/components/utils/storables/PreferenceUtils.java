/*
 *  Copyright (c) 2015 RoboSwag (Gavriil Sitnikov, Vsevolod Ivanov)
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

package ru.touchin.roboswag.components.utils.storables;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Type;

import ru.touchin.roboswag.core.observables.storable.Converter;
import ru.touchin.roboswag.core.observables.storable.SameTypesConverter;
import ru.touchin.roboswag.core.observables.storable.Storable;
import ru.touchin.roboswag.core.observables.storable.NonNullStorable;

/**
 * Created by Gavriil Sitnikov on 01/09/2016.
 * Utility class to get {@link Storable}s based on {@link SharedPreferences}.
 */
public final class PreferenceUtils {

    /**
     * Creates {@link Storable} that stores string into {@link SharedPreferences}.
     *
     * @param name        Name of preference;
     * @param preferences Preferences to store value;
     * @return {@link Storable} for string.
     */
    @NonNull
    public static Storable<String, String, String> stringStorable(@NonNull final String name, @NonNull final SharedPreferences preferences) {
        return new Storable.Builder<String, String, String>(
                name,
                String.class,
                String.class,
                new PreferenceStore<>(preferences),
                new SameTypesConverter<>()
        ).build();
    }

    /**
     * Creates {@link NonNullStorable} that stores string into {@link SharedPreferences} with default value.
     *
     * @param name         Name of preference;
     * @param preferences  Preferences to store value;
     * @param defaultValue Default value;
     * @return {@link Storable} for string.
     */
    @NonNull
    public static NonNullStorable<String, String, String> stringStorable(
            @NonNull final String name,
            @NonNull final SharedPreferences preferences,
            @NonNull final String defaultValue
    ) {
        return new Storable.Builder<String, String, String>(
                name,
                String.class,
                String.class,
                new PreferenceStore<>(preferences),
                new SameTypesConverter<>()
        ).setDefaultValue(defaultValue).build();
    }

    /**
     * Creates {@link Storable} that stores long into {@link SharedPreferences}.
     *
     * @param name        Name of preference;
     * @param preferences Preferences to store value;
     * @return {@link Storable} for long.
     */
    @NonNull
    public static Storable<String, Long, Long> longStorable(@NonNull final String name, @NonNull final SharedPreferences preferences) {
        return new Storable.Builder<String, Long, Long>(
                name,
                Long.class,
                Long.class,
                new PreferenceStore<>(preferences),
                new SameTypesConverter<>()
        ).build();
    }

    /**
     * Creates {@link NonNullStorable} that stores long into {@link SharedPreferences} with default value.
     *
     * @param name         Name of preference;
     * @param preferences  Preferences to store value;
     * @param defaultValue Default value;
     * @return {@link Storable} for long.
     */
    @NonNull
    public static NonNullStorable<String, Long, Long> longStorable(
            @NonNull final String name,
            @NonNull final SharedPreferences preferences,
            final long defaultValue
    ) {
        return new Storable.Builder<String, Long, Long>(
                name,
                Long.class,
                Long.class,
                new PreferenceStore<>(preferences),
                new SameTypesConverter<>()
        ).setDefaultValue(defaultValue).build();
    }

    /**
     * Creates {@link Storable} that stores boolean into {@link SharedPreferences}.
     *
     * @param name        Name of preference;
     * @param preferences Preferences to store value;
     * @return {@link Storable} for boolean.
     */
    @NonNull
    public static Storable<String, Boolean, Boolean> booleanStorable(@NonNull final String name, @NonNull final SharedPreferences preferences) {
        return new Storable.Builder<String, Boolean, Boolean>(
                name,
                Boolean.class,
                Boolean.class,
                new PreferenceStore<>(preferences),
                new SameTypesConverter<>()
        ).build();
    }

    /**
     * Creates {@link NonNullStorable} that stores boolean into {@link SharedPreferences} with default value.
     *
     * @param name         Name of preference;
     * @param preferences  Preferences to store value;
     * @param defaultValue Default value;
     * @return {@link Storable} for boolean.
     */
    @NonNull
    public static NonNullStorable<String, Boolean, Boolean> booleanStorable(
            @NonNull final String name,
            @NonNull final SharedPreferences preferences,
            final boolean defaultValue
    ) {
        return new Storable.Builder<String, Boolean, Boolean>(
                name,
                Boolean.class,
                Boolean.class,
                new PreferenceStore<>(preferences),
                new SameTypesConverter<>()
        ).setDefaultValue(defaultValue).build();
    }

    /**
     * Creates {@link Storable} that stores integer into {@link SharedPreferences}.
     *
     * @param name        Name of preference;
     * @param preferences Preferences to store value;
     * @return {@link Storable} for integer.
     */
    @NonNull
    public static Storable<String, Integer, Integer> integerStorable(@NonNull final String name, @NonNull final SharedPreferences preferences) {
        return new Storable.Builder<String, Integer, Integer>(
                name,
                Integer.class,
                Integer.class,
                new PreferenceStore<>(preferences),
                new SameTypesConverter<>()
        ).build();
    }

    /**
     * Creates {@link NonNullStorable} that stores integer into {@link SharedPreferences} with default value.
     *
     * @param name         Name of preference;
     * @param preferences  Preferences to store value;
     * @param defaultValue Default value;
     * @return {@link Storable} for integer.
     */
    @NonNull
    public static NonNullStorable<String, Integer, Integer> integerStorable(
            @NonNull final String name,
            @NonNull final SharedPreferences preferences,
            final int defaultValue
    ) {
        return new Storable.Builder<String, Integer, Integer>(
                name,
                Integer.class,
                Integer.class,
                new PreferenceStore<>(preferences),
                new SameTypesConverter<>()
        ).setDefaultValue(defaultValue).build();
    }

    /**
     * Creates {@link Storable} that stores float into {@link SharedPreferences}.
     *
     * @param name        Name of preference;
     * @param preferences Preferences to store value;
     * @return {@link Storable} for float.
     */
    @NonNull
    public static Storable<String, Float, Float> floatStorable(@NonNull final String name, @NonNull final SharedPreferences preferences) {
        return new Storable.Builder<String, Float, Float>(
                name,
                Float.class,
                Float.class,
                new PreferenceStore<>(preferences),
                new SameTypesConverter<>()
        ).build();
    }

    /**
     * Creates {@link NonNullStorable} that stores float into {@link SharedPreferences} with default value.
     *
     * @param name         Name of preference;
     * @param preferences  Preferences to store value;
     * @param defaultValue Default value;
     * @return {@link Storable} for float.
     */
    @NonNull
    public static NonNullStorable<String, Float, Float> floatStorable(
            @NonNull final String name,
            @NonNull final SharedPreferences preferences,
            final float defaultValue
    ) {
        return new Storable.Builder<String, Float, Float>(
                name,
                Float.class,
                Float.class,
                new PreferenceStore<>(preferences),
                new SameTypesConverter<>()
        ).setDefaultValue(defaultValue).build();
    }

    /**
     * Creates {@link Storable} that stores enum into {@link SharedPreferences}.
     *
     * @param name        Name of preference;
     * @param preferences Preferences to store value;
     * @return {@link Storable} for enum.
     */
    @NonNull
    public static <T extends Enum<T>> Storable<String, T, String> enumStorable(
            @NonNull final String name,
            @NonNull final Class<T> enumClass,
            @NonNull final SharedPreferences preferences
    ) {
        return new Storable.Builder<String, T, String>(
                name,
                enumClass,
                String.class,
                new PreferenceStore<>(preferences),
                new EnumToStringConverter<>()
        ).build();
    }

    /**
     * Creates {@link NonNullStorable} that stores enum into {@link SharedPreferences} with default value.
     *
     * @param name         Name of preference;
     * @param preferences  Preferences to store value;
     * @param defaultValue Default value;
     * @return {@link Storable} for enum.
     */
    @NonNull
    public static <T extends Enum<T>> NonNullStorable<String, T, String> enumStorable(
            @NonNull final String name,
            @NonNull final Class<T> enumClass,
            @NonNull final SharedPreferences preferences,
            @NonNull final T defaultValue
    ) {
        return new Storable.Builder<String, T, String>(
                name,
                enumClass,
                String.class,
                new PreferenceStore<>(preferences),
                new EnumToStringConverter<>()
        ).setDefaultValue(defaultValue).build();
    }

    private PreferenceUtils() {
    }

    private static class EnumToStringConverter<T extends Enum<T>> implements Converter<T, String> {

        @Nullable
        @Override
        public String toStoreObject(@NonNull final Type objectType, @NonNull final Type storeObjectType, @Nullable final T object)
                throws ConversionException {
            return object != null ? object.name() : null;
        }

        @Nullable
        @Override
        @SuppressWarnings("unchecked")
        //unchecked: we checked it before cast
        public T toObject(@NonNull final Type objectType, @NonNull final Type storeObjectType, @Nullable final String stringObject)
                throws ConversionException {
            if (!(objectType instanceof Class) || !((Class) objectType).isEnum()) {
                throw new ConversionException(String.format("Type %s is not enum class", objectType));
            }
            return stringObject != null ? Enum.valueOf((Class<T>) objectType, stringObject) : null;
        }
    }

}
