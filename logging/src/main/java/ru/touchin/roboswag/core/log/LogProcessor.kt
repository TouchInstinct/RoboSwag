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

/**
 * Abstract object to intercept log messages coming from [LcGroup] and [Lc] log methods.
 */
abstract class LogProcessor(
        /**
         * Minimum logging level.
         * Any messages with lower priority won't be passed into [.processLogMessage].
         *
         * @return Minimum log level represented by [LcLevel] object.
         */
        val minLogLevel: LcLevel
) {

    /**
     * Core method to process any incoming log messages from [LcGroup] and [Lc] with level higher or equals [.getMinLogLevel].
     *
     * @param group     [LcGroup] where log message came from;
     * @param level     [LcLevel] level (priority) of message;
     * @param tag       String mark of message;
     * @param message   Message to log;
     * @param throwable Exception to log.
     */
    abstract fun processLogMessage(group: LcGroup, level: LcLevel, tag: String, message: String, throwable: Throwable?)

}
