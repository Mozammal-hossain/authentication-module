package com.example.authentication.model.data.remote.login

data class LoginResponseModel(
    val message: String,
    val token: String,
    val user: User
)