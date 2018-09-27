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

package ru.touchin.roboswag.core.utils;

import android.support.annotation.NonNull;

/**
 * Created by Gavriil Sitnikov on 13/11/2015.
 * Thread local value with specified creator of value per thread.
 */
public class ThreadLocalValue<T> extends ThreadLocal<T> {

    @NonNull
    private final Fabric<T> fabric;

    public ThreadLocalValue(@NonNull final Fabric<T> fabric) {
        super();
        this.fabric = fabric;
    }

    @NonNull
    @Override
    protected T initialValue() {
        return fabric.create();
    }

    /**
     * Fabric of thread-local objects.
     *
     * @param <T> Type of objects.
     */
    public interface Fabric<T> {

        /**
         * Creates object.
         *
         * @return new instance of object.
         */
        @NonNull
        T create();

    }

}
