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

package ru.touchin.roboswag.core.log;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.touchin.roboswag.core.utils.ShouldNotHappenException;

/**
 * Created by Gavriil Sitnikov on 13/11/2015.
 * General logging utility of RoboSwag library.
 * You can initialize {@link LogProcessor} to intercept log messages and make decision how to show them.
 * Also you can specify assertions behavior to manually make application more stable in production but intercept illegal states in some
 * third-party tool to fix them later but not crash in production.
 */
@SuppressWarnings({"checkstyle:methodname", "PMD.ShortMethodName", "PMD.ShortClassName"})
//MethodNameCheck,ShortMethodName: log methods better be 1-symbol
public final class Lc {

    public static final LcGroup GENERAL_LC_GROUP = new LcGroup("GENERAL");

    public static final int STACK_TRACE_CODE_DEPTH;

    private static boolean crashOnAssertions = true;
    @NonNull
    private static LogProcessor logProcessor = new ConsoleLogProcessor(LcLevel.ERROR);

    static {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int stackDepth;
        for (stackDepth = 0; stackDepth < stackTrace.length; stackDepth++) {
            if (stackTrace[stackDepth].getClassName().equals(Lc.class.getName())) {
                break;
            }
        }
        STACK_TRACE_CODE_DEPTH = stackDepth + 1;
    }

    /**
     * Flag to crash application or pass it to {@link LogProcessor#processLogMessage(LcGroup, LcLevel, String, String, Throwable)}
     * on specific {@link LcGroup#assertion(Throwable)} points of code.
     *
     * @return True if application should crash on assertion.
     */
    public static boolean isCrashOnAssertions() {
        return crashOnAssertions;
    }

    /**
     * Returns {@link LogProcessor} object to intercept incoming log messages (by default it returns {@link ConsoleLogProcessor}).
     *
     * @return Specific {@link LogProcessor}.
     */
    @NonNull
    public static LogProcessor getLogProcessor() {
        return logProcessor;
    }

    /**
     * Initialize general logging behavior.
     *
     * @param logProcessor      {@link LogProcessor} to intercept all log messages;
     * @param crashOnAssertions Flag to crash application
     *                          or pass it to {@link LogProcessor#processLogMessage(LcGroup, LcLevel, String, String, Throwable)}
     *                          on specific {@link LcGroup#assertion(Throwable)} points of code.
     */
    public static void initialize(@NonNull final LogProcessor logProcessor, final boolean crashOnAssertions) {
        Lc.crashOnAssertions = crashOnAssertions;
        Lc.logProcessor = logProcessor;
    }

    /**
     * Logs debug message via {@link #GENERAL_LC_GROUP}.
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    public static void d(@NonNull final String message, @NonNull final Object... args) {
        GENERAL_LC_GROUP.d(message, args);
    }

    /**
     * Logs debug message via {@link #GENERAL_LC_GROUP}.
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    public static void d(@NonNull final Throwable throwable, @NonNull final String message, @NonNull final Object... args) {
        GENERAL_LC_GROUP.d(throwable, message, args);
    }

    /**
     * Logs info message via {@link #GENERAL_LC_GROUP}.
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    public static void i(@NonNull final String message, @NonNull final Object... args) {
        GENERAL_LC_GROUP.i(message, args);
    }

    /**
     * Logs info message via {@link #GENERAL_LC_GROUP}.
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    public static void i(@NonNull final Throwable throwable, @NonNull final String message, @NonNull final Object... args) {
        GENERAL_LC_GROUP.i(throwable, message, args);
    }

    /**
     * Logs warning message via {@link #GENERAL_LC_GROUP}.
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    public static void w(@NonNull final String message, @NonNull final Object... args) {
        GENERAL_LC_GROUP.w(message, args);
    }

    /**
     * Logs warning message via {@link #GENERAL_LC_GROUP}.
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    public static void w(@NonNull final Throwable throwable, @NonNull final String message, @NonNull final Object... args) {
        GENERAL_LC_GROUP.w(throwable, message, args);
    }

    /**
     * Logs error message via {@link #GENERAL_LC_GROUP}.
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    public static void e(@NonNull final String message, @NonNull final Object... args) {
        GENERAL_LC_GROUP.e(message, args);
    }

    /**
     * Logs error message via {@link #GENERAL_LC_GROUP}.
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    public static void e(@NonNull final Throwable throwable, @NonNull final String message, @NonNull final Object... args) {
        GENERAL_LC_GROUP.e(throwable, message, args);
    }

    /**
     * Processes assertion. Normally it will throw {@link ShouldNotHappenException} and crash app.
     * If it should crash or not is specified at {@link Lc#isCrashOnAssertions()}.
     * In some cases crash on assertions should be switched off and assertion should be processed in {@link LogProcessor}.
     * It is useful for example to not crash but log it as handled crash in Crashlitycs in production build.
     *
     * @param message Message that is describing assertion.
     */
    public static void assertion(@NonNull final String message) {
        GENERAL_LC_GROUP.assertion(message);
    }

