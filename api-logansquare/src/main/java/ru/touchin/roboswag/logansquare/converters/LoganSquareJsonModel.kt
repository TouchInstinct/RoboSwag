/*
 *  Copyright (c) 2019 Touch Instinct
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

package ru.touchin.roboswag.logansquare.converters

import ru.touchin.roboswag.core.log.Lc
import ru.touchin.roboswag.logansquare.ApiModel

/**
 * Just model from getting from API via LoganSquare.
 */
abstract class LoganSquareJsonModel : ApiModel() {

    companion object {

        /**
         * Throws exception if object is missed or null.
         *
         * @param object Value of field to check;
         * @throws ValidationException Exception of validation.
         */
        @Throws(ValidationException::class)
        protected fun validateNotNull(value: Any?) {
            value ?: throw ValidationException("Not nullable object is null or missed at " + Lc.getCodePoint(null, 1))
        }
    }

}
