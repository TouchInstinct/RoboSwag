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

package ru.touchin.roboswag.components.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import java.util.ArrayList;
import java.util.List;

import ru.touchin.defaults.DefaultTextWatcher;
import ru.touchin.roboswag.components.views.internal.AttributesUtils;
import ru.touchin.roboswag.core.log.Lc;

/**
 * Created by Gavriil Sitnikov on 18/07/2014.
 * EditText that supports custom typeface and forces developer to specify if this view multiline or not.
 * Also in debug mode it has common checks for popular bugs.
 */
@SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
@Deprecated
//ConstructorCallsOverridableMethod: it's ok as we need to setTypeface
public class TypefacedEditText extends AppCompatEditText {

    private boolean multiline;
    private boolean constructed;

    @Nullable
    private OnTextChangedListener onTextChangedListener;

    public TypefacedEditText(@NonNull final Context context) {
        super(context);
        initialize(context, null);
    }

    public TypefacedEditText(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public TypefacedEditText(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        initialize(context, attrs);
    }

    private void initialize(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        constructed = true;
        super.setIncludeFontPadding(false);
        initializeTextChangedListener();
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TypefacedEditText);
            final boolean multiline = typedArray.getBoolean(R.styleable.TypefacedEditText_isMultiline, false);
            if (multiline) {
                setMultiline(AttributesUtils.getMaxLinesFromAttrs(context, attrs));
            } else {
                setSingleLine();
            }
            typedArray.recycle();
            if (BuildConfig.DEBUG) {
                checkAttributes(context, attrs);
            }
        }
    }

    @Nullable
    public InputConnection onCreateInputConnection(@NonNull final EditorInfo attrs) {
        final InputConnection inputConnection = super.onCreateInputConnection(attrs);
        if (inputConnection != null && attrs.hintText == null) {
            for (ViewParent parent = getParent(); parent instanceof View; parent = parent.getParent()) {
                if (parent instanceof TextInputLayout) {
                    attrs.hintText = ((TextInputLayout) parent).getHint();
                    break;
                }
            }
        }

        return inputConnection;
    }

    private void checkAttributes(@NonNull final Context context, @NonNull final AttributeSet attrs) {
        final List<String> errors = new ArrayList<>();
        Boolean multiline = null;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TypefacedEditText);
        AttributesUtils.checkAttribute(typedArray, errors, R.styleable.TypefacedEditText_isMultiline, true,
                "isMultiline required parameter");
        if (typedArray.hasValue(R.styleable.TypefacedEditText_isMultiline)) {
            multiline = typedArray.getBoolean(R.styleable.TypefacedEditText_isMultiline, false);
        }
        typedArray.recycle();

        try {
            final Class androidRes = Class.forName("com.android.internal.R$styleable");

            typedArray = context.obtainStyledAttributes(attrs, AttributesUtils.getField(androidRes, "TextView"));
            AttributesUtils.checkRegularTextViewAttributes(typedArray, androidRes, errors, "isMultiline");
            checkEditTextSpecificAttributes(typedArray, androidRes, errors);
            if (multiline != null) {
                checkMultilineAttributes(typedArray, androidRes, errors, multiline);
            }
        } catch (final Exception exception) {
            Lc.e(exception, "Error during checking attributes");
        }
        AttributesUtils.handleErrors(this, errors);
        typedArray.recycle();
    }

    private void checkEditTextSpecificAttributes(@NonNull final TypedArray typedArray, @NonNull final Class androidRes,
                                                 @NonNull final List<String> errors)
            throws NoSuchFieldException, IllegalAccessException {
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_singleLine"), false,
                "remove singleLine and use isMultiline");
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_includeFontPadding"), false,
                "includeFontPadding forbid parameter");
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_ellipsize"), false,
                "ellipsize forbid parameter");

        if (typedArray.hasValue(AttributesUtils.getField(androidRes, "TextView_hint"))) {
            AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_textColorHint"), true,
                    "textColorHint required parameter if hint is not null");
        }

        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_textSize"), true,
                "textSize required parameter. If it's dynamic then use '0sp'");
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_inputType"), true,
                "inputType required parameter");

        final int inputType = typedArray.getInt(AttributesUtils.getField(androidRes, "TextView_inputType"), -1);
        if (AttributesUtils.isNumberInputType(inputType)) {
            errors.add("use inputType phone instead of number");
        }

        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_imeOptions"), true,
                "imeOptions required parameter");
    }

    private void checkMultilineAttributes(@NonNull final TypedArray typedArray, @NonNull final Class androidRes,
                                          @NonNull final List<String> errors, final boolean multiline)
            throws NoSuchFieldException, IllegalAccessException {
        if (multiline) {
            if (typedArray.getInt(AttributesUtils.getField(androidRes, "TextView_lines"), -1) == 1) {
                errors.add("lines should be more than 1 if isMultiline is true");
            }
            if (typedArray.getInt(AttributesUtils.getField(androidRes, "TextView_maxLines"), -1) == 1) {
                errors.add("maxLines should be more than 1 if isMultiline is true");
            }
            if (!typedArray.hasValue(AttributesUtils.getField(androidRes, "TextView_maxLines"))
                    && !typedArray.hasValue(AttributesUtils.getField(androidRes, "TextView_maxLength"))) {
                errors.add("specify maxLines or maxLength if isMultiline is true");
            }
        } else {
            AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_lines"), false,
                    "remove lines and use isMultiline");
            AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_maxLines"), false,
                    "maxLines remove and use isMultiline");
            AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_minLines"), false,
                    "minLines remove and use isMultiline");
            AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_maxLength"), true,
                    "maxLength required parameter if isMultiline is false");
        }
    }

    private void initializeTextChangedListener() {
        addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void onTextChanged(@NonNull final CharSequence inputText, final int start, final int before, final int count) {
                if (onTextChangedListener != null) {
                    onTextChangedListener.onTextChanged(inputText);
                }
            }
        });
    }

    /**
     * Sets if view supports multiline text alignment.
     *
     * @param maxLines Maximum lines to be set.
     */
    public void setMultiline(final int maxLines) {
        if (maxLines <= 1) {
            Lc.assertion("Wrong maxLines: " + maxLines);
            return;
        }
        multiline = true;
        final TransformationMethod transformationMethod = getTransformationMethod();
        super.setSingleLine(false);
        super.setMaxLines(maxLines);
        if (!(transformationMethod instanceof SingleLineTransformationMethod)) {
            setTransformationMethod(transformationMethod);
        }
    }

    @Override
    public void setSingleLine(final boolean singleLine) {
        if (singleLine) {
            setSingleLine();
        } else {
            setMultiline(Integer.MAX_VALUE);
        }
    }

    @Override
    public void setSingleLine() {
        final TransformationMethod transformationMethod = getTransformationMethod();
        super.setSingleLine(true);
        if (transformationMethod != null) {
            /*DEBUG if (!(transformationMethod instanceof SingleLineTransformationMethod)) {
                Lc.w("SingleLineTransformationMethod method ignored because of previous transformation method: " + transformationMethod);
            }*/
            setTransformationMethod(transformationMethod);
        }
        setLines(1);
        multiline = false;
    }

    @Override
    public void setLines(final int lines) {
        if (constructed && multiline && lines == 1) {
            Lc.assertion(new IllegalStateException(AttributesUtils.viewError(this, "lines = 1 is illegal if multiline is set to true")));
            return;
        }
        super.setLines(lines);
    }

    @Override
    public void setMaxLines(final int maxLines) {
        if (constructed && !multiline && maxLines > 1) {
            Lc.assertion(new IllegalStateException(AttributesUtils.viewError(this, "maxLines > 1 is illegal if multiline is set to false")));
            return;
        }
        super.setMaxLines(maxLines);
    }

    @Override
    public void setMinLines(final int minLines) {
        if (constructed && !multiline && minLines > 1) {
            Lc.assertion(new IllegalStateException(AttributesUtils.viewError(this, "minLines > 1 is illegal if multiline is set to false")));
            return;
        }
        super.setMinLines(minLines);
    }

    @Override
    public final void setIncludeFontPadding(final boolean includeFontPadding) {
        if (!constructed) {
            return;
        }
        Lc.assertion(new IllegalStateException(
                AttributesUtils.viewError(this, "Do not specify font padding as it is hard to make pixel-perfect design with such option")));
    }

    @Override
    public void setEllipsize(@NonNull final TextUtils.TruncateAt ellipsis) {
        if (!constructed) {
            return;
        }
        Lc.assertion(new IllegalStateException(AttributesUtils.viewError(this, "Do not specify ellipsize for EditText")));
    }

    @Override
    public void setInputType(final int type) {
        if (AttributesUtils.isNumberInputType(type)) {
            Lc.assertion(new IllegalStateException(AttributesUtils.viewError(this,
                    "Do not specify number InputType for EditText, use phone instead")));
            super.setInputType(InputType.TYPE_CLASS_PHONE);
            return;
        }
        super.setInputType(type);
    }

    public void setOnTextChangedListener(@Nullable final OnTextChangedListener onTextChangedListener) {
        this.onTextChangedListener = onTextChangedListener;
    }

    /**
     * Simplified variant of {@link TextWatcher}.
     */
    public interface OnTextChangedListener {

        /**
         * Calls when text have changed.
         *
         * @param text New text.
         */
        void onTextChanged(@NonNull CharSequence text);

    }

}
