package com.tolstoy.zurichat.data.remoteSource.authentication

import com.tolstoy.zurichat.models.loginmodel.LoginForm
import com.tolstoy.zurichat.models.loginmodel.LoginResult
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/auth/login")
    suspend fun login(@Body loginForm: LoginForm): LoginResult
}