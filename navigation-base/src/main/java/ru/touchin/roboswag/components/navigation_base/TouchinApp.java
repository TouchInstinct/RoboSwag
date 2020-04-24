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

package ru.touchin.roboswag.components.navigation_base;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;
import io.fabric.sdk.android.Fabric;
import ru.touchin.roboswag.core.log.ConsoleLogProcessor;
import ru.touchin.roboswag.core.log.Lc;
import ru.touchin.roboswag.core.log.LcGroup;
import ru.touchin.roboswag.core.log.LcLevel;
import ru.touchin.roboswag.core.log.LogProcessor;
import ru.touchin.roboswag.core.utils.ShouldNotHappenException;
import ru.touchin.templates.ApiModel;

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

    private static class CrashlyticsLogProcessor extends LogProcessor {

        @NonNull
        private final Crashlytics crashlytics;

        public CrashlyticsLogProcessor(@NonNull final Crashlytics crashlytics) {
            super(LcLevel.INFO);
            this.crashlytics = crashlytics;
        }

        @Override
        public void processLogMessage(@NonNull final LcGroup group,
                                      @NonNull final LcLevel level,
                                      @NonNull final String tag,
                                      @NonNull final String message,
                                      @Nullable final Throwable throwable) {
            if (group == LcGroup.UI_LIFECYCLE) {
                crashlytics.core.log(level.getPriority(), tag, message);
            } else if (!level.lessThan(LcLevel.ASSERT)
                    || (group == ApiModel.API_VALIDATION_LC_GROUP && level == LcLevel.ERROR)) {
                Log.e(tag, message);
                if (throwable != null) {
                    crashlytics.core.log(level.getPriority(), tag, message);
                    crashlytics.core.logException(throwable);
                } else {
                    final ShouldNotHappenException exceptionToLog = new ShouldNotHappenException(tag + ':' + message);
                    reduceStackTrace(exceptionToLog);
                    crashlytics.core.logException(exceptionToLog);
                }
            }
        }

        private void reduceStackTrace(@NonNull final Throwable throwable) {
            final StackTraceElement[] stackTrace = throwable.getStackTrace();
            final List<StackTraceElement> reducedStackTraceList = new ArrayList<>();
            for (int i = stackTrace.length - 1; i >= 0; i--) {
                final StackTraceElement stackTraceElement = stackTrace[i];
                if (stackTraceElement.getClassName().contains(getClass().getSimpleName())
                        || stackTraceElement.getClassName().contains(LcGroup.class.getName())
                        || stackTraceElement.getClassName().contains(Lc.class.getName())) {
                    break;
                }
                reducedStackTraceList.add(0, stackTraceElement);
            }
            StackTraceElement[] reducedStackTrace = new StackTraceElement[reducedStackTraceList.size()];
            reducedStackTrace = reducedStackTraceList.toArray(reducedStackTrace);
            throwable.setStackTrace(reducedStackTrace);
        }

    }

}
