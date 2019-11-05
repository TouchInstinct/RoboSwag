package ru.touchin.roboswag.core.log

import android.util.Log
import com.crashlytics.android.Crashlytics
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

class CrashlyticsLogProcessor(private val crashlytics: Crashlytics) : LogProcessor(LcLevel.INFO) {

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
            !level.lessThan(LcLevel.ASSERT) && level == LcLevel.ERROR -> {
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
