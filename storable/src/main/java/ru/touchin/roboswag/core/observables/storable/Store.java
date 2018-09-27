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

package ru.touchin.roboswag.core.observables.storable;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Type;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.touchin.roboswag.core.utils.Optional;

/**
 * Created by Gavriil Sitnikov on 04/10/2015.
 * Interface that is providing access to abstract object which can store (e.g. in file, database, remote store)
 * some type of objects (e.g. String, byte[], Integer) by key.
 *
 * @param <TKey>         Type of keys for values;
 * @param <TStoreObject> Type of values stored in store.
 */
public interface Store<TKey, TStoreObject> {

    /**
     * Returns if store contains specific key related to some value.
     *
     * @param key Key which is finding in store;
     * @return True if key have found in store.
     */
    @NonNull
    Single<Boolean> contains(@NonNull TKey key);

    /**
     * Stores object to store with related key.
     *
     * @param storeObjectType Type of object to store;
     * @param key             Key related to object;
     * @param storeObject     Object to store;
     */
    @NonNull
    Completable storeObject(@NonNull Type storeObjectType, @NonNull TKey key, @Nullable TStoreObject storeObject);

    /**
     * Loads object from store by key.
     *
     * @param storeObjectType Type of object to store;
     * @param key             Key related to object;
     * @return Object from store found by key;
     */
    @NonNull
    Single<Optional<TStoreObject>> loadObject(@NonNull Type storeObjectType, @NonNull TKey key);

    /**
     * Stores object to store with related key.
     *
     * @param storeObjectType Type of object to store;
     * @param key             Key related to object;
     * @param storeObject     Object to store;
     */
    void setObject(@NonNull Type storeObjectType, @NonNull TKey key, @Nullable TStoreObject storeObject);

    /**
     * Gets object from store by key.
     *
     * @param storeObjectType Type of object to store;
     * @param key             Key related to object;
     * @return Object from store found by key;
     */
    @Nullable
    TStoreObject getObject(@NonNull Type storeObjectType, @NonNull TKey key);

}
