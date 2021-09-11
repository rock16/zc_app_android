package com.tolstoy.zurichat.util

import com.tolstoy.zurichat.util.networkutils.NetworkUtils.Companion.resolveError
import com.tolstoy.zurichat.util.networkutils.State
import java.io.IOException

/**
 * Wrap a suspending API [call] in try/catch. In case an exception is thrown, a [State.Error] is
 * created based on the [errorType].
 */
suspend fun <T : Any> safeApiCall(call: suspend () -> State<T>): State<T> {
    return try {
        call()
    } catch (e: Exception) {
        e.printStackTrace()
        // An exception was thrown when calling the API so we're resolving to the type of error
        return resolveError(e)
    }
}