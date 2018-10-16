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

import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by Gavriil Sitnikov on 06/10/2015.
 * Object that allows to migrate some store objects from one version to another by migrators passed into constructor.
 * Migrating objects should have same types of store key.
 *
 * @param <TKey> Type of key of store objects.
 */
public class Migration<TKey> {

    public static final long DEFAULT_VERSION = -1L;

    private final long latestVersion;
    @NonNull
    private final Store<TKey, Long> versionsStore;
    @NonNull
    private final List<Migrator<TKey, ?, ?>> migrators;

    @SafeVarargs
    public Migration(@NonNull final Store<TKey, Long> versionsStore,
                     final long latestVersion,
                     @NonNull final Migrator<TKey, ?, ?>... migrators) {
        this.versionsStore = versionsStore;
        this.latestVersion = latestVersion;
        this.migrators = Arrays.asList(migrators);
    }

    @NonNull
    private Single<Long> loadCurrentVersion(@NonNull final TKey key) {
        return versionsStore.loadObject(Long.class, key)
                .map(version -> version.get() != null ? version.get() : DEFAULT_VERSION)
                .onErrorResumeNext(throwable
                        -> Single.error(new MigrationException(String.format("Can't get version of '%s' from %s", key, versionsStore), throwable)));
    }

    @NonNull
    private Single<Long> makeMigrationChain(@NonNull final TKey key, @NonNull final VersionUpdater versionUpdater) {
        Single<Long> chain = Single.fromCallable(() -> versionUpdater.initialVersion);
        for (final Migrator<TKey, ?, ?> migrator : migrators) {
            chain = chain.flatMap(updatedVersion ->
                    migrator.canMigrate(key, updatedVersion)
                            .flatMap(canMigrate -> canMigrate
                                    ? migrator.migrate(key, updatedVersion)
                                    .doOnSuccess(newVersion
                                            -> versionUpdater.updateVersion(newVersion, latestVersion, migrator))
                                    : Single.just(updatedVersion)));
        }
        return chain;
    }

    /**
     * Migrates some object by key to latest version.
     *
     * @param key Key of object to migrate.
     */
    @NonNull
    public Completable migrateToLatestVersion(@NonNull final TKey key) {
        return loadCurrentVersion(key)
                .flatMap(currentVersion -> {
                    final VersionUpdater versionUpdater = new VersionUpdater<>(key, versionsStore, currentVersion);
                    return makeMigrationChain(key, versionUpdater)
                            .doOnSuccess(lastUpdatedVersion -> {
                                if (lastUpdatedVersion < latestVersion) {
                                    throw new NextLoopMigrationException();
                                }
                                if (versionUpdater.initialVersion == versionUpdater.oldVersion) {
                                    throw new MigrationException(String.format("Version of '%s' not updated from %s",
                                            key, versionUpdater.initialVersion));
                                }
                            })
                            .retryWhen(attempts -> attempts
                                    .switchMap(throwable -> throwable instanceof NextLoopMigrationException
                                            ? Flowable.just(new Object()) : Flowable.error(throwable)));
                })
                .ignoreElement()
                .andThen(versionsStore.storeObject(Long.class, key, latestVersion))
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof MigrationException) {
                        return Completable.error(throwable);
                    }
                    return Completable.error(new MigrationException(String.format("Can't migrate '%s'", key), throwable));
                });
    }

    private static class VersionUpdater<TKey> {

        @NonNull
        private final TKey key;
        @NonNull
        private final Store versionsStore;
        private long oldVersion;
        private long initialVersion;

        public VersionUpdater(@NonNull final TKey key, @NonNull final Store versionsStore, final long initialVersion) {
            this.key = key;
            this.versionsStore = versionsStore;
            this.oldVersion = initialVersion;
            this.initialVersion = initialVersion;
        }

        public void updateVersion(final long updateVersion, final long latestVersion, @NonNull final Migrator migrator) {
            if (initialVersion > updateVersion) {
                throw new MigrationException(String.format("Version of '%s' downgraded from %s to %s [from %s by %s]",
                        key, initialVersion, updateVersion, versionsStore, migrator));
            }
            if (updateVersion > latestVersion) {
                throw new MigrationException(
                        String.format("Version of '%s' is %s and higher than latest version %s [from %s by %s]",
                                key, initialVersion, updateVersion, versionsStore, migrator));
            }
            if (updateVersion == initialVersion) {
                throw new MigrationException(String.format("Update version of '%s' equals current version '%s' [from %s by %s]",
                        key, updateVersion, versionsStore, migrator));
            }
            oldVersion = initialVersion;
            initialVersion = updateVersion;
        }

    }

    private static class NextLoopMigrationException extends Exception {
    }

    public static class MigrationException extends RuntimeException {

        public MigrationException(@NonNull final String message) {
            super(message);
        }

        public MigrationException(@NonNull final String message, @NonNull final Throwable throwable) {
            super(message, throwable);
        }

    }

}
