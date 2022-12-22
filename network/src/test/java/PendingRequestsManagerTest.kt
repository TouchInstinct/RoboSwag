import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Call
import retrofit2.Callback
import ru.touchin.network.blocking.PendingRequestsManager

@Suppress("UNCHECKED_CAST")
@ObsoleteCoroutinesApi
class PendingRequestsManagerTest {

    @Before
    fun `Clear pending requests list`() {
        PendingRequestsManager.dropPendingRequests()
    }

    @Test
    fun `Assert pending requests add synchronization`() = runBlocking {
        runOnFixedThreadScope {
            1.rangeTo(1000).map { launch { addRequestsAsync() } }.joinAll()
        }

        assertEquals(10000, PendingRequestsManager.getPendingRequestsCount())
    }

    @Test
    fun `Assert pending requests synchronization`() = runBlocking {
        runOnFixedThreadScope {
            repeat(1000) { addRequestsAsync() }

            val executeJob = launch {
                PendingRequestsManager.executePendingRequests()
            }

            val addJobs2 = 1.rangeTo(1000).map { launch {
                addRequestsAsync()
            } }

            executeJob.join()
            addJobs2.joinAll()
        }

        assertEquals(10000, PendingRequestsManager.getPendingRequestsCount())
    }

    private fun addRequestsAsync() {
        repeat(10) {
            PendingRequestsManager.addPendingRequest(
                    call = mock(Call::class.java) as Call<Any>,
                    callback = mock(Callback::class.java) as Callback<Any>
            )
        }
    }

    private suspend fun runOnFixedThreadScope(block: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(newFixedThreadPoolContext(5, "synchronizationPool"))
                .launch { block() }
                .join()
    }
}
