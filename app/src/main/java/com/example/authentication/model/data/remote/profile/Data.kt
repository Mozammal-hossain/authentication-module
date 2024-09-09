package com.example.authentication.model.data.remote.profile

data class Data(
    val _id: String,
    val avatar: String,
    val email: String,
    val firstname: String,
    val isResetPassVerified: Boolean,
    val isVerified: Boolean,
    val lastname: String,
    val role: String
)