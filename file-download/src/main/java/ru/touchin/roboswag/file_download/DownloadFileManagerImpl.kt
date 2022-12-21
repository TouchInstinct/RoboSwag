package ru.touchin.roboswag.file_download

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File

class DownloadFileManagerImpl(
    private val context: Context
) : DownloadFileManager {

    private val downloadManager: DownloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

    override suspend fun download(fileName: String, fileUri: String, headers: Map<String, String>): String {
        val result = withContext(Dispatchers.Default) {
            async {
                downloadWithStatus(fileName, fileUri, headers)
            }
        }
        return result.await()

    }

    private fun downloadWithStatus(fileName: String, fileUri: String, headers: Map<String, String>): String {
        var downloadId: Long
        DownloadManager.Request(Uri.parse(fileUri))
            .apply {
                setTitle(fileName)
                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

                headers.forEach { (key, value) ->
                    addRequestHeader(key, value)
                }

                allowScanningByMediaScanner()
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                downloadId = downloadManager.enqueue(this)
            }
        return getStatus(downloadId, fileUri)

    }

    @SuppressLint("Range")
    private fun getStatus(id: Long, url: String): String {
        val query = DownloadManager.Query().setFilterById(id)
        var status: Int
        var downloading = true
        do {
            val cursor: Cursor = downloadManager.query(query)
            cursor.moveToFirst()
            val statusId = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            if (isFinished(statusId)) {
                downloading = false
            }
            status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            cursor.close()
        } while (downloading)

        return statusMessage(url, File(Environment.DIRECTORY_DOWNLOADS), status)

    }

    private fun statusMessage(url: String, directory: File, status: Int): String {
        val msg = when (status) {
            DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
            DownloadManager.STATUS_PAUSED -> "Paused"
            DownloadManager.STATUS_PENDING -> "Pending"
            DownloadManager.STATUS_RUNNING -> "Downloading..."
            DownloadManager.STATUS_SUCCESSFUL -> "PDF downloaded successfully in $directory" + File.separator + url.substring(
                url.lastIndexOf("/") + 1
            )
            else -> "There's nothing to download"
        }
        return msg

    }

    private fun isFinished(status: Int): Boolean {
        return status == DownloadManager.STATUS_SUCCESSFUL
                || status == DownloadManager.STATUS_FAILED

    }

}
