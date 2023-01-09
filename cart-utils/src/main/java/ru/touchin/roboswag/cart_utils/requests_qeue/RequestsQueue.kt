package ru.touchin.roboswag.cart_utils.requests_qeue

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Queue for abstract requests which will be executed one after another
 */
typealias Request<TResponse> = suspend () -> TResponse

class RequestsQueue<TRequest : Request<*>> {

    private val requestChannel = Channel<TRequest>(capacity = Channel.BUFFERED)

    fun initRequestsExecution(
            coroutineScope: CoroutineScope,
            executeRequestAction: suspend (TRequest) -> Unit,
    ) {
        requestChannel
                .consumeAsFlow()
                .onEach { executeRequestAction.invoke(it) }
                .launchIn(coroutineScope)
    }

    fun addToQueue(request: TRequest) {
        requestChannel.trySend(request)
    }

    fun clearQueue() {
        while (hasPendingRequests()) requestChannel.tryReceive()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun hasPendingRequests() = !requestChannel.isEmpty
}
