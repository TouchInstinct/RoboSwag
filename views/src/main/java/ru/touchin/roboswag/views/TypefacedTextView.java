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

package ru.touchin.roboswag.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;

import ru.touchin.roboswag.components.utils.UiUtils;
import ru.touchin.roboswag.views.BuildConfig;
import ru.touchin.roboswag.views.R;
import ru.touchin.roboswag.views.internal.AttributesUtils;
import ru.touchin.roboswag.core.log.Lc;

/**
 * Created by Gavriil Sitnikov on 18/07/2014.
 * TextView that supports custom typeface and forces developer to specify {@link LineStrategy}.
 * Also in debug mode it has common checks for popular bugs.
 */
@SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
@Deprecated
//ConstructorCallsOverridableMethod: it's ok as we need to setTypeface
public class TypefacedTextView extends AppCompatTextView {

    private static final int SIZE_THRESHOLD = 10000;

    private static final int UNSPECIFIED_MEASURE_SPEC = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
    private static final int START_SCALABLE_DIFFERENCE = 4;

    private boolean constructed;
    @NonNull
    private LineStrategy lineStrategy = LineStrategy.SINGLE_LINE_ELLIPSIZE;

    public TypefacedTextView(@NonNull final Context context) {
        super(context);
        initialize(context, null);
    }

