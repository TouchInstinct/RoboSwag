package ru.touchin.converter.commands

sealed class Command<out T>(open val data: T?) {

    /**
     * Success
     */
    data class Success<out T>(override val data: T? = null) : Command<T>(data)

    /**
     * Fallback
     */
    data class Fallback<out T>(override val data: T? = null) : Command<T>(data)

    /**
     * Error
     */
    data class Error<out T>(override val data: T? = null) : Command<T>(data)

    /**
     * Set [data]
     */
    data class Set<out T>(override val data: T) : Command<T>(data)

    /**
     * Remove last [data]
     */
    data class Remove<out T>(override val data: T) : Command<T>(data)

}
