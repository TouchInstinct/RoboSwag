package ru.touchin.roboswag.pdf_reader.ui.base

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.touchin.roboswag.pdf_reader.viewstate.PdfReaderViewState
import ru.touchin.roboswag.pdf_reader.BaseViewModel
import java.io.File

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM

    protected open fun renderData(state: PdfReaderViewState) {
        when (state) {
            is PdfReaderViewState.ReadingSuccess -> downloadSuccess(state.data)
            is PdfReaderViewState.RenderingSuccess -> renderSuccess(state.data)
            is PdfReaderViewState.Error -> renderError(state.error)
            is PdfReaderViewState.Loading -> setLoading(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            renderData(state)
        }

    }

    protected open fun downloadSuccess(file: File) {
        setLoading(false)
    }

    protected open fun renderSuccess(bitmap: Bitmap) {
        setLoading(false)
    }

    protected open fun renderError(error: Throwable) {
        setLoading(false)
        error.message?.let { showMessage(it) }
    }

    protected open fun renderMessage(message: String) {
        setLoading(false)
        showMessage(message)
    }

    protected open fun setLoading(isLoading: Boolean) {
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_LONG
        ).show()
    }

}