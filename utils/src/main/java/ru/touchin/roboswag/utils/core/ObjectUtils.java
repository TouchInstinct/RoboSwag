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

package ru.touchin.roboswag.utils.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Gavriil Sitnikov on 04/10/2015.
 * Some utilities related to objects.
 */
public final class ObjectUtils {

    /**
     * Compares two objects if they are equals or not. If they are arrays then compare process same as {@link Arrays#deepEquals(Object[], Object[])}.
     *
     * @param object1 First object to compare;
     * @param object2 Second object to compare;
     * @return True if objects are equals.
     */
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    @Deprecated // Use Objects.equals() or ObjectsCompat.equals() and explicit Arrays.equals().
    //CompareObjectsWithEquals: we need to compare if it's same object
    public static boolean equals(@Nullable final Object object1, @Nullable final Object object2) {
        if (object1 == object2) {
            return true;
        }
        if (object1 == null || object2 == null) {
            return false;
        }

        final Class<?> elementType1 = object1.getClass().getComponentType();
        final Class<?> elementType2 = object2.getClass().getComponentType();

        if (!(elementType1 == null ? elementType2 == null : elementType1.equals(elementType2))) {
            return false;
        }
        if (elementType1 == null) {
            return object1.equals(object2);
        }
        return isArraysEquals(object1, object2, elementType1);
    }

    /**
     * Compares two collections if their elements are equals or not.
     *
     * @param collection1 First object to compare;
     * @param collection2 Second object to compare;
     * @return True if collections are equals.
     */
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    @Deprecated // Use Objects.equals() or ObjectsCompat.equals(), all collections have equals overriding.
    //CompareObjectsWithEquals: we need to compare if it's same object
    public static boolean isCollectionsEquals(@Nullable final Collection<?> collection1, @Nullable final Collection<?> collection2) {
        if (collection1 == collection2) {
            return true;
        }
        if (collection1 == null || collection2 == null) {
            return false;
        }
        if (collection1.size() != collection2.size()) {
            return false;
        }
        final Iterator<?> collection2Iterator = collection2.iterator();
        for (final Object item1 : collection1) {
            if (!equals(item1, collection2Iterator.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares two maps if their elements are equals or not.
     *
     * @param map1 First object to compare;
     * @param map2 Second object to compare;
     * @return True if maps are equals.
     */
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    @Deprecated // Use Objects.equals() or ObjectsCompat.equals(), all maps have equals overriding.
    //CompareObjectsWithEquals: we need to compare if it's same object
    public static boolean isMapsEquals(@Nullable final Map<?, ?> map1, @Nullable final Map<?, ?> map2) {
        return map1 == map2 || !(map1 == null || map2 == null)
                && map1.size() == map2.size()
                && map1.entrySet().containsAll(map2.entrySet())
                && map2.entrySet().containsAll(map1.entrySet());
    }

    @SuppressWarnings("PMD.AvoidUsingShortType")
    private static boolean isArraysEquals(@NonNull final Object object1, @Nullable final Object object2, @NonNull final Class<?> elementType) {
        if (object1 instanceof Object[]) {
            return Arrays.deepEquals((Object[]) object1, (Object[]) object2);
        } else if (elementType == int.class) {
            return Arrays.equals((int[]) object1, (int[]) object2);
        } else if (elementType == char.class) {
            return Arrays.equals((char[]) object1, (char[]) object2);
        } else if (elementType == boolean.class) {
            return Arrays.equals((boolean[]) object1, (boolean[]) object2);
        } else if (elementType == byte.class) {
            return Arrays.equals((byte[]) object1, (byte[]) object2);
        } else if (elementType == long.class) {
            return Arrays.equals((long[]) object1, (long[]) object2);
        } else if (elementType == float.class) {
            return Arrays.equals((float[]) object1, (float[]) object2);
        } else if (elementType == double.class) {
            return Arrays.equals((double[]) object1, (double[]) object2);
        } else {
            return Arrays.equals((short[]) object1, (short[]) object2);
        }
    }

    /**
     * Calculates hashCode() of several objects.
     *
     * @param objects Objects to combine hashCode() of;
     * @return Calculated hashCode().
     */
    @Deprecated // Use Objects.hash() or ObjectsCompat.hash()
    public static int hashCode(@Nullable final Object... objects) {
        return Arrays.hashCode(objects);
    }

    /**
     * Returns if class is simple like primitive, enum or string.
     *
     * @param objectClass Class to check if it's simple class;
     * @return True if class is simple.
     */
    public static boolean isSimpleClass(@NonNull final Class objectClass) {
        return objectClass.isPrimitive() || objectClass.getSuperclass() == Number.class
                || objectClass.isEnum() || objectClass == Boolean.class
                || objectClass == String.class || objectClass == Object.class;
    }

    /**
     * Returns true if collection is null or empty.
     *
     * @param collection Collection to check;
     * @return True if collection is null or empty.
     */
    public static boolean isNullOrEmpty(@Nullable final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Returns true if map is null or empty.
     *
     * @param map Map to check;
     * @return True if map is null or empty.
     */
    public static boolean isNullOrEmpty(@Nullable final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    private ObjectUtils() {
    }

}
