package ru.touchin.network.blocking

import retrofit2.Call
import retrofit2.Callback
import java.util.concurrent.atomic.AtomicBoolean

object PendingRequestsManager {

    val isPending = AtomicBoolean(false)

    private val pendingRequests = mutableListOf<Pair<Call<Any>, Callback<Any>>>()

    fun addPendingRequest(call: Call<Any>, callback: Callback<Any>) {
        pendingRequests.add(call to callback)
    }

    @Synchronized
    fun executePendingRequests() {
        applyActionToPendingRequests { first.enqueue(second) }
    }

    @Synchronized
    fun dropPendingRequests() {
        applyActionToPendingRequests { first.cancel() }
    }

    private fun applyActionToPendingRequests(action: Pair<Call<Any>, Callback<Any>>.() -> Unit) {
        isPending.set(false)

        pendingRequests.forEach { it.action() }

        pendingRequests.clear()
    }

}
