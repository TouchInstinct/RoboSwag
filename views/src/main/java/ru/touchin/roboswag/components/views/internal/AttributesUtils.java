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

package ru.touchin.roboswag.components.views.internal;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.StyleableRes;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;
import java.util.Collection;

import ru.touchin.roboswag.components.utils.UiUtils;
import ru.touchin.roboswag.core.log.Lc;
import ru.touchin.roboswag.core.utils.ShouldNotHappenException;

/**
 * Created by Gavriil Sitnikov on 13/06/2016.
 * Bunch of inner helper library methods to validate attributes of custom views.
 */
@Deprecated
public final class AttributesUtils {

    /**
     * Gets static field of class.
     *
     * @param resourcesClass Class to get field from;
     * @param fieldName      name of field;
     * @param <T>            Type of object that is stored in field;
     * @return Field value;
     * @throws NoSuchFieldException   Throws on reflection call;
     * @throws IllegalAccessException Throws on reflection call.
     */
    @NonNull
    @SuppressWarnings("unchecked")
    public static <T> T getField(@NonNull final Class resourcesClass, @NonNull final String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        final Field field = resourcesClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(null);
    }

    /**
     * Checks if attribute is in array or not and collecterror if attribute missed.
     *
     * @param typedArray  Array of attributes;
     * @param errors      Errors to collect into;
     * @param resourceId  Id of attribute;
     * @param required    Is parameter have to be in array OR it have not to be in;
     * @param description Description of error.
     */
    public static void checkAttribute(
            @NonNull final TypedArray typedArray,
            @NonNull final Collection<String> errors,
            @StyleableRes final int resourceId,
            final boolean required,
            @NonNull final String description
    ) {
        if ((required && typedArray.hasValue(resourceId))
                || (!required && !typedArray.hasValue(resourceId))) {
            return;
        }
        errors.add(description);
    }

    /**
     * Collects regular {@link android.widget.TextView} errors.
     *
     * @param typedArray                Array of attributes;
     * @param androidRes                Class of styleable attributes;
     * @param errors                    Errors to collect into;
     * @param lineStrategyParameterName name of line strategy parameter;
     * @throws NoSuchFieldException   Throws during getting attribute values through reflection;
     * @throws IllegalAccessException Throws during getting attribute values through reflection.
     */
    public static void checkRegularTextViewAttributes(
            @NonNull final TypedArray typedArray,
            @NonNull final Class androidRes,
            @NonNull final Collection<String> errors,
            @NonNull final String lineStrategyParameterName
    )
            throws NoSuchFieldException, IllegalAccessException {
        checkAttribute(typedArray, errors, getField(androidRes, "TextView_fontFamily"), true, "fontFamily required parameter");
        checkAttribute(typedArray, errors, getField(androidRes, "TextView_includeFontPadding"), false, "includeFontPadding forbid parameter");
        checkAttribute(typedArray, errors, getField(androidRes, "TextView_singleLine"), false,
                "remove singleLine and use " + lineStrategyParameterName);
        checkAttribute(typedArray, errors, getField(androidRes, "TextView_ellipsize"), false,
                "remove ellipsize and use " + lineStrategyParameterName);
        checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_textColor"), true,
                "textColor required parameter. If it's dynamic then use 'android:color/transparent'");
    }

    /**
     * Inner helper library method to merge errors in string and assert it.
     *
     * @param view   View with errors;
     * @param errors Errors of view.
     */
    public static void handleErrors(@NonNull final View view, @NonNull final Collection<String> errors) {
        if (!errors.isEmpty()) {
            final String exceptionText = viewError(view, TextUtils.join("\n", errors));
            Lc.cutAssertion(new ShouldNotHappenException(exceptionText));
        }
    }

    /**
     * Returns max lines attribute value for views extended from {@link android.widget.TextView}.
     *
     * @param context Context of attributes;
     * @param attrs   TextView based attributes;
     * @return Max lines value.
     */
    public static int getMaxLinesFromAttrs(@NonNull final Context context, @NonNull final AttributeSet attrs) {
        try {
            final Class androidRes = Class.forName("com.android.internal.R$styleable");
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, AttributesUtils.getField(androidRes, "TextView"));
            final int result = typedArray.getInt(AttributesUtils.getField(androidRes, "TextView_maxLines"), Integer.MAX_VALUE);
            typedArray.recycle();
            return result;
        } catch (final Exception exception) {
            return Integer.MAX_VALUE;
        }
    }

    /**
     * Creates readable view error.
     *
     * @param view      View of error;
     * @param errorText Text of error;
     * @return Readable error string.
     */
    @NonNull
    public static String viewError(@NonNull final View view, @NonNull final String errorText) {
        return "Errors for view id=" + UiUtils.OfViews.getViewIdString(view) + ":\n" + errorText;
    }

    /**
     * Returns true if input type equals number input type.
     *
     * @param inputType Input type to check;
     * @return true if input type equals number input type.
     */
    public static boolean isNumberInputType(final int inputType) {
        return inputType == InputType.TYPE_CLASS_NUMBER || inputType == InputType.TYPE_DATETIME_VARIATION_NORMAL;
    }

    private AttributesUtils() {
    }

}
