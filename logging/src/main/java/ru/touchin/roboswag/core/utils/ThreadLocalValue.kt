/*
 *  Copyright (c) 2019 RoboSwag (Gavriil Sitnikov, Vsevolod Ivanov)
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

package ru.touchin.roboswag.core.utils

/**
 * Thread local value with specified creator of value per thread.
 */
class ThreadLocalValue<T>(private val fabric: Fabric<T>) : ThreadLocal<T>() {

    override fun initialValue(): T = fabric.create()

    /**
     * Fabric of thread-local objects.
     *
     * @param <T> Type of objects.
    </T> */
    interface Fabric<T> {

        /**
         * Creates object.
         *
         * @return new instance of object.
         */
        fun create(): T

    }

}
