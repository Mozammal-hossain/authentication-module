package com.example.authentication.model

data class LoginResponseModel(
    val message: String,
    val token: String,
    val user: User
)