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

package ru.touchin.roboswag.components.navigation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;

import java.util.Set;

import ru.touchin.roboswag.core.log.Lc;
import ru.touchin.roboswag.core.log.LcGroup;

/**
 * Created by Gavriil Sitnikov on 08/03/2016.
 * Base activity to use in components repository.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @NonNull
    private final Set<OnBackPressedListener> onBackPressedListeners = new ArraySet<>();

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this) + " requestCode: " + requestCode + "; resultCode: " + resultCode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
    }

    @Override
    protected void onPause() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle stateToSave) {
        super.onSaveInstanceState(stateToSave);
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
    }

    @Override
    protected void onStop() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LcGroup.UI_LIFECYCLE.i(Lc.getCodePoint(this));
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void addOnBackPressedListener(@NonNull final OnBackPressedListener onBackPressedListener) {
        onBackPressedListeners.add(onBackPressedListener);
    }

    public void removeOnBackPressedListener(@NonNull final OnBackPressedListener onBackPressedListener) {
        onBackPressedListeners.remove(onBackPressedListener);
    }

    @Override
    public void onBackPressed() {
        for (final OnBackPressedListener onBackPressedListener : onBackPressedListeners) {
            if (onBackPressedListener.onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }

}
