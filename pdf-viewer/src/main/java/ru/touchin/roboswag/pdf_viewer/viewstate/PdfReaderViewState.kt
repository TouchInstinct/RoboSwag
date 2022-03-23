package ru.touchin.roboswag.pdf_reader.viewstate

import android.graphics.Bitmap
import java.io.File

sealed class PdfReaderViewState {
    data class ReadingSuccess(val data: File) : PdfReaderViewState()
    data class RenderingSuccess(val data: Bitmap) : PdfReaderViewState()
    data class Error(val error: Throwable) : PdfReaderViewState()
    data class Loading(val isLoading: Boolean) : PdfReaderViewState()
}