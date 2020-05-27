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

import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.touchin.roboswag.core.utils.ShouldNotHappenException;
import ru.touchin.roboswag.core.utils.ThreadLocalValue;

/**
 * Created by Gavriil Sitnikov on 14/05/2016.
 * Group of log messages with specific tag prefix (name of group).
 * It could be used in specific {@link LogProcessor} to filter messages by group.
 */
@SuppressWarnings({"checkstyle:methodname", "PMD.ShortMethodName"})
//MethodNameCheck,ShortMethodName: log methods better be 1-symbol
public class LcGroup {

    /**
     * Logging group to log UI metrics (like inflation or layout time etc.).
     */
    public static final LcGroup UI_METRICS = new LcGroup("UI_METRICS");
    /**
     * Logging group to log UI lifecycle (onCreate, onStart, onResume etc.).
     */
    public static final LcGroup UI_LIFECYCLE = new LcGroup("UI_LIFECYCLE");
    /**
     * Logging group to log UI lifecycle (onCreate, onStart, onResume etc.).
     */
    public static final LcGroup API_VALIDATION = new LcGroup("API_VALIDATION");

    private static final ThreadLocalValue<SimpleDateFormat> DATE_TIME_FORMATTER
            = new ThreadLocalValue<>(() -> new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()));

    @NonNull
    private final String name;
    private boolean disabled;

    public LcGroup(@NonNull final String name) {
        this.name = name;
    }

    /**
     * Disables logging of this group.
     */
    public void disable() {
        disabled = true;
    }

    /**
     * Enables logging of this group.
     */
    public void enable() {
        disabled = false;
    }

    @NonNull
    private String createLogTag() {
        final StackTraceElement trace = Thread.currentThread().getStackTrace()[Lc.STACK_TRACE_CODE_DEPTH + 3];
        return trace.getFileName() + ':' + trace.getLineNumber();
    }

    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    //AvoidCatchingThrowable: it is needed to safety format message
    @Nullable
    private String createFormattedMessage(@Nullable final String message, @NonNull final Object... args) {
        try {
            if (args.length > 0 && message == null) {
                throw new ShouldNotHappenException("Args are not empty but format message is null");
            }
            return message != null ? (args.length > 0 ? String.format(message, args) : message) : null;
        } catch (final Throwable formattingException) {
            Lc.assertion(formattingException);
            return null;
        }
    }

    @NonNull
    private String createLogMessage(@Nullable final String formattedMessage) {
        return DATE_TIME_FORMATTER.get().format(System.currentTimeMillis())
                + ' ' + Thread.currentThread().getName()
                + ' ' + name
                + (formattedMessage != null ? (' ' + formattedMessage) : "");
    }

    private void logMessage(@NonNull final LcLevel logLevel, @Nullable final String message,
                            @Nullable final Throwable throwable, @NonNull final Object... args) {
        if (disabled || logLevel.lessThan(Lc.getLogProcessor().getMinLogLevel())) {
            return;
        }

        if (throwable == null && args.length > 0 && args[0] instanceof Throwable) {
            Lc.w("Maybe you've misplaced exception with first format arg? format: %s; arg: %s", message, args[0]);
        }

        final String formattedMessage = createFormattedMessage(message, args);
        if (logLevel == LcLevel.ASSERT && Lc.isCrashOnAssertions()) {
            throw createAssertion(formattedMessage, throwable);
        }

        Lc.getLogProcessor().processLogMessage(this, logLevel, createLogTag(), createLogMessage(formattedMessage), throwable);
    }

    @NonNull
    private ShouldNotHappenException createAssertion(@Nullable final String message, @Nullable final Throwable exception) {
        return exception != null
                ? (message != null ? new ShouldNotHappenException(message, exception)
                : (exception instanceof ShouldNotHappenException ? (ShouldNotHappenException) exception : new ShouldNotHappenException(exception)))
                : (message != null ? new ShouldNotHappenException(message) : new ShouldNotHappenException());
    }

    /**
     * Logs debug message.
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    public void d(@NonNull final String message, @NonNull final Object... args) {
        logMessage(LcLevel.DEBUG, message, null, args);
    }

    /**
     * Logs debug message.
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    public void d(@NonNull final Throwable throwable, @NonNull final String message, @NonNull final Object... args) {
        logMessage(LcLevel.DEBUG, message, throwable, args);
    }

    /**
     * Logs info message.
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    public void i(@NonNull final String message, @NonNull final Object... args) {
        logMessage(LcLevel.INFO, message, null, args);
    }

    /**
     * Logs info message.
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    public void i(@NonNull final Throwable throwable, @NonNull final String message, @NonNull final Object... args) {
        logMessage(LcLevel.INFO, message, throwable, args);
    }

    /**
     * Logs warning message.
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    public void w(@NonNull final String message, @NonNull final Object... args) {
        logMessage(LcLevel.WARN, message, null, args);
    }

    /**
     * Logs warning message.
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    public void w(@NonNull final Throwable throwable, @NonNull final String message, @NonNull final Object... args) {
        logMessage(LcLevel.WARN, message, throwable, args);
    }

    /**
     * Logs error message.
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    public void e(@NonNull final String message, @NonNull final Object... args) {
        logMessage(LcLevel.ERROR, message, null, args);
    }

    /**
     * Logs error message.
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    public void e(@NonNull final Throwable throwable, @NonNull final String message, @NonNull final Object... args) {
        logMessage(LcLevel.ERROR, message, throwable, args);
    }

    /**
     * Processes assertion. Normally it will throw {@link ShouldNotHappenException} and crash app.
     * If it should crash or not is specified at {@link Lc#isCrashOnAssertions()}.
     * In some cases crash on assertions should be switched off and assertion should be processed in {@link LogProcessor}.
     * It is useful for example to not crash but log it as handled crash in Crashlitycs in production build.
     *
     * @param message Message that is describing assertion.
     */
    public void assertion(@NonNull final String message) {
        logMessage(LcLevel.ASSERT, "Assertion appears at %s with message: %s", null, Lc.getCodePoint(null, 2), message);
    }

    /**
     * Processes assertion. Normally it will throw {@link ShouldNotHappenException} and crash app.
     * If it should crash or not is specified at {@link Lc#isCrashOnAssertions()}.
     * In some cases crash on assertions should be switched off and assertion should be processed in {@link LogProcessor}.
     * It is useful for example to not crash but log it as handled crash in Crashlitycs in production build.
     *
     * @param throwable Exception that is describing assertion.
     */
    public void assertion(@NonNull final Throwable throwable) {
        logMessage(LcLevel.ASSERT, "Assertion appears at %s", throwable, Lc.getCodePoint(null, 2));
    }

}
