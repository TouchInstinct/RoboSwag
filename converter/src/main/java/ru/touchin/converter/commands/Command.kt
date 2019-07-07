package ru.touchin.converter.commands

sealed class Command<out T>(open val data: T?) {

    data class Success<out T>(override val data: T? = null) : Command<T>(data)

    data class Set<out T>(override val data: T) : Command<T>(data)

    data class Remove<out T>(override val data: T) : Command<T>(data)

    data class Error<out T>(override val data: T? = null) : Command<T>(data)

}
