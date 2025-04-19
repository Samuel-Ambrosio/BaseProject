package com.samuelav.domain.model.utils.debug

import org.jetbrains.annotations.NonNls

interface Logger {
    /** Log a verbose message. */
    fun v(@NonNls message: String)

    /** Log a verbose exception and a message. */
    fun v(t: Throwable, @NonNls message: String)

    /** Log a verbose exception. */
    fun v(t: Throwable)

    /** Log a debug message. */
    fun d(@NonNls message: String)

    /** Log a debug exception and a message. */
    fun d(t: Throwable, @NonNls message: String)

    /** Log a debug exception. */
    fun d(t: Throwable)

    /** Log an info message. */
    fun i(@NonNls message: String)

    /** Log an info exception and a message. */
    fun i(t: Throwable, @NonNls message: String)

    /** Log an info exception. */
    fun i(t: Throwable)

    /** Log a warning message. */
    fun w(@NonNls message: String)

    /** Log a warning exception and a message. */
    fun w(t: Throwable?, @NonNls message: String)

    /** Log a warning exception. */
    fun w(t: Throwable?)

    /** Log an error message. */
    fun e(@NonNls message: String)

    /** Log an error exception and a message. */
    fun e(t: Throwable?, @NonNls message: String)

    /** Log an error exception. */
    fun e(t: Throwable?)
}