package ru.touchin.lifecycle.event

sealed class Event {

    object Loading : Event()

    object Complete : Event()

    data class Error(val throwable: Throwable) : Event()

}
