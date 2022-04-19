package ru.touchin.network.blocking

import retrofit2.Call
import retrofit2.Callback
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Manager that holds the list of requests and provides methods to interact with them.
 * Provided as a singleton and can be used to prevent sending requests right away
 * e.g. via [BlockingRequest].
 */
object PendingRequestsManager {

    //Using ReentrantLock to avoid concurrency pitfalls when interacting with pendingRequests
    private val pendingRequestsLock = ReentrantLock()

    private val pendingRequests = mutableListOf<Pair<Call<Any>, Callback<Any>>>()

    private val internalAtomicPending = AtomicBoolean(false)

    /**
     * Flag that show if requests should be stock in [pendingRequests] or be enqueued right away
     * Wrapper of atomic [internalAtomicPending]
     */
    var isPending: Boolean
        get() = internalAtomicPending.get()
        set(value) { internalAtomicPending.set(value) }

    /**
     * Shows how many requests are stock
     */
    fun getPendingRequestsCount() = pendingRequests.count()

    /**
     * Used for adding requests to stock
     * @param call is retrofit method
     * @param callback used to provide actions when requests finished with success or error
     */
    fun addPendingRequest(call: Call<Any>, callback: Callback<Any>) {
        pendingRequestsLock.withLock {
            pendingRequests.add(call to callback)
        }
    }

    /**
     * Used to execute and clear all stocked requests
     */
    fun executePendingRequests() {
        applyActionToPendingRequests { first.enqueue(second) }
    }

    /**
     * Used to cancel and clear all stocked requests
     */
    fun dropPendingRequests() {
        applyActionToPendingRequests { first.cancel() }
    }

    private fun applyActionToPendingRequests(action: Pair<Call<Any>, Callback<Any>>.() -> Unit) {
        pendingRequestsLock.withLock {
            pendingRequests.forEach { it.action() }

            pendingRequests.clear()
        }
    }

}
