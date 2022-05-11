package ru.touchin.network.blocking

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.touchin.network.utils.getAnnotation

/**
 * Custom [Call] implementation for handling blocking and pending requests.
 * @param callDelegate is delegate of default Call implementation
 */
class BlockingCall(
        private val callDelegate: Call<Any>
) : Call<Any> by callDelegate {

    override fun enqueue(callback: Callback<Any>) {
        val isBlocking = callDelegate.blocking != null

        if (PendingRequestsManager.isPending) {
            PendingRequestsManager.addPendingRequest(
                    call = this,
                    callback = callback,
                    isBlocking = isBlocking
            )
            return
        }

        if (isBlocking) PendingRequestsManager.isPending = true

        callDelegate.enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                callback.onResponse(call, response)

                if (call.blocking != null) {
                    PendingRequestsManager.isPending = false
                    PendingRequestsManager.executePendingRequests()
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                PendingRequestsManager.isPending = false
                callback.onFailure(call, t)

                val (isBlockingInternal, cancelOnFail) = callDelegate.blocking
                        .let { (it != null) to (it?.cancelRequestsOnFail == true) }
                when {
                    isBlockingInternal && cancelOnFail -> PendingRequestsManager.dropPendingRequests()
                    isBlockingInternal -> PendingRequestsManager.executePendingRequests()
                }
            }
        })
    }

    private val Call<Any>.blocking get() = request().getAnnotation(BlockingRequest::class.java)
}
