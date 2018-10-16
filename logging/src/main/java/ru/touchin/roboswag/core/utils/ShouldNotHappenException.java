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

import androidx.annotation.NonNull;

/**
 * Created by Gavriil Sitnikov on 13/11/2015.
 * Exception that should be threw when some unexpected code reached.
 * E.g. if some value null but it is not legal or in default case in switch if all specific cases should be processed.
 */
public class ShouldNotHappenException extends RuntimeException {

    private static final long serialVersionUID = 0;

    public ShouldNotHappenException() {
        super();
    }

    public ShouldNotHappenException(@NonNull final String detailMessage) {
        super(detailMessage);
    }

    public ShouldNotHappenException(@NonNull final String detailMessage, @NonNull final Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ShouldNotHappenException(@NonNull final Throwable throwable) {
        super(throwable);
    }

}
