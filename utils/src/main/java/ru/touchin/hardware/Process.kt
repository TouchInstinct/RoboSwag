package ru.touchin.hardware

import android.app.ActivityManager
import android.content.Context
import android.os.Process

fun Context.isOnMainProcess(): Boolean {
    val applicationContext = this.applicationContext
    val runningAppProcesses = (applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .runningAppProcesses
    if (runningAppProcesses != null && runningAppProcesses.size != 0) {
        for (runningProcessInfo in runningAppProcesses) {
            val isCurrentProcess = runningProcessInfo.pid == Process.myPid()
            val isMainProcessName = this.packageName == runningProcessInfo.processName
            if (isCurrentProcess && isMainProcessName) {
                return true
            }
        }
    }
    return false
}
