package ru.touchin.network.blocking

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.touchin.network.utils.getAnnotation

class BlockingCall(
        private val callDelegate: Call<Any>
) : Call<Any> by callDelegate {

    override fun enqueue(callback: Callback<Any>) {
        if (PendingRequestsManager.isPending.get()) {
            PendingRequestsManager.addPendingRequest(callDelegate, callback)
            return
        }

        val isBlocking = callDelegate.isBlocking()
        if (isBlocking) PendingRequestsManager.isPending.set(true)

        callDelegate.enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                callback.onResponse(call, response)

                if (isBlocking) PendingRequestsManager.executePendingRequests()
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                callback.onFailure(call, t)

                if (isBlocking) PendingRequestsManager.dropPendingRequests()
            }
        })
    }

    private fun Call<Any>.isBlocking() = request().getAnnotation(BlockingRequest::class.java) != null
}
