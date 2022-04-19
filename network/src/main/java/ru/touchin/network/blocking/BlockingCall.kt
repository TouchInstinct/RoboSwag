package ru.touchin.network.blocking

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.touchin.network.utils.getAnnotation

class BlockingCall(
        private val callDelegate: Call<Any>
) : Call<Any> by callDelegate {

    override fun enqueue(callback: Callback<Any>) {
        if (PendingRequestsManager.isPending) {
            PendingRequestsManager.addPendingRequest(callDelegate, callback)
            return
        }

        val (isBlocking, cancelOnFail) = callDelegate.blocking()
                .let { (it != null) to (it?.cancelRequestsOnFail == true) }

        if (isBlocking) PendingRequestsManager.isPending = true

        callDelegate.enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                PendingRequestsManager.isPending = false
                callback.onResponse(call, response)

                if (isBlocking) PendingRequestsManager.executePendingRequests()
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                PendingRequestsManager.isPending = false
                callback.onFailure(call, t)

                when {
                    isBlocking && cancelOnFail -> PendingRequestsManager.dropPendingRequests()
                    isBlocking -> PendingRequestsManager.executePendingRequests()
                }
            }
        })
    }

    private fun Call<Any>.blocking() = request().getAnnotation(BlockingRequest::class.java)
}
