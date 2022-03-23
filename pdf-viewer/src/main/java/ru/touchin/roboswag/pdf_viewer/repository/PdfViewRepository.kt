package ru.touchin.roboswag.pdf_reader.repository

import android.graphics.Bitmap
import java.io.File

interface PdfViewRepository {

    suspend fun downloadPdf(fileUri: String, fileName: String)

    suspend fun getPdfFromStorage(fileUri: String) : File

    suspend fun renderSinglePage(filePath: String, width: Int) : Bitmap

}