/*
 *  Copyright (c) 2016 Touch Instinct
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

package ru.touchin.templates.logansquare;

import androidx.annotation.Nullable;

import ru.touchin.roboswag.core.log.Lc;
import ru.touchin.templates.ApiModel;

/**
 * Created by Gavriil Sitnikov.
 * Just model from getting from API via LoganSquare.
 */
public abstract class LoganSquareJsonModel extends ApiModel {

    /**
     * Throws exception if object is missed or null.
     *
     * @param object Value of field to check;
     * @throws ValidationException Exception of validation.
     */
    protected static void validateNotNull(@Nullable final Object object) throws ValidationException {
        if (object == null) {
            throw new ValidationException("Not nullable object is null or missed at " + Lc.getCodePoint(null, 1));
        }
    }

}
