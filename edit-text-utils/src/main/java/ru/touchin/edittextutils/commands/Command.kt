package ru.touchin.edittextutils.commands

/**
 * Command behavior pattern to dissociate [Verifier] and it's logic.
 * [Verifier] returns [Command] according to basic verification.
 * Interpretation of command and action is up to [VerifierController].
 */
sealed class Command<out T>(open val data: T?) {

    /**
     * Success command. No action required.
     */
    data class Success<out T>(override val data: T? = null) : Command<T>(data)

    /**
     * Fallback command. Return to previous text state.
     */
    data class Fallback<out T>(override val data: T? = null) : Command<T>(data)

    /**
     * Error command. Unrecoverable state of text input.
     */
    data class Error<out T>(override val data: T? = null) : Command<T>(data)

    /**
     * Set command. Set(replace) [data] of [T] type to text input.
     */
    data class Set<out T>(override val data: T) : Command<T>(data)

    /**
     * Remove last [data] amount of symbols from text input.
     */
    data class Remove<out T>(override val data: T) : Command<T>(data)

}
