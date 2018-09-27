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

package ru.touchin.roboswag.components.utils.spans;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/**
 * Created by Ilia Kurtov on 15/02/2016.
 * Span for typefaces in texts.
 * http://stackoverflow.com/a/15181195
 */
public class TypefaceSpan extends MetricAffectingSpan {

    @NonNull
    private final Typeface typeface;

    public TypefaceSpan(@NonNull final Typeface typeface) {
        super();
        this.typeface = typeface;
    }

    @Override
    public void updateMeasureState(@NonNull final TextPaint textPaint) {
        textPaint.setTypeface(typeface);
        textPaint.setFlags(textPaint.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    @Override
    public void updateDrawState(@NonNull final TextPaint textPaint) {
        textPaint.setTypeface(typeface);
        textPaint.setFlags(textPaint.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

}