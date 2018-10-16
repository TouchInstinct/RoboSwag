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
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;

import ru.touchin.roboswag.components.utils.UiUtils;

/**
 * Created by Ilia Kurtov on 07/12/2016.
 * Simple endless progress bar view in material (round circle) style.
 * It is able to setup size, stroke width and color.
 * See MaterialLoadingBar Attributes:
 * R.styleable#MaterialLoadingBar_size
 * R.styleable#MaterialLoadingBar_strokeWidth
 * R.styleable#MaterialLoadingBar_color
 * Use
 * R.styleable#MaterialLoadingBar_materialLoadingBarStyle
 * to set default style of MaterialLoadingBar in your Theme.
 * Sample:
 * <style name="MyAppLoadingBar">
 * <item name="strokeWidth">3dp</item>
 * <item name="color">@android:color/black</item>
 * <item name="size">24dp</item>
 * </style>
 * <style name="AppTheme" parent="Theme.AppCompat.NoActionBar">
 * <item name="materialLoadingBarStyle">@style/MyAppLoadingBar</item>
 * </style>
 */
public class MaterialLoadingBar extends AppCompatImageView {

    private static int getPrimaryColor(@NonNull final Context context) {
        final int colorAttr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAttr = android.R.attr.colorPrimary;
        } else {
            colorAttr = context.getResources().getIdentifier("colorPrimary", "attr", context.getPackageName());
        }
        final TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.data;
    }

    private MaterialProgressDrawable progressDrawable;

    public MaterialLoadingBar(@NonNull final Context context) {
        this(context, null);
    }

    public MaterialLoadingBar(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, R.attr.materialLoadingBarStyle);
    }

    public MaterialLoadingBar(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialLoadingBar,
                defStyleAttr,
                0);
        final int size = (int) typedArray.getDimension(R.styleable.MaterialLoadingBar_size, UiUtils.OfMetrics.dpToPixels(context, 48));
        final int color = typedArray.getColor(R.styleable.MaterialLoadingBar_color, getPrimaryColor(context));
        final float strokeWidth = typedArray.getDimension(R.styleable.MaterialLoadingBar_strokeWidth,
                UiUtils.OfMetrics.dpToPixels(context, 4));
        typedArray.recycle();

        progressDrawable = new MaterialProgressDrawable(context, size);
        setColor(color);
        progressDrawable.setStrokeWidth(strokeWidth);
        setScaleType(ScaleType.CENTER);
        setImageDrawable(progressDrawable);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        progressDrawable.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        progressDrawable.stop();
        super.onDetachedFromWindow();
    }

    /**
     * Set color of loader.
     *
     * @param colorInt Color of loader to be set.
     */
    public void setColor(@ColorInt final int colorInt) {
        progressDrawable.setColor(colorInt);
    }

}
