package ru.touchin.network.blocking

/**
 * Annotation that is used for methods of retrofit services to tag request as blocking one.
 * It means that every upcoming request will be pended until this method finished.
 * @param cancelRequestsOnFail if true then all pending requests will be canceled
 * if blocking request fails
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class BlockingRequest(val cancelRequestsOnFail: Boolean = false)
