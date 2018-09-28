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

import androidx.annotation.NonNull;
import android.util.Log;

/**
 * Created by Gavriil Sitnikov on 14/05/2016.
 * Level of log message.
 */
public enum LcLevel {

    VERBOSE(Log.VERBOSE),
    DEBUG(Log.DEBUG),
    INFO(Log.INFO),
    WARN(Log.WARN),
    ERROR(Log.ERROR),
    ASSERT(Log.ASSERT);

    private final int priority;

    LcLevel(final int priority) {
        this.priority = priority;
    }

    /**
     * Standard {@link Log} integer value of level represents priority of message.
     *
     * @return Integer level.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Compares priorities of LcLevels and returns if current is less than another.
     *
     * @param logLevel {@link LcLevel} to compare priority with;
     * @return True if current level priority less than level passed as parameter.
     */
    public boolean lessThan(@NonNull final LcLevel logLevel) {
        return this.priority < logLevel.priority;
    }

}
