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

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import ru.touchin.roboswag.core.utils.ShouldNotHappenException
import java.util.Arrays

/**
 * General logging utility of RoboSwag library.
 * You can initialize [LogProcessor] to intercept log messages and make decision how to show them.
 * Also you can specify assertions behavior to manually make application more stable in production but intercept illegal states in some
 * third-party tool to fix them later but not crash in production.
 */
@SuppressWarnings("checkstyle:methodname", "PMD.ShortMethodName", "PMD.ShortClassName")
//MethodNameCheck,ShortMethodName: log methods better be 1-symbol
object Lc {

    private val GENERAL_LC_GROUP = LcGroup("GENERAL")

    val STACK_TRACE_CODE_DEPTH: Int

    /**
     * Flag to crash application or pass it to [LogProcessor.processLogMessage]
     * on specific [LcGroup.assertion] points of code.
     *
     * @return True if application should crash on assertion.
     */
    var isCrashOnAssertions = true
        private set

    /**
     * Returns [LogProcessor] object to intercept incoming log messages (by default it returns [ConsoleLogProcessor]).
     *
     * @return Specific [LogProcessor].
     */
    var logProcessor: LogProcessor = ConsoleLogProcessor(LcLevel.ERROR)
        private set

    init {
        val stackTrace = Thread.currentThread().stackTrace
        var stackDepth = 0
        while (stackDepth < stackTrace.size) {
            if (stackTrace[stackDepth].className == Lc::class.java.name) {
                break
            }
            stackDepth++
        }
        STACK_TRACE_CODE_DEPTH = stackDepth + 1
    }

    /**
     * Initialize general logging behavior.
     *
     * @param logProcessor      [LogProcessor] to intercept all log messages;
     * @param crashOnAssertions Flag to crash application
     * or pass it to [LogProcessor.processLogMessage]
     * on specific [LcGroup.assertion] points of code.
     */
    fun initialize(logProcessor: LogProcessor, crashOnAssertions: Boolean) {
        isCrashOnAssertions = crashOnAssertions
        Lc.logProcessor = logProcessor
    }

    /**
     * Logs debug message via [.GENERAL_LC_GROUP].
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    fun d(message: String, vararg args: Any) {
        GENERAL_LC_GROUP.d(message, *args)
    }

    /**
     * Logs debug message via [.GENERAL_LC_GROUP].
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    fun d(throwable: Throwable, message: String, vararg args: Any) {
        GENERAL_LC_GROUP.d(throwable, message, *args)
    }

    /**
     * Logs info message via [.GENERAL_LC_GROUP].
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    fun i(message: String, vararg args: Any) {
        GENERAL_LC_GROUP.i(message, *args)
    }

    /**
     * Logs info message via [.GENERAL_LC_GROUP].
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    fun i(throwable: Throwable, message: String, vararg args: Any) {
        GENERAL_LC_GROUP.i(throwable, message, *args)
    }

    /**
     * Logs warning message via [.GENERAL_LC_GROUP].
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    fun w(message: String, vararg args: Any) {
        GENERAL_LC_GROUP.w(message, *args)
    }

    /**
     * Logs warning message via [.GENERAL_LC_GROUP].
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    fun w(throwable: Throwable, message: String, vararg args: Any) {
        GENERAL_LC_GROUP.w(throwable, message, *args)
    }

    /**
     * Logs error message via [.GENERAL_LC_GROUP].
     *
     * @param message Message or format of message to log;
     * @param args    Arguments of formatted message.
     */
    fun e(message: String, vararg args: Any) {
        GENERAL_LC_GROUP.e(message, *args)
    }

    /**
     * Logs error message via [.GENERAL_LC_GROUP].
     *
     * @param throwable Exception to log;
     * @param message   Message or format of message to log;
     * @param args      Arguments of formatted message.
     */
    fun e(throwable: Throwable, message: String, vararg args: Any) {
        GENERAL_LC_GROUP.e(throwable, message, *args)
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
        GENERAL_LC_GROUP.assertion(message)
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
        GENERAL_LC_GROUP.assertion(throwable)
    }

    /**
     * Throws assertion on main thread (to avoid Rx exceptions e.g.) and cuts top causes by type of exception class.
     *
     * @param assertion              Source throwable;
     * @param exceptionsClassesToCut Classes which will be cut from top of causes stack of source throwable.
     */
    @SafeVarargs
    fun cutAssertion(assertion: Throwable, vararg exceptionsClassesToCut: Class<out Throwable>) {
        Handler(Looper.getMainLooper()).post {
            val processedExceptions = arrayListOf<Throwable>()
            var result: Throwable? = assertion
            var exceptionAssignableFromIgnores: Boolean
            do {
                exceptionAssignableFromIgnores = false
                processedExceptions.add(result!!)
                for (exceptionClass in exceptionsClassesToCut) {
                    if (result!!.javaClass.isAssignableFrom(exceptionClass)) {
                        exceptionAssignableFromIgnores = true
                        result = result.cause
                        break
                    }
                }
            } while (exceptionAssignableFromIgnores && result != null && !processedExceptions.contains(result))
            Lc.assertion(result ?: assertion)
        }
    }

    /**
     * Returns line of code from where this method called.
     *
     * @param caller     Object who is calling for code point;
     * @param stackShift caller Shift of stack (e.g. 2 means two elements deeper);
     * @return String represents code point.
     */
    fun getCodePoint(caller: Any?, stackShift: Int = 1): String {
        val traceElement = Thread.currentThread().stackTrace[STACK_TRACE_CODE_DEPTH + stackShift]
        return "${traceElement.methodName}(${traceElement.fileName}:${traceElement.lineNumber})"
                .plus(caller?.let { " of object ${caller.javaClass.simpleName}(${Integer.toHexString(caller.hashCode())})" }.orEmpty())
    }

    /**
     * Returns line of code from where this method called.
     *
     * @param caller Object who is calling for code point;
     * @param methodName String represents lifecycle method in which it was called
     * @return String represents code point.
     */
    fun getCodePoint(caller: Any?, methodName: String): String = methodName
            .plus(caller?.let { " of object ${caller.javaClass.simpleName}" }.orEmpty())

    /**
     * Prints stacktrace in log with specified tag.
     *
     * @param tag Tag to be shown in logs.
     */

    @SuppressLint("LogConditional")
    fun printStackTrace(tag: String) {
        val stackTrace = Thread.currentThread().stackTrace
        if (Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, Arrays.copyOfRange(stackTrace, STACK_TRACE_CODE_DEPTH, stackTrace.size).joinToString(separator = "\n"))
        }
    }

}
