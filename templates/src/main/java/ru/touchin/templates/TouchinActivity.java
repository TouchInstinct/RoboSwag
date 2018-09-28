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

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import ru.touchin.roboswag.components.navigation.activities.BaseActivity;
import ru.touchin.roboswag.core.log.Lc;

/**
 * Created by Gavriil Sitnikov on 11/03/16.
 * Base class of activity to extends for Touch Instinct related projects.
 */
public abstract class TouchinActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Possible work around for market launches. See http://code.google.com/p/android/issues/detail?id=2373
        // for more details. Essentially, the market launches the main activity on top of other activities.
        // we never want this to happen. Instead, we check if we are the root and if not, we finish.
        if (!isTaskRoot() && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(getIntent().getAction())) {
            Lc.e("Finishing activity as it is launcher but not root");
            finish();
        }
    }

    /**
     * Setup task description of application for Android 5.0 and later. It is showing when user opens task bar.
     *
     * @param label           Name of application to show in task bar;
     * @param iconRes         Icon of application to show in task bar;
     * @param primaryColorRes Color of application to show in task bar.
     */
    protected void setupTaskDescriptor(@NonNull final String label, @DrawableRes final int iconRes, @ColorRes final int primaryColorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(label,
                    ((BitmapDrawable) ContextCompat.getDrawable(this, iconRes)).getBitmap(),
                    ContextCompat.getColor(this, primaryColorRes));
            setTaskDescription(taskDescription);
        }
    }

}
