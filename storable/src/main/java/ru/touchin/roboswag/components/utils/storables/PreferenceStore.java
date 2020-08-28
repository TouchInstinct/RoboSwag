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

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.touchin.roboswag.core.log.Lc;
import ru.touchin.roboswag.core.observables.storable.Store;
import ru.touchin.roboswag.utils.core.Optional;

/**
 * Created by Gavriil Sitnikov on 18/03/16.
 * Store based on {@link SharedPreferences} for {@link ru.touchin.roboswag.core.observables.storable.Storable}.
 *
 * @param <T> Type of storable. Could be Boolean, Integer, Long, Float or String.
 */
public class PreferenceStore<T> implements Store<String, T> {

    private static boolean isTypeBoolean(@NonNull final Type type) {
        return type.equals(Boolean.class) || type.equals(boolean.class);
    }

    private static boolean isTypeInteger(@NonNull final Type type) {
        return type.equals(Integer.class) || type.equals(int.class);
    }

    private static boolean isTypeFloat(@NonNull final Type type) {
        return type.equals(Float.class) || type.equals(float.class);
    }

    private static boolean isTypeLong(@NonNull final Type type) {
        return type.equals(Long.class) || type.equals(long.class);
    }

    @NonNull
    private final SharedPreferences preferences;

    public PreferenceStore(@NonNull final SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @NonNull
    @Override
    public Single<Boolean> contains(@NonNull final String key) {
        return Single.fromCallable(() -> preferences.contains(key));
    }

    @NonNull
    @Override
    public Completable storeObject(@NonNull final Type storeObjectType, @NonNull final String key, @Nullable final T storeObject) {
        return Completable.fromAction(() -> setObject(storeObjectType, key, storeObject));
    }

    @NonNull
    @Override
    public Single<Optional<T>> loadObject(@NonNull final Type storeObjectType, @NonNull final String key) {
        return Single.fromCallable(() -> new Optional<>(!preferences.contains(key) ? null : getObject(storeObjectType, key)));
    }

    @Override
    public void setObject(@NonNull final Type storeObjectType, @NonNull final String key, @Nullable final T storeObject) {
        if (storeObject == null) {
            preferences.edit().remove(key).apply();
            return;
        }
        if (isTypeBoolean(storeObjectType)) {
            preferences.edit().putBoolean(key, (Boolean) storeObject).apply();
        } else if (storeObjectType.equals(String.class)) {
            preferences.edit().putString(key, (String) storeObject).apply();
        } else if (isTypeInteger(storeObjectType)) {
            preferences.edit().putInt(key, (Integer) storeObject).apply();
        } else if (isTypeLong(storeObjectType)) {
            preferences.edit().putLong(key, (Long) storeObject).apply();
        } else if (isTypeFloat(storeObjectType)) {
            preferences.edit().putFloat(key, (Float) storeObject).apply();
        } else {
            Lc.assertion("Unsupported type of object " + storeObjectType);
        }
    }

    @Nullable
    @SuppressWarnings("unchecked") //unchecked: it is checking class in if-else statements
    @Override
    public T getObject(@NonNull final Type storeObjectType, @NonNull final String key) {
        if (isTypeBoolean(storeObjectType)) {
            return (T) ((Boolean) preferences.getBoolean(key, false));
        } else if (storeObjectType.equals(String.class)) {
            return (T) (preferences.getString(key, null));
        } else if (isTypeInteger(storeObjectType)) {
            return (T) ((Integer) preferences.getInt(key, 0));
        } else if (isTypeLong(storeObjectType)) {
            return (T) ((Long) preferences.getLong(key, 0L));
        } else if (isTypeFloat(storeObjectType)) {
            return (T) ((Float) preferences.getFloat(key, 0f));
        } else {
            Lc.assertion("Unsupported type of object " + storeObjectType);
            return null;
        }
    }

}
