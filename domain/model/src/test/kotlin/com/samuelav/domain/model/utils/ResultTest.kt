package com.samuelav.domain.model.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ResultTest {
    @Test
    fun `fold runs only the success branch and isNotLoading for a success`() {
        val calls = mutableListOf<String>()

        Result.Success(1).fold<Error, Int>(
            isLoading = { calls += "loading" },
            isNotLoading = { calls += "notLoading" },
            isSuccess = { calls += "success:$it" },
            isFailure = { calls += "failure" },
        )

        assertEquals(listOf("notLoading", "success:1"), calls)
    }

    @Test
    fun `fold runs only the failure branch and isNotLoading for a failure`() {
        val calls = mutableListOf<String>()

        Result.Failure(Error.Network).fold<Error, Int>(
            isLoading = { calls += "loading" },
            isNotLoading = { calls += "notLoading" },
            isSuccess = { calls += "success" },
            isFailure = { calls += "failure:$it" },
        )

        assertEquals(listOf("notLoading", "failure:Network"), calls)
    }

    @Test
    fun `fold runs only isLoading for loading`() {
        val calls = mutableListOf<String>()

        Result.Loading.fold<Error, Int>(
            isLoading = { calls += "loading" },
            isNotLoading = { calls += "notLoading" },
            isSuccess = { calls += "success" },
            isFailure = { calls += "failure" },
        )

        assertEquals(listOf("loading"), calls)
    }

    @Test
    fun `mapSuccess transforms the data and keeps failure and loading untouched`() {
        val success: Result<String, Int> = Result.Success(2)
        val failure: Result<String, Int> = Result.Failure("boom")
        val loading: Result<String, Int> = Result.Loading

        assertEquals(Result.Success("4"), success.mapSuccess { (it * 2).toString() })
        assertEquals(Result.Failure("boom"), failure.mapSuccess { (it * 2).toString() })
        assertEquals(Result.Loading, loading.mapSuccess { (it * 2).toString() })
    }

    @Test
    fun `nullable mapSuccess turns a null mapping into the onNull error`() {
        val success: Result<Error, Int> = Result.Success(1)

        assertEquals(Result.Failure(Error.NotFound), success.mapSuccess<Int, String> { null })
        assertEquals(
            Result.Failure(Error.NoContent),
            success.mapSuccess<Int, String>(onNull = { Error.NoContent }) { null }
        )
        assertEquals(Result.Success("1"), success.mapSuccess<Int, String> { it.toString() })
    }

    @Test
    fun `nullable mapSuccess keeps an existing failure`() {
        val failure: Result<Error, Int> = Result.Failure(Error.Server)

        assertEquals(Result.Failure(Error.Server), failure.mapSuccess<Int, String> { it.toString() })
    }

    @Test
    fun `successOrNull and failureOrNull expose only their own side`() {
        val success: Result<Error, Int> = Result.Success(1)
        val failure: Result<Error, Int> = Result.Failure(Error.Timeout)

        assertEquals(1, success.successOrNull())
        assertNull(success.failureOrNull())
        assertNull(failure.successOrNull())
        assertEquals(Error.Timeout, failure.failureOrNull())
    }

    @Test
    fun `isSuccess is true only for a success`() {
        val nullResult: Result<Error, Int>? = null

        assertTrue(Result.Success(1).isSuccess)
        assertFalse(Result.Failure(Error.Unknown).isSuccess)
        assertFalse(Result.Loading.isSuccess)
        assertFalse(nullResult.isSuccess)
    }

    @Test
    fun `ifSuccess runs the block only for a success and returns the same result`() {
        val seen = mutableListOf<Int>()
        val success: Result<Error, Int> = Result.Success(7)
        val failure: Result<Error, Int> = Result.Failure(Error.Unknown)

        assertEquals(success, success.ifSuccess { seen += it })
        assertEquals(failure, failure.ifSuccess { seen += it })
        assertEquals(listOf(7), seen)
    }

    @Test
    fun `asSuccess and asFailure wrap the receiver`() {
        assertEquals(Result.Success("data"), "data".asSuccess())
        assertEquals(Result.Failure(Error.Forbidden), Error.Forbidden.asFailure())
    }
}
