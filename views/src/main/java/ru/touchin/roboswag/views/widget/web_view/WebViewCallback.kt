package ru.touchin.roboswag.views.widget.web_view

import ru.touchin.roboswag.views.widget.web_view.WebViewLoadingState

interface WebViewCallback {

    fun onStateChanged(newState: WebViewLoadingState)

}