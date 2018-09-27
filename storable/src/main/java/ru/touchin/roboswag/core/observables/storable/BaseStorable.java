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
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import ru.touchin.roboswag.core.log.LcGroup;
import ru.touchin.roboswag.core.observables.ObservableRefCountWithCacheTime;
import ru.touchin.roboswag.core.utils.ObjectUtils;
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
 * @param <TKey>          Type of key to identify object;
 * @param <TObject>       Type of actual object;
 * @param <TStoreObject>  Type of store object. Could be same as {@link TObject};
 * @param <TReturnObject> Type of actual value operating by Storable. Could be same as {@link TObject}.
 */
public abstract class BaseStorable<TKey, TObject, TStoreObject, TReturnObject> {

    public static final LcGroup STORABLE_LC_GROUP = new LcGroup("STORABLE");

    private static final long DEFAULT_CACHE_TIME_MILLIS = TimeUnit.SECONDS.toMillis(5);

    @NonNull
    private static ObserveStrategy getDefaultObserveStrategyFor(@NonNull final Type objectType, @NonNull final Type storeObjectType) {
        if (objectType instanceof Class && ObjectUtils.isSimpleClass((Class) objectType)) {
            return ObserveStrategy.CACHE_ACTUAL_VALUE;
        }
        if (objectType instanceof Class && ObjectUtils.isSimpleClass((Class) storeObjectType)) {
            return ObserveStrategy.CACHE_STORE_VALUE;
        }
        return ObserveStrategy.NO_CACHE;
    }

    @NonNull
    private final TKey key;
    @NonNull
    private final Type objectType;
    @NonNull
    private final Type storeObjectType;
    @NonNull
    private final Store<TKey, TStoreObject> store;
    @NonNull
    private final Converter<TObject, TStoreObject> converter;
    @NonNull
    private final PublishSubject<Optional<TStoreObject>> newStoreValueEvent = PublishSubject.create();
    @NonNull
    private final Observable<Optional<TStoreObject>> storeValueObservable;
    @NonNull
    private final Observable<Optional<TObject>> valueObservable;
    @NonNull
    private final Scheduler scheduler;

    public BaseStorable(@NonNull final BuilderCore<TKey, TObject, TStoreObject> builderCore) {
        this(builderCore.key, builderCore.objectType, builderCore.storeObjectType,
                builderCore.store, builderCore.converter, builderCore.observeStrategy,
                builderCore.migration, builderCore.defaultValue, builderCore.storeScheduler, builderCore.cacheTimeMillis);
    }

    @SuppressWarnings("PMD.ExcessiveParameterList")
    //ExcessiveParameterList: that's why we are using builder to create it
    private BaseStorable(@NonNull final TKey key,
                         @NonNull final Type objectType,
                         @NonNull final Type storeObjectType,
                         @NonNull final Store<TKey, TStoreObject> store,
                         @NonNull final Converter<TObject, TStoreObject> converter,
                         @Nullable final ObserveStrategy observeStrategy,
                         @Nullable final Migration<TKey> migration,
                         @Nullable final TObject defaultValue,
                         @Nullable final Scheduler storeScheduler,
                         final long cacheTimeMillis) {
        this.key = key;
        this.objectType = objectType;
        this.storeObjectType = storeObjectType;
        this.store = store;
        this.converter = converter;

        final ObserveStrategy nonNullObserveStrategy
                = observeStrategy != null ? observeStrategy : getDefaultObserveStrategyFor(objectType, storeObjectType);
        scheduler = storeScheduler != null ? storeScheduler : Schedulers.from(Executors.newSingleThreadExecutor());
        storeValueObservable
                = createStoreValueObservable(nonNullObserveStrategy, migration, defaultValue, cacheTimeMillis);
        valueObservable = createValueObservable(storeValueObservable, nonNullObserveStrategy, cacheTimeMillis);
    }

    @Nullable
    private Optional<TStoreObject> returnDefaultValueIfNull(@NonNull final Optional<TStoreObject> storeObject, @Nullable final TObject defaultValue)
            throws Converter.ConversionException {
        if (storeObject.get() != null || defaultValue == null) {
            return storeObject;
        }

        try {
            return new Optional<>(converter.toStoreObject(objectType, storeObjectType, defaultValue));
        } catch (final Converter.ConversionException exception) {
            STORABLE_LC_GROUP.w(exception, "Exception while converting default value of '%s' from '%s' from store %s",
                    key, defaultValue, store);
            throw exception;
        }
    }

