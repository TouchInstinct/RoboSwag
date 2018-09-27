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

package ru.touchin.roboswag.core.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Gavriil Sitnikov on 13/11/2015.
 * Abstract object to intercept log messages coming from {@link LcGroup} and {@link Lc} log methods.
 */
public abstract class LogProcessor {

    @NonNull
    private final LcLevel minLogLevel;

    public LogProcessor(@NonNull final LcLevel minLogLevel) {
        this.minLogLevel = minLogLevel;
    }

    /**
     * Minimum logging level.
     * Any messages with lower priority won't be passed into {@link #processLogMessage(LcGroup, LcLevel, String, String, Throwable)}.
     *
     * @return Minimum log level represented by {@link LcLevel} object.
     */
    @NonNull
    public LcLevel getMinLogLevel() {
        return minLogLevel;
    }

    /**
     * Core method to process any incoming log messages from {@link LcGroup} and {@link Lc} with level higher or equals {@link #getMinLogLevel()}.
     *
     * @param group     {@link LcGroup} where log message came from;
     * @param level     {@link LcLevel} level (priority) of message;
     * @param tag       String mark of message;
     * @param message   Message to log;
     * @param throwable Exception to log.
     */
    public abstract void processLogMessage(@NonNull final LcGroup group, @NonNull final LcLevel level,
                                           @NonNull final String tag, @NonNull final String message, @Nullable final Throwable throwable);

}