    /**
     * Processes assertion. Normally it will throw {@link ShouldNotHappenException} and crash app.
     * If it should crash or not is specified at {@link Lc#isCrashOnAssertions()}.
     * In some cases crash on assertions should be switched off and assertion should be processed in {@link LogProcessor}.
     * It is useful for example to not crash but log it as handled crash in Crashlitycs in production build.
     *
     * @param throwable Exception that is describing assertion.
     */
    public static void assertion(@NonNull final Throwable throwable) {
        GENERAL_LC_GROUP.assertion(throwable);
    }

    /**
     * Throws assertion on main thread (to avoid Rx exceptions e.g.) and cuts top causes by type of exception class.
     *
     * @param assertion              Source throwable;
     * @param exceptionsClassesToCut Classes which will be cut from top of causes stack of source throwable.
     */
    @SafeVarargs
    public static void cutAssertion(@NonNull final Throwable assertion, @NonNull final Class<? extends Throwable>... exceptionsClassesToCut) {
        new Handler(Looper.getMainLooper()).post(() -> {
            final List<Throwable> processedExceptions = new ArrayList<>();
            Throwable result = assertion;
            boolean exceptionAssignableFromIgnores;
            do {
                exceptionAssignableFromIgnores = false;
                processedExceptions.add(result);
                for (final Class exceptionClass : exceptionsClassesToCut) {
                    if (result.getClass().isAssignableFrom(exceptionClass)) {
                        exceptionAssignableFromIgnores = true;
                        result = result.getCause();
                        break;
                    }
                }
            }
            while (exceptionAssignableFromIgnores && result != null && !processedExceptions.contains(result));
            Lc.assertion(result != null ? result : assertion);
        });
    }

    /**
     * Returns line of code from where this method called.
     *
     * @param caller Object who is calling for code point;
     * @return String represents code point.
     */
    @NonNull
    public static String getCodePoint(@Nullable final Object caller) {
        return getCodePoint(caller, 1);
    }

    /**
     * Returns line of code from where this method called.
     *
     * @param caller     Object who is calling for code point;
     * @param stackShift caller Shift of stack (e.g. 2 means two elements deeper);
     * @return String represents code point.
     */
    @NonNull
    public static String getCodePoint(@Nullable final Object caller, final int stackShift) {
        final StackTraceElement traceElement = Thread.currentThread().getStackTrace()[STACK_TRACE_CODE_DEPTH + stackShift];
        return traceElement.getMethodName() + '(' + traceElement.getFileName() + ':' + traceElement.getLineNumber() + ')'
                + (caller != null ? " of object " + caller.getClass().getSimpleName() + '(' + Integer.toHexString(caller.hashCode()) + ')' : "");
    }

    /**
     * Returns line of code from where this method called.
     *
     * @param caller Object who is calling for code point;
     * @param methodName String represents lifecycle method in which it was called
     * @return String represents code point.
     */
    @Non
    @NonNull
    public static String getCodePoint(@Nullable final Object caller, final String methodName) {
        return methodName
                + (caller != null ? " of object " + caller.getClass().getSimpleName() : "");
    }

    /**
     * Prints stacktrace in log with specified tag.
     *
     * @param tag Tag to be shown in logs.
     */

    @SuppressLint("LogConditional")
    public static void printStackTrace(@NonNull final String tag) {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, TextUtils.join("\n", Arrays.copyOfRange(stackTrace, STACK_TRACE_CODE_DEPTH, stackTrace.length)));
        }
    }

    private Lc() {
    }

}
