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

package ru.touchin.templates;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import ru.touchin.roboswag.core.log.Lc;
import ru.touchin.roboswag.core.log.LcGroup;

/**
 * Created by Gavriil Sitnikov on 11/08/2016.
 * Just model from getting from API.
 */
public abstract class ApiModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Validates list of objects. Use it if objects in list extends {@link ApiModel}.
     *
     * @param collection               Collection of items to check;
     * @param collectionValidationRule Rule explaining what to do if invalid items found;
     * @throws ValidationException Exception of validation.
     */
    @SuppressWarnings({"PMD.PreserveStackTrace", "PMD.CyclomaticComplexity"})
    // PreserveStackTrace: it's ok - we are logging it on Lc.e()
    public static void validateCollection(@NonNull final Collection collection, @NonNull final CollectionValidationRule collectionValidationRule)
            throws ValidationException {
        boolean haveValidItem = false;
        int position = 0;
        final Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            final Object item = iterator.next();
            if (!(item instanceof ApiModel)) {
                if (item != null) {
                    // let's just think that all of items are not ApiModels
                    break;
                }
                continue;
            }

            try {
                ((ApiModel) item).validate();
                haveValidItem = true;
            } catch (final ValidationException exception) {
                switch (collectionValidationRule) {
                    case EXCEPTION_IF_ANY_INVALID:
                        throw exception;
                    case EXCEPTION_IF_ALL_INVALID:
                        iterator.remove();
                        LcGroup.API_VALIDATION.e(exception, "Item %s is invalid at " + Lc.getCodePoint(null, 1), position);
                        if (!iterator.hasNext() && !haveValidItem) {
                            throw new ValidationException("Whole list is invalid at " + Lc.getCodePoint(null, 1));
                        }
                        break;
                    case REMOVE_INVALID_ITEMS:
                        iterator.remove();
                        LcGroup.API_VALIDATION.e(exception, "Item %s is invalid at " + Lc.getCodePoint(null, 1), position);
                        break;
                    default:
                        Lc.assertion("Unexpected rule " + collectionValidationRule);
                        break;
                }
            }
            position++;
        }
    }

    /**
     * Validates collection on emptiness.
     *
     * @param collection Collection to check;
     * @throws ValidationException Exception of validation.
     */
    protected static void validateCollectionNotEmpty(@NonNull final Collection collection)
            throws ValidationException {
        if (collection.isEmpty()) {
            throw new ValidationException("List is empty at " + Lc.getCodePoint(null, 1));
        }
    }

    /**
     * Validates this object. Override it to write your own logic.
     *
     * @throws ValidationException Exception of validation.
     */
    @CallSuper
    public void validate() throws ValidationException {
        //do nothing
    }

    public enum CollectionValidationRule {
        EXCEPTION_IF_ANY_INVALID,
        EXCEPTION_IF_ALL_INVALID,
        REMOVE_INVALID_ITEMS,
    }

    /**
     * Class of exceptions throws during {@link ApiModel} validation.
     */
    public static class ValidationException extends IOException {

        public ValidationException(@NonNull final String reason) {
            super(reason);
        }

    }

}
