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
import android.util.Log;

/**
 * Created by Gavriil Sitnikov on 13/11/2015.
 * Simple {@link LogProcessor} implementation which is logging messages to console (logcat).
 */
public class ConsoleLogProcessor extends LogProcessor {

    private static final int MAX_LOG_LENGTH = 4000;

    public ConsoleLogProcessor(@NonNull final LcLevel lclevel) {
        super(lclevel);
    }

    @NonNull
    private String normalize(@NonNull final String message) {
        return message.replace("\r\n", "\n").replace("\0", "");
    }

    @Override
    @SuppressWarnings({"WrongConstant", "LogConditional"})
    //WrongConstant, LogConditional: level.getPriority() is not wrong constant!
    public void processLogMessage(@NonNull final LcGroup group, @NonNull final LcLevel level,
                                  @NonNull final String tag, @NonNull final String message, @Nullable final Throwable throwable) {
        final String messageToLog = normalize(message + (throwable != null ? '\n' + Log.getStackTraceString(throwable) : ""));
        final int length = messageToLog.length();
        for (int i = 0; i < length; i++) {
            int newline = messageToLog.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                final int end = Math.min(newline, i + MAX_LOG_LENGTH);
                Log.println(level.getPriority(), tag, messageToLog.substring(i, end));
                i = end;
            }
            while (i < newline);
        }

    }

}