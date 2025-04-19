package com.samuelav.domain.model.utils.debug

import com.samuelav.domain.model.extensions.isNotNull
import org.jetbrains.annotations.NonNls
import java.time.OffsetDateTime

object AppLogger : Logger {

    private val unsentMessages = mutableListOf<MessageInfo>()

    var logger: Logger? = null
        set(value) {
            field = value
            if (value.isNotNull()) {
                unsentMessages.forEach { (type, time, message, throwable) ->
                    val newMessage = "Stacked message: [$time] $message"
                    if (throwable.isNotNull()) {
                        when (type) {
                            MessageInfo.Type.VERBOSE -> value.v(throwable, newMessage)
                            MessageInfo.Type.DEBUG -> value.d(throwable, newMessage)
                            MessageInfo.Type.INFO -> value.i(throwable, newMessage)
                            MessageInfo.Type.WARNING -> value.w(throwable, newMessage)
                            MessageInfo.Type.ERROR -> value.e(throwable, newMessage)
                        }
                    } else
                        when (type) {
                            MessageInfo.Type.VERBOSE -> value.v(newMessage)
                            MessageInfo.Type.DEBUG -> value.d(newMessage)
                            MessageInfo.Type.INFO -> value.i(newMessage)
                            MessageInfo.Type.WARNING -> value.w(newMessage)
                            MessageInfo.Type.ERROR -> value.e(newMessage)
                        }
                }
                unsentMessages.clear()
            }
        }

    override fun v(@NonNls message: String) {
        logger?.v(message)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.VERBOSE,
                        time = OffsetDateTime.now(),
                        message = message,
                    )
                )
            }
    }

    override fun v(t: Throwable, @NonNls message: String) {
        logger?.v(t, message)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.VERBOSE,
                        time = OffsetDateTime.now(),
                        message = message,
                        throwable = t,
                    )
                )
            }
    }

    override fun v(t: Throwable) {
        logger?.v(t)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.VERBOSE,
                        time = OffsetDateTime.now(),
                        throwable = t,
                    )
                )
            }
    }

    override fun d(@NonNls message: String) {
        logger?.d(message)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.DEBUG,
                        time = OffsetDateTime.now(),
                        message = message,
                    )
                )
            }
    }

    override fun d(t: Throwable, @NonNls message: String) {
        logger?.d(t, message)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.DEBUG,
                        time = OffsetDateTime.now(),
                        message = message,
                        throwable = t,
                    )
                )
            }
    }

    override fun d(t: Throwable) {
        logger?.d(t)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.DEBUG,
                        time = OffsetDateTime.now(),
                        throwable = t,
                    )
                )
            }
    }

    override fun i(@NonNls message: String) {
        logger?.i(message)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.INFO,
                        time = OffsetDateTime.now(),
                        message = message,
                    )
                )
            }
    }

    override fun i(t: Throwable, @NonNls message: String) {
        logger?.i(t, message)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.INFO,
                        time = OffsetDateTime.now(),
                        message = message,
                        throwable = t,
                    )
                )
            }
    }

    override fun i(t: Throwable) {
        logger?.i(t)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.INFO,
                        time = OffsetDateTime.now(),
                        throwable = t,
                    )
                )
            }
    }

    override fun w(@NonNls message: String) {
        logger?.w(message)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.WARNING,
                        time = OffsetDateTime.now(),
                        message = message,
                    )
                )
            }
    }

    override fun w(t: Throwable?, @NonNls message: String) {
        logger?.w(t, message)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.WARNING,
                        time = OffsetDateTime.now(),
                        message = message,
                        throwable = t,
                    )
                )
            }
    }

    override fun w(t: Throwable?) {
        logger?.w(t)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.WARNING,
                        time = OffsetDateTime.now(),
                        throwable = t,
                    )
                )
            }
    }

    override fun e(@NonNls message: String) {
        logger?.e(message)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.ERROR,
                        time = OffsetDateTime.now(),
                        message = message,
                    )
                )
            }
    }

    override fun e(t: Throwable?, @NonNls message: String) {
        logger?.e(t, message)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.ERROR,
                        time = OffsetDateTime.now(),
                        message = message,
                        throwable = t,
                    )
                )
            }
    }

    override fun e(t: Throwable?) {
        logger?.e(t)
            ?: run {
                unsentMessages.add(
                    MessageInfo(
                        type = MessageInfo.Type.ERROR,
                        time = OffsetDateTime.now(),
                        throwable = t,
                    )
                )
            }
    }
}

private data class MessageInfo(
    val type: Type,
    val time: OffsetDateTime,
    val message: String? = null,
    val throwable: Throwable? = null,
) {
    enum class Type {
        VERBOSE,
        DEBUG,
        INFO,
        WARNING,
        ERROR,
    }
}