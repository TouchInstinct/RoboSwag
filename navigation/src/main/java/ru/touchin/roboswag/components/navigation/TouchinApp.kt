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
import ru.touchin.roboswag.core.log.Lc
import ru.touchin.roboswag.core.log.LcGroup
import ru.touchin.roboswag.core.log.LcLevel
import ru.touchin.roboswag.core.log.LogProcessor
import ru.touchin.roboswag.core.utils.ShouldNotHappenException
import ru.touchin.templates.ApiModel

/**
 * Base class of application to extends for Touch Instinct related projects.
 */
abstract class TouchinApp : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        if (BuildConfig.DEBUG) {
            enableStrictMode()
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
                Lc.e("Crashlytics initialization error! Did you forget to add\n"
                        + "compile('com.crashlytics.sdk.android:crashlytics:+@aar') {\n"
                        + "        transitive = true;\n"
                        + "}\n"
                        + "to your build.gradle?", error)
            }

        }
    }

    private fun enableStrictMode() {
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

    private class CrashlyticsLogProcessor(private val crashlytics: Crashlytics) : LogProcessor(LcLevel.INFO) {

        override fun processLogMessage(
                group: LcGroup,
                level: LcLevel,
                tag: String,
                message: String,
                throwable: Throwable?
        ) {
            when {
                group === LcGroup.UI_LIFECYCLE -> {
                    crashlytics.core.log(level.priority, tag, message)
                }
                !level.lessThan(LcLevel.ASSERT) || group === ApiModel.API_VALIDATION_LC_GROUP && level == LcLevel.ERROR -> {
                    Log.e(tag, message)
                    if (throwable != null) {
                        crashlytics.core.log(level.priority, tag, message)
                        crashlytics.core.logException(throwable)
                    } else {
                        val exceptionToLog = ShouldNotHappenException("$tag:$message")
                        reduceStackTrace(exceptionToLog)
                        crashlytics.core.logException(exceptionToLog)
                    }
                }
            }
        }

        private fun reduceStackTrace(throwable: Throwable) {
            val stackTrace = throwable.stackTrace
            val reducedStackTraceList = arrayListOf<StackTraceElement>()
            for (i in stackTrace.indices.reversed()) {
                val stackTraceElement = stackTrace[i]
                if (stackTraceElement.className.contains(javaClass.simpleName)
                        || stackTraceElement.className.contains(LcGroup::class.java.name)
                        || stackTraceElement.className.contains(Lc::class.java.name)) {
                    break
                }
                reducedStackTraceList.add(0, stackTraceElement)
            }
            throwable.stackTrace = reducedStackTraceList.toTypedArray()
        }

    }

}
