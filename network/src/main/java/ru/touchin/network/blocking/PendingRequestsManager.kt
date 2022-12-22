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

    private val pendingRequests = mutableListOf<PendingRequestData>()

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
    fun addPendingRequest(call: Call<Any>, callback: Callback<Any>, isBlocking: Boolean) {
        pendingRequestsLock.withLock {
            pendingRequests.add(PendingRequestData(call, callback, isBlocking))
        }
    }

    /**
     * Used to execute and clear all stocked requests
     */
    fun executePendingRequests() {
        applyActionToPendingRequests { call.enqueue(callback) }
    }

    /**
     * Used to cancel and clear all stocked requests
     */
    fun dropPendingRequests() {
        applyActionToPendingRequests { call.cancel() }
    }

    private fun applyActionToPendingRequests(action: PendingRequestData.() -> Unit) {
        pendingRequestsLock.withLock {
            with (pendingRequests.iterator()) {
                while (hasNext()) {
                    val requestData = next()
                    remove()

                    requestData.action()
                    if (requestData.isBlocking) break
                }
            }
        }
    }

    /**
     * Contains data of stocked requests
     * @param call is retrofit request we want to stock
     * @param callback used to call methods onResponse and onFailure of method
     * @param isBlocking shows if request we add to stock is blocking and all following requests must be pended
     */
    internal class PendingRequestData(
            val call: Call<Any>,
            val callback: Callback<Any>,
            val isBlocking: Boolean = false
    )

}