    @NonNull
    private Observable<Optional<TStoreObject>> createStoreInitialLoadingObservable(@Nullable final Migration<TKey> migration) {
        final Single<Optional<TStoreObject>> loadObservable = store.loadObject(storeObjectType, key)
                .doOnError(throwable -> STORABLE_LC_GROUP.w(throwable, "Exception while trying to load value of '%s' from store %s", key, store));
        return (migration != null ? migration.migrateToLatestVersion(key).andThen(loadObservable) : loadObservable)
                .subscribeOn(scheduler)
                .observeOn(scheduler)
                .toObservable()
                .replay(1)
                .refCount()
                .take(1);
    }

    @NonNull
    private Observable<Optional<TStoreObject>> createStoreValueObservable(@NonNull final ObserveStrategy observeStrategy,
                                                                          @Nullable final Migration<TKey> migration,
                                                                          @Nullable final TObject defaultValue,
                                                                          final long cacheTimeMillis) {
        final Observable<Optional<TStoreObject>> storeInitialLoadingObservable = createStoreInitialLoadingObservable(migration);
        final Observable<Optional<TStoreObject>> result = storeInitialLoadingObservable
                .concatWith(newStoreValueEvent)
                .map(storeObject -> returnDefaultValueIfNull(storeObject, defaultValue));
        return observeStrategy == ObserveStrategy.CACHE_STORE_VALUE || observeStrategy == ObserveStrategy.CACHE_STORE_AND_ACTUAL_VALUE
                ? RxJavaPlugins.onAssembly(new ObservableRefCountWithCacheTime<>(result.replay(1), cacheTimeMillis, TimeUnit.MILLISECONDS))
                : result;
    }

    @NonNull
    private Observable<Optional<TObject>> createValueObservable(@NonNull final Observable<Optional<TStoreObject>> storeValueObservable,
                                                                @NonNull final ObserveStrategy observeStrategy,
                                                                final long cacheTimeMillis) {
        final Observable<Optional<TObject>> result = storeValueObservable
                .map(storeObject -> {
                    try {
                        return new Optional<>(converter.toObject(objectType, storeObjectType, storeObject.get()));
                    } catch (final Converter.ConversionException exception) {
                        STORABLE_LC_GROUP.w(exception, "Exception while trying to converting value of '%s' from store %s by %s",
                                key, storeObject, store, converter);
                        throw exception;
                    }
                });
        return observeStrategy == ObserveStrategy.CACHE_ACTUAL_VALUE || observeStrategy == ObserveStrategy.CACHE_STORE_AND_ACTUAL_VALUE
                ? RxJavaPlugins.onAssembly(new ObservableRefCountWithCacheTime<>(result.replay(1), cacheTimeMillis, TimeUnit.MILLISECONDS))
                : result;
    }

    /**
     * Returns key of value.
     *
     * @return Unique key.
     */
    @NonNull
    public TKey getKey() {
        return key;
    }

    /**
     * Returns type of actual object.
     *
     * @return Type of actual object.
     */
    @NonNull
    public Type getObjectType() {
        return objectType;
    }

    /**
     * Returns type of store object.
     *
     * @return Type of store object.
     */
    @NonNull
    public Type getStoreObjectType() {
        return storeObjectType;
    }

    /**
     * Returns {@link Store} where store class representation of object is storing.
     *
     * @return Store.
     */
    @NonNull
    public Store<TKey, TStoreObject> getStore() {
        return store;
    }

    /**
     * Returns {@link Converter} to convert values from store class to actual and back.
     *
     * @return Converter.
     */
    @NonNull
    public Converter<TObject, TStoreObject> getConverter() {
        return converter;
    }

