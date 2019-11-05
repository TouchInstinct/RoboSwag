/*
 *  Copyright (c) 2019 Touch Instinct
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

package ru.touchin.roboswag.components.navigation

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.util.Log
import androidx.multidex.MultiDex
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import net.danlew.android.joda.JodaTimeAndroid
import ru.touchin.roboswag.core.log.ConsoleLogProcessor
import ru.touchin.roboswag.core.log.CrashlyticsLogProcessor
import ru.touchin.roboswag.core.log.Lc
import ru.touchin.roboswag.core.log.LcGroup
import ru.touchin.roboswag.core.log.LcLevel

/**
 * Base class of application to extends for Touch Instinct related projects.
 */
abstract class TouchinApp : Application() {

    companion object {
        private const val CRASHLYTICS_INITIALIZATION_ERROR = "Crashlytics initialization error! Did you forget to add\n" +
                "compile('com.crashlytics.sdk.android:crashlytics:+') {\n" +
                "        transitive = true;\n" +
                "}\n" +
                "to your build.gradle?"
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        enableStrictMode()
        initCrashlytics()
    }

    private fun enableStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .permitDiskReads()
                    .permitDiskWrites()
                    .penaltyLog()
                    .build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build())
        }
    }

    private fun initCrashlytics() {
        if (BuildConfig.DEBUG) {
            Lc.initialize(ConsoleLogProcessor(LcLevel.VERBOSE), true)
            LcGroup.UI_LIFECYCLE.disable()
        } else {
            try {
                val crashlytics = Crashlytics()
                Fabric.with(this, crashlytics)
                Fabric.getLogger().logLevel = Log.ERROR
                Lc.initialize(CrashlyticsLogProcessor(crashlytics), false)
            } catch (error: NoClassDefFoundError) {
                Lc.initialize(ConsoleLogProcessor(LcLevel.INFO), false)
                Lc.e(CRASHLYTICS_INITIALIZATION_ERROR, error)
            }
        }
    }

}
