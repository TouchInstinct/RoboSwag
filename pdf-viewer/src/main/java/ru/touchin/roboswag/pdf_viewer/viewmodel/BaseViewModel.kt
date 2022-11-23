package ru.touchin.roboswag.pdf_reader.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.touchin.roboswag.pdf_reader.viewstate.PdfReaderViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

abstract class BaseViewModel(
) : ViewModel() {

    protected val mStateLiveData = MutableLiveData<PdfReaderViewState>()
    val stateLiveData get() = mStateLiveData as LiveData<PdfReaderViewState>

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected open fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    private fun handleError(error: Throwable) {
        mStateLiveData.postValue(PdfReaderViewState.Error(error))
    }

    protected fun runAsync(block: suspend () -> Unit) =
        viewModelCoroutineScope.launch {
            block()
        }

}