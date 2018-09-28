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

import android.app.Service;
import android.os.Binder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Gavriil Sitnikov on 03/10/2015.
 * Basic binding to {@link Service} which holds service object inside.
 */
public class ServiceBinder<TService extends Service> extends Binder {

    @NonNull
    private final TService service;

    public ServiceBinder(@NonNull final TService service) {
        super();
        this.service = service;
    }

    /**
     * Returns service which created this binder.
     *
     * @return Returns service.
     */
    @NonNull
    public TService getService() {
        return service;
    }

    @Override
    public boolean equals(@Nullable final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final ServiceBinder that = (ServiceBinder) object;

        return ObjectUtils.equals(service, that.service);
    }

    @Override
    public int hashCode() {
        return service.hashCode();
    }

}
