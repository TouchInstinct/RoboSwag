package ru.touchin.roboswag.pdf_reader.repository

import android.graphics.Bitmap
import java.io.File

interface PdfViewRepository {

    suspend fun renderSinglePage(filePath: String, width: Int) : Bitmap

}