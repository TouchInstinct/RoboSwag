package ru.touchin.lifecycle.event

sealed class ContentEvent<out T>(open val data: T?) {

    data class Loading<out T>(override val data: T? = null) : ContentEvent<T>(data)

    data class Success<out T>(override val data: T) : ContentEvent<T>(data)

    data class Error<out T>(val throwable: Throwable, override val data: T? = null) : ContentEvent<T>(data)

    data class Complete<out T>(override val data: T? = null) : ContentEvent<T>(data)

    fun <P> transform(transformation: (T?) -> P): ContentEvent<P> {
        return when(this) {
            is Loading -> Loading(transformation(data))
            is Success -> Success(transformation(data))
            is Complete -> Complete(transformation(data))
            is Error -> Error(throwable, transformation(data))
        }
    }

}