    public TypefacedTextView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public TypefacedTextView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        initialize(context, attrs);
    }

    private void initialize(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        constructed = true;
        super.setIncludeFontPadding(false);
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TypefacedTextView);
            final LineStrategy lineStrategy = LineStrategy
                    .byResIndex(typedArray.getInt(R.styleable.TypefacedTextView_lineStrategy, LineStrategy.MULTILINE_ELLIPSIZE.ordinal()));
            if (lineStrategy.multiline) {
                setLineStrategy(lineStrategy, AttributesUtils.getMaxLinesFromAttrs(context, attrs));
            } else {
                setLineStrategy(lineStrategy);
            }
            typedArray.recycle();
            if (BuildConfig.DEBUG) {
                checkAttributes(context, attrs);
            }
        }
    }

    private void checkAttributes(@NonNull final Context context, @NonNull final AttributeSet attrs) {
        final List<String> errors = new ArrayList<>();
        LineStrategy lineStrategy = null;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TypefacedTextView);
        AttributesUtils.checkAttribute(typedArray, errors, R.styleable.TypefacedTextView_lineStrategy, true,
                "lineStrategy required parameter");
        if (typedArray.hasValue(R.styleable.TypefacedTextView_lineStrategy)) {
            lineStrategy = LineStrategy.byResIndex(typedArray.getInt(R.styleable.TypefacedTextView_lineStrategy, -1));
        }
        typedArray.recycle();

        try {
            final Class androidRes = Class.forName("com.android.internal.R$styleable");

            typedArray = context.obtainStyledAttributes(attrs, AttributesUtils.getField(androidRes, "TextView"));
            AttributesUtils.checkRegularTextViewAttributes(typedArray, androidRes, errors, "lineStrategy");
            checkTextViewSpecificAttributes(typedArray, androidRes, errors);

            if (lineStrategy != null) {
                checkLineStrategyAttributes(typedArray, androidRes, errors, lineStrategy);
            }
        } catch (final Exception exception) {
            Lc.e(exception, "Error during checking attributes");
        }
        AttributesUtils.handleErrors(this, errors);
        typedArray.recycle();
    }

    private void checkTextViewSpecificAttributes(@NonNull final TypedArray typedArray, @NonNull final Class androidRes,
                                                 @NonNull final List<String> errors)
            throws NoSuchFieldException, IllegalAccessException {

        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_phoneNumber"), false,
                "phoneNumber forbid parameter");
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_password"), false,
                "password forbid parameter");
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_numeric"), false,
                "numeric forbid parameter");
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_inputType"), false,
                "inputType forbid parameter");
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_imeOptions"), false,
                "imeOptions forbid parameter");
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_imeActionId"), false,
                "imeActionId forbid parameter");
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_imeActionLabel"), false,
                "imeActionLabel forbid parameter");
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_hint"), false,
                "hint forbid parameter");
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_editable"), false,
                "editable forbid parameter");
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_digits"), false,
                "digits forbid parameter");
        AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_cursorVisible"), false,
                "cursorVisible forbid parameter");
    }

    private void checkLineStrategyAttributes(@NonNull final TypedArray typedArray, @NonNull final Class androidRes,
                                             @NonNull final List<String> errors, @NonNull final LineStrategy lineStrategy)
            throws NoSuchFieldException, IllegalAccessException {
        if (!lineStrategy.scalable) {
            AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_textSize"), true,
                    "textSize required parameter. If it's dynamic then use '0sp'");
        }
        if (lineStrategy.multiline) {
            if (typedArray.getInt(AttributesUtils.getField(androidRes, "TextView_lines"), -1) == 1) {
                errors.add("lines should be more than 1 if lineStrategy is true");
            }
            if (typedArray.getInt(AttributesUtils.getField(androidRes, "TextView_maxLines"), -1) == 1) {
                errors.add("maxLines should be more than 1 if lineStrategy is true");
            }
        } else {
            AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_lines"), false,
                    "remove lines and use lineStrategy");
            AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_maxLines"), false,
                    "remove maxLines and use lineStrategy");
            AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_minLines"), false,
                    "remove minLines and use lineStrategy");
            AttributesUtils.checkAttribute(typedArray, errors, AttributesUtils.getField(androidRes, "TextView_textAllCaps"), false,
                    "remove textAllCaps and use app:textAllCaps");
        }
    }

    /**
     * Sets behavior of text if there is no space for it in one line.
     *
     * @param lineStrategy Specific {@link LineStrategy}.
     */
    public void setLineStrategy(@NonNull final LineStrategy lineStrategy) {
        setLineStrategy(lineStrategy, Integer.MAX_VALUE);
    }

    /**
     * Sets behavior of text if there is no space for it in one line.
     *
     * @param lineStrategy Specific {@link LineStrategy};
     * @param maxLines     Max lines if line strategy is multiline.
     */
    public void setLineStrategy(@NonNull final LineStrategy lineStrategy, final int maxLines) {
        this.lineStrategy = lineStrategy;
        final TransformationMethod transformationMethod = getTransformationMethod();
        super.setSingleLine(!lineStrategy.multiline);
        if (transformationMethod != null) {
            /*DEBUG if (!(transformationMethod instanceof SingleLineTransformationMethod)) {
                Lc.w("SingleLineTransformationMethod method ignored because of previous transformation method: " + transformationMethod);
            }*/
            setTransformationMethod(transformationMethod);
        }
        if (lineStrategy.multiline) {
            super.setMaxLines(maxLines);
        }
        switch (lineStrategy) {
            case SINGLE_LINE_ELLIPSIZE:
            case MULTILINE_ELLIPSIZE:
                super.setEllipsize(TextUtils.TruncateAt.END);
                break;
            case SINGLE_LINE_MARQUEE:
            case MULTILINE_MARQUEE:
                super.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                break;
            case SINGLE_LINE_AUTO_SCALE:
                super.setEllipsize(null);
                break;
            case SINGLE_LINE_ELLIPSIZE_MIDDLE:
            case MULTILINE_ELLIPSIZE_MIDDLE:
                super.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                break;
            default:
                Lc.assertion("Unknown line strategy: " + lineStrategy);
                break;
        }
        if (lineStrategy.scalable) {
            requestLayout();
        }
    }

    /**
     * Returns behavior of text if there is no space for it in one line.
     *
     * @return Specific {@link LineStrategy}.
     */
    @NonNull
    public LineStrategy getLineStrategy() {
        return lineStrategy;
    }

    @Override
    public void setSingleLine() {
        if (!constructed) {
            return;
        }
        Lc.assertion(new IllegalStateException(AttributesUtils.viewError(this, "Do not specify setSingleLine use setLineStrategy instead")));
    }

    @Override
    public void setSingleLine(final boolean singleLine) {
        if (!constructed) {
            return;
        }
        Lc.assertion(new IllegalStateException(AttributesUtils.viewError(this, "Do not specify setSingleLine use setLineStrategy instead")));
    }

    @Override
    public void setLines(final int lines) {
        if (constructed && lineStrategy.multiline && lines == 1) {
            Lc.assertion(new IllegalStateException(AttributesUtils.viewError(this, "lines = 1 is illegal if lineStrategy is multiline")));
            return;
        }
        super.setLines(lines);
    }

    @Override
    public void setMaxLines(final int maxLines) {
        if (constructed && !lineStrategy.multiline && maxLines > 1) {
            Lc.assertion(new IllegalStateException(
                    AttributesUtils.viewError(this, "maxLines > 1 is illegal if lineStrategy is single line")));
            return;
        }
        super.setMaxLines(maxLines);
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
    public void setMinLines(final int minLines) {
        if (constructed && !lineStrategy.multiline && minLines > 1) {
            Lc.assertion(new IllegalStateException(
                    AttributesUtils.viewError(this, "minLines > 1 is illegal if lineStrategy is single line")));
            return;
        }
        super.setMinLines(minLines);
    }

    @Override
    public void setEllipsize(@NonNull final TextUtils.TruncateAt ellipsize) {
        if (!constructed) {
            return;
        }
        Lc.assertion(new IllegalStateException(AttributesUtils.viewError(this, "Do not specify ellipsize use setLineStrategy instead")));
    }

    @Override
    public void setText(@Nullable final CharSequence text, @Nullable final BufferType type) {
        super.setText(text, type);
        if (constructed && lineStrategy.scalable) {
            requestLayout();
        }
    }

    @Override
    public void setTextSize(final float size) {
        if (constructed && lineStrategy.scalable) {
            Lc.cutAssertion(new IllegalStateException(AttributesUtils.viewError(this, "textSize call is illegal if lineStrategy is scalable")));
            return;
        }
        super.setTextSize(size);
    }

    @Override
    public void setTextSize(final int unit, final float size) {
        if (constructed && lineStrategy.scalable) {
            Lc.assertion(new IllegalStateException(AttributesUtils.viewError(this, "textSize call is illegal if lineStrategy is scalable")));
            return;
        }
        super.setTextSize(unit, size);
    }

    @SuppressLint("WrongCall")
    //WrongCall: actually this method is always calling from onMeasure
    private void computeScalableTextSize(final int maxWidth, final int maxHeight) {
        final int minDifference = (int) UiUtils.OfMetrics.INSTANCE.dpToPixels(getContext(), 1);
        int difference = (int) UiUtils.OfMetrics.INSTANCE.dpToPixels(getContext(), START_SCALABLE_DIFFERENCE);
        ScaleAction scaleAction = ScaleAction.DO_NOTHING;
        ScaleAction previousScaleAction = ScaleAction.DO_NOTHING;
        do {
            switch (scaleAction) {
                case SCALE_DOWN:
                    if (difference > minDifference) {
                        difference -= minDifference;
                        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.max(0, getTextSize() - difference));
                    } else {
                        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.max(0, getTextSize() - minDifference));
                        if (previousScaleAction == ScaleAction.SCALE_UP) {
                            return;
                        }
                    }
                    break;
                case SCALE_UP:
                    if (difference > minDifference) {
                        difference -= minDifference;
                        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.max(0, getTextSize() + difference));
                    } else {
                        if (previousScaleAction == ScaleAction.SCALE_DOWN) {
                            return;
                        }
                        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.max(0, getTextSize() + minDifference));
                    }
                    break;
                case DO_NOTHING:
                default:
                    break;
            }
            super.onMeasure(UNSPECIFIED_MEASURE_SPEC, UNSPECIFIED_MEASURE_SPEC);
            previousScaleAction = scaleAction;
            scaleAction = computeScaleAction(maxWidth, maxHeight);
        }
        while (scaleAction != ScaleAction.DO_NOTHING);
    }

    @NonNull
    private ScaleAction computeScaleAction(final int maxWidth, final int maxHeight) {
        ScaleAction result = ScaleAction.DO_NOTHING;
        if (maxWidth < getMeasuredWidth()) {
            result = ScaleAction.SCALE_DOWN;
        } else if (maxWidth > getMeasuredWidth()) {
            result = ScaleAction.SCALE_UP;
        }

        if (maxHeight < getMeasuredHeight()) {
            result = ScaleAction.SCALE_DOWN;
        } else if (maxHeight > getMeasuredHeight() && result != ScaleAction.SCALE_DOWN) {
            result = ScaleAction.SCALE_UP;
        }
        return result;
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (!constructed || !lineStrategy.scalable || (maxWidth <= 0 && maxHeight <= 0) || TextUtils.isEmpty(getText())) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        computeScalableTextSize(MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED ? maxWidth : SIZE_THRESHOLD,
                MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.UNSPECIFIED ? maxHeight : SIZE_THRESHOLD);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private enum ScaleAction {
        SCALE_DOWN,
        SCALE_UP,
        DO_NOTHING
    }

    /**
     * Specific behavior, mostly based on combination of {@link #getEllipsize()} and {@link #getMaxLines()} to specify how view should show text
     * if there is no space for it on one line.
     */
    public enum LineStrategy {

        /**
         * Not more than one line and ellipsize text with dots at the end.
         */
        SINGLE_LINE_ELLIPSIZE(false, false),
        /**
         * Not more than one line and ellipsize text with marquee at the end.
         */
        SINGLE_LINE_MARQUEE(false, false),
        /**
         * Not more than one line and scale text to maximum possible size.
         */
        SINGLE_LINE_AUTO_SCALE(false, true),
        /**
         * More than one line and ellipsize text with dots at the end.
         */
        MULTILINE_ELLIPSIZE(true, false),
        /**
         * More than one line and ellipsize text with marquee at the end.
         */
        MULTILINE_MARQUEE(true, false),
        /**
         * Not more than one line and ellipsize text with dots in the middle.
         */
        SINGLE_LINE_ELLIPSIZE_MIDDLE(false, false),
        /**
         * More than one line and ellipsize text with dots in the middle.
         */
        MULTILINE_ELLIPSIZE_MIDDLE(true, false);

        @NonNull
        public static LineStrategy byResIndex(final int resIndex) {
            if (resIndex < 0 || resIndex >= values().length) {
                Lc.assertion("Unexpected resIndex " + resIndex);
                return MULTILINE_ELLIPSIZE;
            }
            return values()[resIndex];
        }

        private final boolean multiline;
        private final boolean scalable;

        LineStrategy(final boolean multiline, final boolean scalable) {
            this.multiline = multiline;
            this.scalable = scalable;
        }

    }

}
