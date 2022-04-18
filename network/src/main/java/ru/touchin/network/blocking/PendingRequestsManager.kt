package ru.touchin.network.blocking

import retrofit2.Call
import retrofit2.Callback
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object PendingRequestsManager {

    val isPending = AtomicBoolean(false)

    private val pendingRequestsLock = ReentrantLock()

    private val pendingRequests = mutableListOf<Pair<Call<Any>, Callback<Any>>>()

    fun getPendingRequestsCount() = pendingRequests.count()

    fun addPendingRequest(call: Call<Any>, callback: Callback<Any>) {
        pendingRequestsLock.withLock {
            pendingRequests.add(call to callback)
        }
    }

    fun executePendingRequests() {
        applyActionToPendingRequests { first.enqueue(second) }
    }

    fun dropPendingRequests() {
        applyActionToPendingRequests { first.cancel() }
    }

    private fun applyActionToPendingRequests(action: Pair<Call<Any>, Callback<Any>>.() -> Unit) {
        pendingRequestsLock.withLock {
            isPending.set(false)

            pendingRequests.forEach { it.action() }

            pendingRequests.clear()
        }
    }

}
