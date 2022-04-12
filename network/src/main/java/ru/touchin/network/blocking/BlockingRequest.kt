package ru.touchin.network.blocking

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class BlockingRequest(val abortOnFail: Boolean = false)
