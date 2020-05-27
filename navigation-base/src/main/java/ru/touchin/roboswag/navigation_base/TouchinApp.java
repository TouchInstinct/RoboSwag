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

package ru.touchin.roboswag.navigation_base;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import net.danlew.android.joda.JodaTimeAndroid;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import io.fabric.sdk.android.Fabric;
import ru.touchin.roboswag.core.log.ConsoleLogProcessor;
import ru.touchin.roboswag.core.log.Lc;
import ru.touchin.roboswag.core.log.LcGroup;
import ru.touchin.roboswag.core.log.LcLevel;
import ru.touchin.roboswag.core.utils.CrashlyticsLogProcessor;

/**
 * Created by Gavriil Sitnikov on 10/03/16.
 * Base class of application to extends for Touch Instinct related projects.
 */
public abstract class TouchinApp extends Application {

    @Override
    protected void attachBaseContext(@NonNull final Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        if (BuildConfig.DEBUG) {
            enableStrictMode();
            Lc.initialize(new ConsoleLogProcessor(LcLevel.VERBOSE), true);
            LcGroup.UI_LIFECYCLE.disable();
        } else {
            try {
                final Crashlytics crashlytics = new Crashlytics();
                Fabric.with(this, crashlytics);
                Fabric.getLogger().setLogLevel(Log.ERROR);
                Lc.initialize(new CrashlyticsLogProcessor(crashlytics), false);
            } catch (final NoClassDefFoundError error) {
                Lc.initialize(new ConsoleLogProcessor(LcLevel.INFO), false);
                Lc.e("Crashlytics initialization error! Did you forget to add\n"
                        + "compile('com.crashlytics.sdk.android:crashlytics:+@aar') {\n"
                        + "        transitive = true;\n"
                        + "}\n"
                        + "to your build.gradle?", error);
            }
        }
    }

    private void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .permitDiskReads()
                .permitDiskWrites()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
    }

}
