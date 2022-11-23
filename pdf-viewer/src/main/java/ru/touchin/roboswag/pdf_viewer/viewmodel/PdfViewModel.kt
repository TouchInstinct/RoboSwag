package ru.touchin.roboswag.pdf_reader.viewmodel

import ru.touchin.roboswag.pdf_reader.viewstate.PdfReaderViewState
import ru.touchin.roboswag.pdf_reader.repository.PdfViewRepository

class PdfViewModel(private val repository: PdfViewRepository) : BaseViewModel() {

    fun renderPage(filePath: String, width: Int) {
        mStateLiveData.postValue(PdfReaderViewState.Loading(true))
        runAsync {
            val bitmap = repository.renderSinglePage(filePath, width)
            mStateLiveData.postValue(PdfReaderViewState.RenderingSuccess(bitmap))
        }
    }

}