    @NonNull
    private Completable internalSet(@Nullable final TObject newValue, final boolean checkForEqualityBeforeSet) {
        return (checkForEqualityBeforeSet ? storeValueObservable.firstOrError() : Single.just(new Optional<>(null)))
                .observeOn(scheduler)
                .flatMapCompletable(oldStoreValue -> {
                    final TStoreObject newStoreValue;
                    try {
                        newStoreValue = converter.toStoreObject(objectType, storeObjectType, newValue);
                    } catch (final Converter.ConversionException exception) {
                        STORABLE_LC_GROUP.w(exception, "Exception while trying to store value of '%s' from store %s by %s",
                                key, newValue, store, converter);
                        return Completable.error(exception);
                    }
                    if (checkForEqualityBeforeSet && ObjectUtils.equals(newStoreValue, oldStoreValue.get())) {
                        return Completable.complete();
                    }
                    return store.storeObject(storeObjectType, key, newStoreValue)
                            .doOnError(throwable -> STORABLE_LC_GROUP.w(throwable,
                                    "Exception while trying to store value of '%s' from store %s by %s",
                                    key, newValue, store, converter))
                            .observeOn(scheduler)
                            .andThen(Completable.fromAction(() -> {
                                newStoreValueEvent.onNext(new Optional<>(newStoreValue));
                                if (checkForEqualityBeforeSet) {
                                    STORABLE_LC_GROUP.i("Value of '%s' changed from '%s' to '%s'", key, oldStoreValue, newStoreValue);
                                } else {
                                    STORABLE_LC_GROUP.i("Value of '%s' force changed to '%s'", key, newStoreValue);
                                }
                            }));
                });
    }

    /**
     * Creates observable which is async setting value to store.
     * It is not checking if stored value equals new value.
     * In result it will be faster to not get value from store and compare but it will emit item to {@link #observe()} subscribers.
     * NOTE: It could emit ONLY completed and errors events. It is not providing onNext event!
     *
     * @param newValue Value to set;
     * @return Observable of setting process.
     */
    @NonNull
    public Completable forceSet(@Nullable final TObject newValue) {
        return internalSet(newValue, false);
    }

    /**
     * Creates observable which is async setting value to store.
     * It is checking if stored value equals new value.
     * In result it will take time to get value from store and compare
     * but it won't emit item to {@link #observe()} subscribers if stored value equals new value.
     * NOTE: It could emit ONLY completed and errors events. It is not providing onNext event!
     *
     * @param newValue Value to set;
     * @return Observable of setting process.
     */
    @NonNull
    public Completable set(@Nullable final TObject newValue) {
        return internalSet(newValue, true);
    }

    /**
     * Sets value synchronously. You should NOT use this method normally. Use {@link #set(Object)} asynchronously instead.
     *
     * @param newValue Value to set;
     */
    public void setSync(@Nullable final TObject newValue) {
        final TStoreObject newStoreValue;
        try {
            newStoreValue = converter.toStoreObject(objectType, storeObjectType, newValue);
        } catch (final Converter.ConversionException exception) {
            STORABLE_LC_GROUP.w(exception, "Exception while trying to store value of '%s' from store %s by %s",
                    key, newValue, store, converter);
            return;
        }
        store.setObject(storeObjectType, key, newStoreValue);
        newStoreValueEvent.onNext(new Optional<>(newStoreValue));
        STORABLE_LC_GROUP.i("Value of '%s' force changed to '%s'", key, newStoreValue);
    }

    @NonNull
    protected Observable<Optional<TObject>> observeOptionalValue() {
        return valueObservable;
    }

    /**
     * Returns Observable which is emitting item on subscribe and every time when someone have changed value.
     * It could emit next and error events but not completed.
     *
     * @return Returns observable of value.
     */
    @NonNull
    public abstract Observable<TReturnObject> observe();

    /**
     * Returns Observable which is emitting only one item on subscribe.
     * It could emit next and error events but not completed.
     *
     * @return Returns observable of value.
     */
    @NonNull
    public Single<TReturnObject> get() {
        return observe().firstOrError();
    }

    /**
     * Gets value synchronously. You should NOT use this method normally. Use {@link #get()} or {@link #observe()} asynchronously instead.
     *
     * @return Returns value;
     */
    @Nullable
    public TObject getSync() {
        final TStoreObject storeObject = store.getObject(storeObjectType, key);
        try {
            return converter.toObject(objectType, storeObjectType, storeObject);
        } catch (final Converter.ConversionException exception) {
            STORABLE_LC_GROUP.w(exception, "Exception while trying to converting value of '%s' from store %s by %s",
                    key, storeObject, store, converter);
            return null;
        }
    }

    /**
     * Enum that is representing strategy of observing item from store.
     */
    public enum ObserveStrategy {

