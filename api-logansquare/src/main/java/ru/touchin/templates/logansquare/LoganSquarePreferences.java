/*
 *  Copyright (c) 2016 Touch Instinct
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

package ru.touchin.templates.logansquare;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import ru.touchin.roboswag.components.utils.storables.PreferenceStore;
import ru.touchin.roboswag.core.observables.storable.Converter;
import ru.touchin.roboswag.core.observables.storable.NonNullStorable;
import ru.touchin.roboswag.core.observables.storable.Storable;
import ru.touchin.roboswag.core.utils.ShouldNotHappenException;

/**
 * Created by Gavriil Sitnikov on 26/12/2016.
 * Utility class to get {@link Storable} that is storing LoganSquare (Json) generated object into preferences.
 */
@SuppressWarnings("CPD-START")
//CPD: it is same code as in GoogleJsonPreferences
public final class LoganSquarePreferences {

    @NonNull
    public static <T> Storable<String, T, String> jsonStorable(@NonNull final String name,
                                                               @NonNull final Class<T> jsonClass,
                                                               @NonNull final SharedPreferences preferences) {
        return new Storable.Builder<String, T, String>(name, jsonClass, String.class, new PreferenceStore<>(preferences), new JsonConverter<>())
                .setObserveStrategy(Storable.ObserveStrategy.CACHE_ACTUAL_VALUE)
                .build();
    }

    @NonNull
    public static <T> NonNullStorable<String, T, String> jsonStorable(@NonNull final String name,
                                                                      @NonNull final Class<T> jsonClass,
                                                                      @NonNull final SharedPreferences preferences,
                                                                      @NonNull final T defaultValue) {
        return new Storable.Builder<String, T, String>(name, jsonClass, String.class, new PreferenceStore<>(preferences), new JsonConverter<>())
                .setObserveStrategy(Storable.ObserveStrategy.CACHE_ACTUAL_VALUE)
                .setDefaultValue(defaultValue)
                .build();
    }

    @NonNull
    public static <T> Storable<String, List<T>, String> jsonListStorable(@NonNull final String name,
                                                                         @NonNull final Class<T> jsonListItemClass,
                                                                         @NonNull final SharedPreferences preferences) {
        return new Storable.Builder<>(name, List.class, String.class, new PreferenceStore<>(preferences), new JsonListConverter<>(jsonListItemClass))
                .setObserveStrategy(Storable.ObserveStrategy.CACHE_ACTUAL_VALUE)
                .build();
    }

    @NonNull
    public static <T> NonNullStorable<String, List<T>, String> jsonListStorable(@NonNull final String name,
                                                                                @NonNull final Class<T> jsonListItemClass,
                                                                                @NonNull final SharedPreferences preferences,
                                                                                @NonNull final List<T> defaultValue) {
        return new Storable.Builder<>(name, List.class, String.class, new PreferenceStore<>(preferences), new JsonListConverter<>(jsonListItemClass))
                .setObserveStrategy(Storable.ObserveStrategy.CACHE_ACTUAL_VALUE)
                .setDefaultValue(defaultValue)
                .build();
    }

    private LoganSquarePreferences() {
    }

    public static class JsonConverter<TJsonObject> implements Converter<TJsonObject, String> {

        @Nullable
        @Override
        public String toStoreObject(@NonNull final Type jsonObjectType, @NonNull final Type stringType, @Nullable final TJsonObject object) {
            if (object == null) {
                return null;
            }
            try {
                return LoganSquare.serialize(object);
            } catch (final IOException exception) {
                throw new ShouldNotHappenException(exception);
            }
        }

        @Nullable
        @Override
        @SuppressWarnings("unchecked")
        public TJsonObject toObject(@NonNull final Type jsonObjectClass, @NonNull final Type storeObjectType, @Nullable final String storeValue) {
            if (storeValue == null) {
                return null;
            }
            try {
                return LoganSquare.parse(storeValue, (Class<TJsonObject>) jsonObjectClass);
            } catch (final IOException exception) {
                throw new ShouldNotHappenException(exception);
            }
        }

    }

    public static class JsonListConverter<T> implements Converter<List<T>, String> {

        @NonNull
        private final Class<T> itemClass;

        public JsonListConverter(@NonNull final Class<T> itemClass) {
            this.itemClass = itemClass;
        }

        @Nullable
        @Override
        @SuppressWarnings("unchecked")
        public String toStoreObject(@NonNull final Type jsonObjectType, @NonNull final Type stringType, @Nullable final List<T> object) {
            if (object == null) {
                return null;
            }
            try {
                return LoganSquare.serialize(object, itemClass);
            } catch (final IOException exception) {
                throw new ShouldNotHappenException(exception);
            }
        }

        @Nullable
        @Override
        public List<T> toObject(@NonNull final Type jsonObjectType, @NonNull final Type stringType, @Nullable final String storeValue) {
            if (storeValue == null) {
                return null;
            }
            try {
                return LoganSquare.parseList(storeValue, itemClass);
            } catch (final IOException exception) {
                throw new ShouldNotHappenException(exception);
            }
        }

    }

}
