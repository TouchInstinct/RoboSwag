package ru.touchin.roboswag.core.log_file

import android.content.Context
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LogFileManager {

    enum class Priority(val title: String, val tag: String) {
        VERBOSE("VERBOSE", "*:V"),
        DEBUG("DEBUG", "*:D"),
        INFO("INFO", "*:I"),
        WARNING("WARNING", "*:W"),
        ERROR("ERROR", "*:E"),
        ASSERT("ASSERT", "*:A")
    }

    companion object {
        private const val logDirecroryName = "log"
        const val fileProviderName = ".fileprovider"

        fun getLogDirectory(context: Context) : File{
            val appDirectory = context.getExternalFilesDir(null)
            return File(appDirectory.toString() + "/$logDirecroryName")
        }

        fun saveLogcatToFile(context: Context, priorityTag: String) {
            val logDirectory = initLogDirectory(context)

            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH_mm_ss_SSS", Locale.getDefault())
            val logFile = File(logDirectory, "logcat_${sdf.format(Date())}.txt")

            try {
                Runtime.getRuntime().exec("logcat ${priorityTag} -f $logFile")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        private fun initLogDirectory(context: Context): File {
            val appDirectory = context.getExternalFilesDir(null)
            if (!appDirectory!!.exists()) {
                appDirectory.mkdir()
            }

            val logDirectory = File(appDirectory.toString() + "/$logDirecroryName")
            if (!logDirectory.exists()) {
                logDirectory.mkdir()
            }

            for (file in logDirectory.listFiles()) {
                file.delete()
            }
            return logDirectory
        }
    }
}
