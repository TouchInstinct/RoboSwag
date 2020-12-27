package ru.touchin.roboswag.core.utils;

import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.touchin.roboswag.core.log.Lc;
import ru.touchin.roboswag.core.log.LcGroup;
import ru.touchin.roboswag.core.log.LcLevel;
import ru.touchin.roboswag.core.log.LogProcessor;

public class CrashlyticsLogProcessor extends LogProcessor {

    @NonNull
    private final FirebaseCrashlytics crashlytics;

    public CrashlyticsLogProcessor(@NonNull final FirebaseCrashlytics crashlytics) {
        super(LcLevel.INFO);
        this.crashlytics = crashlytics;
    }

    private String getLogMessage(final int priorityLevel, final String tag, final String message) {
        return "Priority:" + priorityLevel + ' ' + tag + ':' + message;
    }

    @Override
    public void processLogMessage(@NonNull final LcGroup group,
                                  @NonNull final LcLevel level,
                                  @NonNull final String tag,
                                  @NonNull final String message,
                                  @Nullable final Throwable throwable) {
        if (group == LcGroup.UI_LIFECYCLE) {
            crashlytics.log(getLogMessage(level.getPriority(), tag, message));
        } else if (!level.lessThan(LcLevel.ASSERT)
                || (group == LcGroup.API_VALIDATION && level == LcLevel.ERROR)) {
            Log.e(tag, message);
            if (throwable != null) {
                crashlytics.log(getLogMessage(level.getPriority(), tag, message));
                crashlytics.recordException(throwable);
            } else {
                final ShouldNotHappenException exceptionToLog = new ShouldNotHappenException(tag + ':' + message);
                reduceStackTrace(exceptionToLog);
                crashlytics.recordException(exceptionToLog);
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