        /**
         * Not caching value so on every {@link #get()} emit it will get value from {@link #getStore()} and converts it with {@link #getConverter()}.
         */
        NO_CACHE,
        /**
         * Caching only store value so on every {@link #get()} emit it will converts it with {@link #getConverter()}.
         * Do not use such strategy if store object could be big (like byte-array of file).
         */
        CACHE_STORE_VALUE,
        /**
         * Caching value so it won't spend time for getting value from {@link #getStore()} and converts it by {@link #getConverter()}.
         * But it will take time for getting value from {@link #getStore()} to set value.
         * Do not use such strategy if object could be big (like Bitmap or long string).
         * Do not use such strategy if object is mutable because multiple subscribers could then change it's state.
         */
        CACHE_ACTUAL_VALUE,
        /**
         * Caching value so it won't spend time for getting value from {@link #getStore()} and converts it by {@link #getConverter()}.
         * It won't take time or getting value from {@link #getStore()} to set value.
         * Do not use such strategy if store object could be big (like byte-array of file).
         * Do not use such strategy if object could be big (like Bitmap or long string).
         * Do not use such strategy if object is mutable because multiple subscribers could then change it's state.
         */
        CACHE_STORE_AND_ACTUAL_VALUE

    }

    /**
     * Helper class to create various builders.
     *
     * @param <TKey>         Type of key to identify object;
     * @param <TObject>      Type of actual object;
     * @param <TStoreObject> Type of store object. Could be same as {@link TObject}.
     */
    public static class BuilderCore<TKey, TObject, TStoreObject> {

        @NonNull
        protected final TKey key;
        @NonNull
        protected final Type objectType;
        @NonNull
        private final Type storeObjectType;
        @NonNull
        private final Store<TKey, TStoreObject> store;
        @NonNull
        private final Converter<TObject, TStoreObject> converter;
        @Nullable
        private ObserveStrategy observeStrategy;
        @Nullable
        private Migration<TKey> migration;
        @Nullable
        private TObject defaultValue;
        @Nullable
        private Scheduler storeScheduler;
        private long cacheTimeMillis;

        protected BuilderCore(@NonNull final TKey key,
                              @NonNull final Type objectType,
                              @NonNull final Type storeObjectType,
                              @NonNull final Store<TKey, TStoreObject> store,
                              @NonNull final Converter<TObject, TStoreObject> converter) {
            this(key, objectType, storeObjectType, store, converter, null, null, null, null, DEFAULT_CACHE_TIME_MILLIS);
        }

        protected BuilderCore(@NonNull final BuilderCore<TKey, TObject, TStoreObject> sourceBuilder) {
            this(sourceBuilder.key, sourceBuilder.objectType, sourceBuilder.storeObjectType,
                    sourceBuilder.store, sourceBuilder.converter, sourceBuilder.observeStrategy,
                    sourceBuilder.migration, sourceBuilder.defaultValue, sourceBuilder.storeScheduler, sourceBuilder.cacheTimeMillis);
        }

        @SuppressWarnings({"PMD.ExcessiveParameterList", "CPD-START"})
        //CPD: it is same code as constructor of Storable
        //ExcessiveParameterList: that's why we are using builder to create it
        private BuilderCore(@NonNull final TKey key,
                            @NonNull final Type objectType,
                            @NonNull final Type storeObjectType,
                            @NonNull final Store<TKey, TStoreObject> store,
                            @NonNull final Converter<TObject, TStoreObject> converter,
                            @Nullable final ObserveStrategy observeStrategy,
                            @Nullable final Migration<TKey> migration,
                            @Nullable final TObject defaultValue,
                            @Nullable final Scheduler storeScheduler,
                            final long cacheTimeMillis) {
            this.key = key;
            this.objectType = objectType;
            this.storeObjectType = storeObjectType;
            this.store = store;
            this.converter = converter;
            this.observeStrategy = observeStrategy;
            this.migration = migration;
            this.defaultValue = defaultValue;
            this.storeScheduler = storeScheduler;
            this.cacheTimeMillis = cacheTimeMillis;
        }

        @SuppressWarnings("CPD-END")
        protected void setStoreSchedulerInternal(@Nullable final Scheduler storeScheduler) {
            this.storeScheduler = storeScheduler;
        }

        protected void setObserveStrategyInternal(@Nullable final ObserveStrategy observeStrategy) {
            this.observeStrategy = observeStrategy;
        }

        protected void setMigrationInternal(@NonNull final Migration<TKey> migration) {
            this.migration = migration;
        }

        protected void setCacheTimeInternal(final long cacheTime, @NonNull final TimeUnit timeUnit) {
            this.cacheTimeMillis = timeUnit.toMillis(cacheTime);
        }

        @Nullable
        protected TObject getDefaultValue() {
            return defaultValue;
        }

        protected void setDefaultValueInternal(@NonNull final TObject defaultValue) {
            this.defaultValue = defaultValue;
        }

    }

}
