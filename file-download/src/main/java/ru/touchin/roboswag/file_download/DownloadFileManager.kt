package ru.touchin.roboswag.file_download

interface DownloadFileManager {

    suspend fun download(fileName: String, fileUri: String, headers: Map<String, String> = emptyMap()): String

}