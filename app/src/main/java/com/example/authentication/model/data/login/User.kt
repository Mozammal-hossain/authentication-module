package com.example.authentication.model.data.login

data class User(
    val _id: String,
    val email: String,
    val firstname: String,
    val lastname: String,
    val role: String
)