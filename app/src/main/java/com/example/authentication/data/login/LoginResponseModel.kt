package com.example.authentication.data.login

data class LoginResponseModel(
    val message: String,
    val token: String,
    val user: User
)