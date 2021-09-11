package com.tolstoy.zurichat.data.remoteSource.authentication

import com.tolstoy.zurichat.models.loginmodel.LoginForm
import com.tolstoy.zurichat.models.loginmodel.LoginResult
import com.tolstoy.zurichat.util.networkutils.State
import com.tolstoy.zurichat.util.safeApiCall
import org.json.JSONObject

class RemoteAuthSource(private val service: LoginService) {
    /**
     * Get an authentication token from the server (zc_core) for a user
     */

    suspend fun loginAndGetToken(loginForm: LoginForm) = safeApiCall(
        call = {makeRequest(loginForm)}
    )

    private suspend fun makeRequest(loginForm: LoginForm): State<LoginResult> {
        val response = service.login(loginForm)
        return State.DataState(response)
    }
}