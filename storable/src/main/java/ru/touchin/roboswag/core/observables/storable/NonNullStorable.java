/*
 *  Copyright (c) 2016 RoboSwag (Gavriil Sitnikov, Vsevolod Ivanov)
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

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import ru.touchin.roboswag.core.utils.ShouldNotHappenException;

/**
 * Created by Gavriil Sitnikov on 04/10/2015.
 * {@link Storable} that should return not null value on get.
 * If this rule is violated then it will throw {@link ShouldNotHappenException}.
 *
 * @param <TKey>         Type of key to identify object;
 * @param <TObject>      Type of actual object;
 * @param <TStoreObject> Type of store object. Could be same as {@link TObject}.
 */
public class NonNullStorable<TKey, TObject, TStoreObject> extends BaseStorable<TKey, TObject, TStoreObject, TObject> {

    public NonNullStorable(@NonNull final Builder<TKey, TObject, TStoreObject> builderCore) {
        super(builderCore);
    }

    @NonNull
    @Override
    public Observable<TObject> observe() {
        return observeOptionalValue()
                .map(optional -> {
                    if (optional.get() == null) {
                        throw new ShouldNotHappenException();
                    }
                    return optional.get();
                });
    }

    /**
     * Created by Gavriil Sitnikov on 15/05/2016.
     * Builder that is already contains not null default value.
     *
     * @param <TKey>         Type of key to identify object;
     * @param <TObject>      Type of actual object;
     * @param <TStoreObject> Type of store object. Could be same as {@link TObject}.
     */
    // CPD-OFF
    // CPD: it is same code as Builder of Storable because it's methods returning this and can't be inherited
    public static class Builder<TKey, TObject, TStoreObject> extends BuilderCore<TKey, TObject, TStoreObject> {

        public Builder(@NonNull final Storable.Builder<TKey, TObject, TStoreObject> sourceBuilder,
                       @NonNull final TObject defaultValue) {
            super(sourceBuilder);
            if (defaultValue == null) {
                throw new ShouldNotHappenException();
            }
            setDefaultValueInternal(defaultValue);
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
         * Sets cache time for while value that cached by {@link #setObserveStrategy(ObserveStrategy)}
         * will be in memory after everyone unsubscribe.
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
         * Building {@link NonNullStorable} object.
         *
         * @return New {@link NonNullStorable}.
         */
        @NonNull
        // CPD-ON

        public NonNullStorable<TKey, TObject, TStoreObject> build() {
            if (getDefaultValue() == null) {
                throw new ShouldNotHappenException();
            }
            return new NonNullStorable<>(this);
        }

    }
}
