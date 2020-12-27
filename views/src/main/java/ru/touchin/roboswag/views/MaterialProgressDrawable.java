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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.touchin.roboswag.components.utils.UiUtils;

/**
 * Created by Gavriil Sitnikov on 01/03/16.
 * Simple realization of endless progress bar which is looking material-like.
 */
public class MaterialProgressDrawable extends Drawable implements Runnable, Animatable {

    private static final int UPDATE_INTERVAL = 1000 / 60;

    private static final float DEFAULT_STROKE_WIDTH_DP = 4.5f;
    private static final Parameters DEFAULT_PARAMETERS = new Parameters(20, 270, 4, 12, 4, 8);

    private final int size;
    @NonNull
    private final Paint paint;
    @NonNull
    private Parameters parameters = DEFAULT_PARAMETERS;
    @NonNull
    private final RectF arcBounds = new RectF();

    private float rotationAngle;
    private float arcSize;
    private boolean running;

    public MaterialProgressDrawable(@NonNull final Context context) {
        this(context, -1);
    }

    public MaterialProgressDrawable(@NonNull final Context context, final int size) {
        super();

        this.size = size;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(UiUtils.OfMetrics.INSTANCE.dpToPixels(context, DEFAULT_STROKE_WIDTH_DP));
        paint.setColor(Color.BLACK);
    }

    @Override
    public int getIntrinsicWidth() {
        return size;
    }

    @Override
    public int getIntrinsicHeight() {
        return size;
    }

    /**
     * Returns width of arc.
     *
     * @return Width.
     */
    public float getStrokeWidth() {
        return paint.getStrokeWidth();
    }

    /**
     * Sets width of arc.
     *
     * @param strokeWidth Width.
     */
    public void setStrokeWidth(final float strokeWidth) {
        paint.setStrokeWidth(strokeWidth);
        updateArcBounds();
        invalidateSelf();
    }

    /**
     * Sets color of arc.
     *
     * @param color Color.
     */
    public void setColor(@ColorInt final int color) {
        paint.setColor(color);
        invalidateSelf();
    }

    /**
     * Returns magic parameters of spinning.
     *
     * @return Parameters of spinning.
     */
    @NonNull
    public Parameters getParameters() {
        return parameters;
    }

    /**
     * Sets magic parameters of spinning.
     *
     * @param parameters Parameters of spinning.
     */
    public void setParameters(@NonNull final Parameters parameters) {
        this.parameters = parameters;
        invalidateSelf();
    }

    @Override
    protected void onBoundsChange(@NonNull final Rect bounds) {
        super.onBoundsChange(bounds);
        updateArcBounds();
    }

    private void updateArcBounds() {
        arcBounds.set(getBounds());
        //HACK: + 1 as anti-aliasing drawing bug workaround
        final int inset = (int) (paint.getStrokeWidth() / 2) + 1;
        arcBounds.inset(inset, inset);
    }

    @SuppressWarnings("PMD.NPathComplexity")
    @Override
    public void draw(@NonNull final Canvas canvas) {
        final boolean isGrowingCycle = (((int) (arcSize / parameters.maxAngle)) % 2) == 0;
        final float angle = arcSize % parameters.maxAngle;
        final float shift = (angle / parameters.maxAngle) * parameters.gapAngle;
        canvas.drawArc(arcBounds, isGrowingCycle ? rotationAngle + shift : rotationAngle + parameters.gapAngle - shift,
                isGrowingCycle ? angle + parameters.gapAngle : parameters.maxAngle - angle + parameters.gapAngle, false, paint);
        //TODO: compute based on animation start time
        rotationAngle += isGrowingCycle ? parameters.rotationMagicNumber1 : parameters.rotationMagicNumber2;
        arcSize += isGrowingCycle ? parameters.arcMagicNumber1 : parameters.arcMagicNumber2;
        if (arcSize < 0) {
            arcSize = 0;
        }
        if (isRunning()) {
            scheduleSelf(this, SystemClock.uptimeMillis() + UPDATE_INTERVAL);
        }
    }

    @Override
    public void setAlpha(final int alpha) {
        paint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable final ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void start() {
        if (!running) {
            running = true;
            run();
        }
    }

    @Override
    public void stop() {
        if (running) {
            unscheduleSelf(this);
            running = false;
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        if (running) {
            invalidateSelf();
        }
    }

    /**
     * Some parameters which are using to spin progress bar.
     */
    public static class Parameters {

        private final float gapAngle;
        private final float maxAngle;
        private final float rotationMagicNumber1;
        private final float rotationMagicNumber2;
        private final float arcMagicNumber1;
        private final float arcMagicNumber2;

        public Parameters(final float gapAngle, final float maxAngle,
                          final float rotationMagicNumber1, final float rotationMagicNumber2,
                          final float arcMagicNumber1, final float arcMagicNumber2) {
            this.gapAngle = gapAngle;
            this.maxAngle = maxAngle;
            this.rotationMagicNumber1 = rotationMagicNumber1;
            this.rotationMagicNumber2 = rotationMagicNumber2;
            this.arcMagicNumber1 = arcMagicNumber1;
            this.arcMagicNumber2 = arcMagicNumber2;
        }

        /**
         * Returns angle of gap of arc.
         *
         * @return Angle of gap.
         */
        public float getGapAngle() {
            return gapAngle;
        }

        /**
         * Returns maximum angle of arc.
         *
         * @return Maximum angle of arc.
         */
        public float getMaxAngle() {
            return maxAngle;
        }

        /**
         * Magic parameter 1.
         *
         * @return Magic.
         */
        public float getRotationMagicNumber1() {
            return rotationMagicNumber1;
        }

        /**
         * Magic parameter 2.
         *
         * @return Magic.
         */
        public float getRotationMagicNumber2() {
            return rotationMagicNumber2;
        }

        /**
         * Magic parameter 3.
         *
         * @return Magic.
         */
        public float getArcMagicNumber1() {
            return arcMagicNumber1;
        }

        /**
         * Magic parameter 4.
         *
         * @return Magic.
         */
        public float getArcMagicNumber2() {
            return arcMagicNumber2;
        }

    }

}
