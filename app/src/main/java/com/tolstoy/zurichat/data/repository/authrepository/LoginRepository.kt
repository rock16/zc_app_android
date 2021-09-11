package com.tolstoy.zurichat.data.repository.authrepository

import com.tolstoy.zurichat.data.remoteSource.authentication.RemoteAuthSource
import com.tolstoy.zurichat.models.User
import com.tolstoy.zurichat.models.loginmodel.LoginForm
import com.tolstoy.zurichat.models.loginmodel.LoginResult
import com.tolstoy.zurichat.models.loginmodel.UserX
import com.tolstoy.zurichat.util.networkutils.State
import org.json.JSONObject

class LoginRepository(private val loginUseCase: RemoteAuthSource) {
    // in-memory cache of the loggedInUser object
    var user: UserX? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        //dataSource.logout()
    }

    suspend fun login(loginForm: LoginForm): State<LoginResult> {
        // handle login
        val result = loginUseCase.loginAndGetToken(loginForm)

        if (result is State.DataState) {
            setLoggedInUser(result.data)
        }
        return result
    }

    private fun setLoggedInUser(loggedInUser: LoginResult) {
        this.user = loggedInUser.data.user
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}