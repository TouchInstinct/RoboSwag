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

/**
 * Created by Gavriil Sitnikov on 04/10/2015.
 * Interface that is providing logic to convert value from specific type to type allowed to store in {@link Store} object and back.
 *
 * @param <TObject>      Type of original objects;
 * @param <TStoreObject> Type of objects in store.
 */
public interface Converter<TObject, TStoreObject> {

    /**
     * Converts specific object of objectType to object of storeObjectClass allowed to store.
     *
     * @param objectType      Type of object;
     * @param storeObjectType Type of store object allowed to store;
     * @param object          Object to be converted to store object;
     * @return Object that is allowed to store into specific {@link Store};
     * @throws ConversionException Exception during conversion. Usually it indicates illegal state.
     */
    @Nullable
    TStoreObject toStoreObject(@NonNull Type objectType, @NonNull Type storeObjectType, @Nullable TObject object)
            throws ConversionException;

    /**
     * Converts specific store object of storeObjectClass to object of objectType.
     *
     * @param objectType      Type of object;
     * @param storeObjectType Type of store object allowed to store;
     * @param storeObject     Object from specific {@link Store};
     * @return Object converted from store object;
     * @throws ConversionException Exception during conversion. Usually it indicates illegal state.
     */
    @Nullable
    TObject toObject(@NonNull Type objectType, @NonNull Type storeObjectType, @Nullable TStoreObject storeObject)
            throws ConversionException;

    class ConversionException extends Exception {

        public ConversionException(@NonNull final String message) {
            super(message);
        }

        public ConversionException(@NonNull final String message, @NonNull final Throwable throwable) {
            super(message, throwable);
        }

    }

}
