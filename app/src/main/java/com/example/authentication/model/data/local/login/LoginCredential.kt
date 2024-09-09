package com.example.authentication.model.data.local.login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginCredential(

    @PrimaryKey(autoGenerate = true) val uId: Int = 0,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "token") val token: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "profilePic") val profilePic: String?
)
