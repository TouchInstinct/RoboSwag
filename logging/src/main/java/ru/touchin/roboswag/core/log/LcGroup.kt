/*
 *  Copyright (c) 2019 RoboSwag (Gavriil Sitnikov, Vsevolod Ivanov)
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

package ru.touchin.roboswag.core.log

import ru.touchin.roboswag.core.utils.ShouldNotHappenException
import ru.touchin.roboswag.core.utils.ThreadLocalValue
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Group of log messages with specific tag prefix (name of group).
 * It could be used in specific [LogProcessor] to filter messages by group.
 */
@SuppressWarnings("checkstyle:methodname", "PMD.ShortMethodName")
//MethodNameCheck,ShortMethodName: log methods better be 1-symbol
class LcGroup(private val name: String) {

    companion object {

        /**
         * Logging group to log UI metrics (like inflation or layout time etc.).
         */
        val UI_METRICS = LcGroup("UI_METRICS")

        /**
         * Logging group to log UI lifecycle (onCreate, onStart, onResume etc.).
         */
        val UI_LIFECYCLE = LcGroup("UI_LIFECYCLE")

        private val DATE_TIME_FORMATTER = ThreadLocalValue(object : ThreadLocalValue.Fabric<SimpleDateFormat> {

            override fun create(): SimpleDateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

        })
    }

    private var disabled: Boolean = false

    /**
     * Disables logging of this group.
     */
    fun disable() {
        disabled = true
    }

    /**
     * Enables logging of this group.
     */
    fun enable() {
        disabled = false
    }

    private fun createLogTag(): String {
        val trace = Thread.currentThread().stackTrace[Lc.STACK_TRACE_CODE_DEPTH + 3]
        return "${trace.fileName}:${trace.lineNumber}"
    }

    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    //AvoidCatchingThrowable: it is needed to safety format message
    private fun createFormattedMessage(message: String?, vararg args: Any): String? =
            try {
                if (args.isNotEmpty() && message == null) {
                    throw ShouldNotHappenException("Args are not empty but format message is null")
                }
                message?.let { if (args.isNotEmpty()) String.format(message, *args) else message }
            } catch (formattingException: Throwable) {
                Lc.assertion(formattingException)
                null
            }

    private fun createLogMessage(formattedMessage: String?): String = DATE_TIME_FORMATTER.get()
            .let { formatter ->
                formatter?.format(System.currentTimeMillis()) ?: throw ShouldNotHappenException("Formatter is null")
            }
            .plus(" ${Thread.currentThread().name}")
            .plus(" $name")
            .plus(formattedMessage?.let { " $formattedMessage" }.orEmpty())

    private fun logMessage(
            logLevel: LcLevel,
            message: String,
            throwable: Throwable?,
            vararg args: Any
    ) {
        if (disabled || logLevel.lessThan(Lc.logProcessor.minLogLevel)) return

        if (throwable == null && args.isNotEmpty() && args[0] is Throwable) {
            Lc.w("Maybe you've misplaced exception with first format arg? format: %s; arg: %s", message, args[0])
        }

        val formattedMessage = createFormattedMessage(message, *args)
        if (logLevel == LcLevel.ASSERT && Lc.isCrashOnAssertions) {
            throw createAssertion(formattedMessage, throwable)
        }

        Lc.logProcessor.processLogMessage(this, logLevel, createLogTag(), createLogMessage(formattedMessage), throwable)
    }

    private fun createAssertion(message: String?, exception: Throwable?): ShouldNotHappenException = when {
        exception != null && message != null -> ShouldNotHappenException(message, exception)
        exception != null && exception is ShouldNotHappenException -> exception
        exception != null -> ShouldNotHappenException(exception)
        message != null -> ShouldNotHappenException(message)
        else -> ShouldNotHappenException()
    }

    /**
     * Logs debug message.
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    fun d(message: String, vararg args: Any) {
        logMessage(LcLevel.DEBUG, message, null, *args)
    }

    /**
     * Logs debug message.
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    fun d(throwable: Throwable, message: String, vararg args: Any) {
        logMessage(LcLevel.DEBUG, message, throwable, *args)
    }

    /**
     * Logs info message.
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    fun i(message: String, vararg args: Any) {
        logMessage(LcLevel.INFO, message, null, *args)
    }

    /**
     * Logs info message.
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    fun i(throwable: Throwable, message: String, vararg args: Any) {
        logMessage(LcLevel.INFO, message, throwable, *args)
    }

    /**
     * Logs warning message.
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    fun w(message: String, vararg args: Any) {
        logMessage(LcLevel.WARN, message, null, *args)
    }

    /**
     * Logs warning message.
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    fun w(throwable: Throwable, message: String, vararg args: Any) {
        logMessage(LcLevel.WARN, message, throwable, *args)
    }

    /**
     * Logs error message.
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    fun e(message: String, vararg args: Any) {
        logMessage(LcLevel.ERROR, message, null, *args)
    }

    /**
     * Logs error message.
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    fun e(throwable: Throwable, message: String, vararg args: Any) {
        logMessage(LcLevel.ERROR, message, throwable, *args)
    }

    /**
     * Processes assertion. Normally it will throw [ShouldNotHappenException] and crash app.
     * If it should crash or not is specified at [Lc.isCrashOnAssertions].
     * In some cases crash on assertions should be switched off and assertion should be processed in [LogProcessor].
     * It is useful for example to not crash but log it as handled crash in Crashlitycs in production build.
     *
     * @param message Message that is describing assertion.
     */
    fun assertion(message: String) {
        logMessage(LcLevel.ASSERT, "Assertion appears at %s with message: %s", null, Lc.getCodePoint(null, 2), message)
    }

    /**
     * Processes assertion. Normally it will throw [ShouldNotHappenException] and crash app.
     * If it should crash or not is specified at [Lc.isCrashOnAssertions].
     * In some cases crash on assertions should be switched off and assertion should be processed in [LogProcessor].
     * It is useful for example to not crash but log it as handled crash in Crashlitycs in production build.
     *
     * @param throwable Exception that is describing assertion.
     */
    fun assertion(throwable: Throwable) {
        logMessage(LcLevel.ASSERT, "Assertion appears at %s", throwable, Lc.getCodePoint(null, 2))
    }

}
