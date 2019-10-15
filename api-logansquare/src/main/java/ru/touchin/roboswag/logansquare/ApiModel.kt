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

package ru.touchin.roboswag.logansquare

import androidx.annotation.CallSuper
import java.io.IOException
import java.io.Serializable
import ru.touchin.roboswag.core.log.Lc
import ru.touchin.roboswag.core.log.LcGroup

abstract class ApiModel : Serializable {

    companion object {
        private const val serialVersionUID = 1L

        /**
         * Logging group to log API validation errors.
         */
        val API_VALIDATION_LC_GROUP = LcGroup("API_VALIDATION")

        /**
         * Validates list of objects. Use it if objects in list extends [ApiModel].
         *
         * @param collection               Collection of items to check;
         * @param collectionValidationRule Rule explaining what to do if invalid items found;
         * @throws ValidationException Exception of validation.
         */
        @Throws(ValidationException::class)
        // PreserveStackTrace: it's ok - we are logging it on Lc.e()
        fun validateCollection(collection: MutableCollection<*>, collectionValidationRule: CollectionValidationRule) {
            var haveValidItem = false
            var position = 0
            val iterator = collection.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                if (item !is ApiModel) {
                    if (item != null) {
                        // let's just think that all of items are not ApiModels
                        break
                    }
                    continue
                }

                try {
                    item.validate()
                    haveValidItem = true
                } catch (exception: ValidationException) {
                    when (collectionValidationRule) {
                        CollectionValidationRule.EXCEPTION_IF_ANY_INVALID -> {
                            throw exception
                        }
                        CollectionValidationRule.EXCEPTION_IF_ALL_INVALID -> {
                            iterator.remove()
                            API_VALIDATION_LC_GROUP.e(exception, "Item %s is invalid at " + Lc.getCodePoint(null, 1), position)
                            if (!iterator.hasNext() && !haveValidItem) {
                                throw ValidationException("Whole list is invalid at " + Lc.getCodePoint(null, 1))
                            }
                        }
                        CollectionValidationRule.REMOVE_INVALID_ITEMS -> {
                            iterator.remove()
                            API_VALIDATION_LC_GROUP.e(exception, "Item %s is invalid at " + Lc.getCodePoint(null, 1), position)
                        }
                    }
                }

                position++
            }
        }

        /**
         * Validates collection on emptiness.
         *
         * @param collection Collection to check;
         * @throws ValidationException Exception of validation.
         */
        @Throws(ValidationException::class)
        protected fun validateCollectionNotEmpty(collection: Collection<*>) {
            if (collection.isEmpty()) {
                throw ValidationException("List is empty at " + Lc.getCodePoint(null, 1))
            }
        }
    }

    /**
     * Validates this object. Override it to write your own logic.
     *
     * @throws ValidationException Exception of validation.
     */
    @CallSuper
    @Throws(ValidationException::class)
    fun validate() {
        //do nothing
    }

    enum class CollectionValidationRule {
        EXCEPTION_IF_ANY_INVALID,
        EXCEPTION_IF_ALL_INVALID,
        REMOVE_INVALID_ITEMS
    }

    /**
     * Class of exceptions throws during [ApiModel] validation.
     */
    class ValidationException(reason: String) : IOException(reason)

}
