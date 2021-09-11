package com.tolstoy.zurichat.models.loginmodel

import com.tolstoy.zurichat.ui.login.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginState(
    val success: LoggedInUserView? = null,
    val error: Int? = null,
    val loading: Int? = null
)