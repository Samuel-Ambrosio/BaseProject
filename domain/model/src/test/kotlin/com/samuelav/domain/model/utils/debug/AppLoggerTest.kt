package com.samuelav.domain.model.utils.debug

import org.junit.After
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class AppLoggerTest {
    private val logger = RecordingLogger()

    @After
    fun tearDown() {
        AppLogger.logger = null
    }

    @Test
    fun `messages are delegated straight away when a logger is set`() {
        AppLogger.logger = logger

        AppLogger.d("hello")
        AppLogger.e(IllegalStateException("boom"), "failed")

        assertEquals(listOf("d:hello", "e:failed"), logger.entries.map { "${it.level}:${it.message}" })
    }

    @Test
    fun `messages logged before a logger is set are replayed in order once it is set`() {
        val error = IllegalStateException("boom")

        AppLogger.v("first")
        AppLogger.i("second")
        AppLogger.w(error, "third")
        AppLogger.logger = logger

        assertEquals(listOf("v", "i", "w"), logger.entries.map { it.level })
        assertTrue(logger.entries[0].message.orEmpty().startsWith("Stacked message: ["))
        assertTrue(logger.entries[0].message.orEmpty().endsWith("first"))
        assertSame(error, logger.entries[2].throwable)
    }

    @Test
    fun `replayed messages are not replayed again to the next logger`() {
        AppLogger.d("only once")
        AppLogger.logger = logger
        val nextLogger = RecordingLogger()

        AppLogger.logger = nextLogger

        assertEquals(1, logger.entries.size)
        assertTrue(nextLogger.entries.isEmpty())
    }

    private class RecordingLogger : Logger {
        val entries = mutableListOf<Entry>()

        override fun v(message: String) = record("v", message)
        override fun v(t: Throwable, message: String) = record("v", message, t)
        override fun v(t: Throwable) = record("v", throwable = t)
        override fun d(message: String) = record("d", message)
        override fun d(t: Throwable, message: String) = record("d", message, t)
        override fun d(t: Throwable) = record("d", throwable = t)
        override fun i(message: String) = record("i", message)
        override fun i(t: Throwable, message: String) = record("i", message, t)
        override fun i(t: Throwable) = record("i", throwable = t)
        override fun w(message: String) = record("w", message)
        override fun w(t: Throwable?, message: String) = record("w", message, t)
        override fun w(t: Throwable?) = record("w", throwable = t)
        override fun e(message: String) = record("e", message)
        override fun e(t: Throwable?, message: String) = record("e", message, t)
        override fun e(t: Throwable?) = record("e", throwable = t)

        private fun record(level: String, message: String? = null, throwable: Throwable? = null) {
            entries += Entry(level, message, throwable)
        }
    }

    private data class Entry(val level: String, val message: String?, val throwable: Throwable?)
}
