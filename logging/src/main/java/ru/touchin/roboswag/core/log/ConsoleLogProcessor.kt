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

package ru.touchin.roboswag.core.log

import android.util.Log

/**
 * Simple [LogProcessor] implementation which is logging messages to console (logcat).
 */
class ConsoleLogProcessor(lclevel: LcLevel) : LogProcessor(lclevel) {

    companion object {
        private const val MAX_LOG_LENGTH = 4000
    }

    @SuppressWarnings("WrongConstant", "LogConditional")
    //WrongConstant, LogConditional: level.getPriority() is not wrong constant!
    override fun processLogMessage(
            group: LcGroup,
            level: LcLevel,
            tag: String,
            message: String,
            throwable: Throwable?
    ) {
        message.plus(throwable?.let { "\n${Log.getStackTraceString(throwable)}" }.orEmpty())
                .normalize()
                .split('\n')
                .map { msg -> msg.chunked(MAX_LOG_LENGTH) }
                .flatten()
                .forEach { msg ->
                    Log.println(level.priority, tag, msg)
                }
    }

    private fun String.normalize(): String = this
            .replace("\r\n", "\n")
            .replace("\u0000", "")

}
