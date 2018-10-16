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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import ru.touchin.roboswag.core.utils.Optional;

/**
 * Created by Gavriil Sitnikov on 04/10/2015.
 * Base class allows to async access to some store.
 * Supports conversion between store and actual value. If it is not needed then use {@link SameTypesConverter}
 * Supports migration from specific version to latest by {@link Migration} object.
 * Allows to set default value which will be returned if actual value is null.
 * Allows to declare specific {@link ObserveStrategy}.
 * Also specific {@link Scheduler} could be specified to not create new scheduler per storable.
 *
 * @param <TKey>         Type of key to identify object;
 * @param <TObject>      Type of actual object;
 * @param <TStoreObject> Type of store object. Could be same as {@link TObject}.
 */
public class Storable<TKey, TObject, TStoreObject> extends BaseStorable<TKey, TObject, TStoreObject, Optional<TObject>> {

    public Storable(@NonNull final BuilderCore<TKey, TObject, TStoreObject> builderCore) {
        super(builderCore);
    }

    @NonNull
    @Override
    public Observable<Optional<TObject>> observe() {
        return observeOptionalValue();
    }

    /**
     * Helper class to build {@link Storable}.
     *
     * @param <TKey>         Type of key to identify object;
     * @param <TObject>      Type of actual object;
     * @param <TStoreObject> Type of store object. Could be same as {@link TObject}.
     */
    public static class Builder<TKey, TObject, TStoreObject> extends BuilderCore<TKey, TObject, TStoreObject> {

        public Builder(@NonNull final TKey key,
                       @NonNull final Type objectType,
                       @NonNull final Type storeObjectType,
                       @NonNull final Store<TKey, TStoreObject> store,
                       @NonNull final Converter<TObject, TStoreObject> converter) {
            super(key, objectType, storeObjectType, store, converter);
        }

        /**
         * Sets specific {@link Scheduler} to store/load/convert values on it.
         *
         * @param storeScheduler Scheduler;
         * @return Builder that allows to specify other fields.
         */
        @NonNull
        public Builder<TKey, TObject, TStoreObject> setStoreScheduler(@Nullable final Scheduler storeScheduler) {
            setStoreSchedulerInternal(storeScheduler);
            return this;
        }

        /**
         * Sets specific {@link ObserveStrategy} to cache value in memory in specific way.
         *
         * @param observeStrategy ObserveStrategy;
         * @return Builder that allows to specify other fields.
         */
        @NonNull
        public Builder<TKey, TObject, TStoreObject> setObserveStrategy(@Nullable final ObserveStrategy observeStrategy) {
            setObserveStrategyInternal(observeStrategy);
            return this;
        }

        /**
         * Sets cache time for while value that cached by {@link #setObserveStrategy(ObserveStrategy)} will be in memory after everyone unsubscribe.
         * It is important for example for cases when user switches between screens and hide/open app very fast.
         *
         * @param cacheTime Cache time value;
         * @param timeUnit  Cache time units.
         * @return Builder that allows to specify other fields.
         */
        @NonNull
        public Builder<TKey, TObject, TStoreObject> setCacheTime(final long cacheTime, @NonNull final TimeUnit timeUnit) {
            setCacheTimeInternal(cacheTime, timeUnit);
            return this;
        }

        /**
         * Sets specific {@link Migration} to migrate values from specific version to latest version.
         *
         * @param migration Migration;
         * @return Builder that allows to specify other fields.
         */
        @NonNull
        public Builder<TKey, TObject, TStoreObject> setMigration(@NonNull final Migration<TKey> migration) {
            setMigrationInternal(migration);
            return this;
        }

        /**
         * Sets value which will be returned instead of null.
         *
         * @param defaultValue Default value;
         * @return Builder that allows to specify other fields.
         */
        @NonNull
        public NonNullStorable.Builder<TKey, TObject, TStoreObject> setDefaultValue(@NonNull final TObject defaultValue) {
            return new NonNullStorable.Builder<>(this, defaultValue);
        }

        /**
         * Building {@link Storable} object.
         *
         * @return New {@link Storable}.
         */
        @NonNull
        public Storable<TKey, TObject, TStoreObject> build() {
            return new Storable<>(this);
        }

    }

}
