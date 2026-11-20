package com.samuelav.domain.repository.utils

import app.cash.turbine.test
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class CachedRemoteResourceTest {
    private val savedValues = mutableListOf<String>()
    private var remoteCalls = 0

    @Test
    fun `returns the cached value without calling remote when it must not be refreshed`() = runTest {
        val result = cachedRemoteResource(
            getFromLocal = { "cached" },
            getFromRemote = { remoteCalls++; Result.Success("remote") },
            saveToLocal = { savedValues += it },
        )

        assertEquals(Result.Success("cached"), result)
        assertEquals(0, remoteCalls)
        assertTrue(savedValues.isEmpty())
    }

    @Test
    fun `fetches, saves and reads local again when the cache is empty`() = runTest {
        val local = ArrayDeque(listOf<String?>(null, "saved"))

        val result = cachedRemoteResource(
            getFromLocal = { local.removeFirst() },
            getFromRemote = { remoteCalls++; Result.Success("remote") },
            saveToLocal = { savedValues += it },
        )

        assertEquals(Result.Success("saved"), result)
        assertEquals(1, remoteCalls)
        assertEquals(listOf("remote"), savedValues)
    }

    @Test
    fun `fetches when shouldFetch asks to refresh the cached value`() = runTest {
        val local = ArrayDeque(listOf<String?>("stale", "fresh"))

        val result = cachedRemoteResource(
            getFromLocal = { local.removeFirst() },
            shouldFetch = { it == "stale" },
            getFromRemote = { Result.Success("fresh") },
            saveToLocal = { savedValues += it },
        )

        assertEquals(Result.Success("fresh"), result)
        assertEquals(listOf("fresh"), savedValues)
    }

    @Test
    fun `returns the remote failure and saves nothing`() = runTest {
        val result = cachedRemoteResource<String>(
            getFromLocal = { null },
            getFromRemote = { Result.Failure(Error.Network) },
            saveToLocal = { savedValues += it },
        )

        assertEquals(Result.Failure(Error.Network), result)
        assertTrue(savedValues.isEmpty())
    }

    @Test
    fun `returns NotFound when local is still empty after saving`() = runTest {
        val result = cachedRemoteResource<String>(
            getFromLocal = { null },
            getFromRemote = { Result.Success("remote") },
            saveToLocal = { savedValues += it },
        )

        assertEquals(Result.Failure(Error.NotFound), result)
    }

    @Test
    fun `maps an unexpected exception to Unknown`() = runTest {
        val result = cachedRemoteResource<String>(
            getFromLocal = { error("disk failure") },
            getFromRemote = { Result.Success("remote") },
            saveToLocal = { savedValues += it },
        )

        assertEquals(Result.Failure(Error.Unknown), result)
    }

    @Test
    fun `rethrows cancellation instead of turning it into an error`() = runTest {
        assertFailsWith<CancellationException> {
            cachedRemoteResource<String>(
                getFromLocal = { throw CancellationException("cancelled") },
                getFromRemote = { Result.Success("remote") },
                saveToLocal = { savedValues += it },
            )
        }
    }

    @Test
    fun `flow emits the cached value and refetches only when the cache becomes empty`() = runTest {
        val local = MutableStateFlow<String?>("cached")

        cachedRemoteResourceFlow(
            getFromLocal = { local },
            getFromRemote = { remoteCalls++; Result.Success("remote") },
            saveToLocal = { local.value = it },
        ).test {
            assertEquals(Result.Success("cached"), awaitItem())

            local.value = null

            assertEquals(Result.Success("remote"), awaitItem())
            assertEquals(1, remoteCalls)
        }
    }

    @Test
    fun `flow emits the remote failure when the cache is empty`() = runTest {
        cachedRemoteResourceFlow<String>(
            getFromLocal = { MutableStateFlow(null) },
            getFromRemote = { Result.Failure(Error.Server) },
            saveToLocal = { savedValues += it },
        ).test {
            assertEquals(Result.Failure(Error.Server), awaitItem())
        }
    }
}
