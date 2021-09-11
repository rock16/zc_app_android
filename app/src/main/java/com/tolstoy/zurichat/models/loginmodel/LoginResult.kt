package com.tolstoy.zurichat.models.loginmodel

data class LoginResult(
    val `data`: Data,
    val message: String,
    val status: Int
)