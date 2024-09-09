package com.example.authentication.model.data.local.login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginCredential(
    @PrimaryKey() val id: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "token") val token: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "profilePic") val profilePic: String?
)
