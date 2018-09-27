/*
 *  Copyright (c) 2017 RoboSwag (Gavriil Sitnikov, Vsevolod Ivanov)
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

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by Gavriil Sitnikov on 16/04/2017.
 * Holds nullable objects inside. It is needed to implement RxJava2 non-null emitting logic.
 *
 * @param <T> Type of object.
 */
public class Optional<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Nullable
    private final T value;

    public Optional(@Nullable final T value) {
        this.value = value;
    }

    /**
     * Returns holding nullable object.
     *
     * @return Holding object.
     */
    @Nullable
    public T get() {
        return value;
    }

    @Override
    public boolean equals(@Nullable final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final Optional<?> that = (Optional<?>) object;
        return ObjectUtils.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

}
