package com.example.authentication.model.data.remote.login

data class LoginRequestModel(
    val FCMToken: String,
    val OS: String,
    val email: String,
    val model: String,
    val password: String
)