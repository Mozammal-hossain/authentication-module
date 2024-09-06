package com.example.authentication.services.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.authentication.model.data.local.login.LoginCredential


@Dao
interface UserDao {
    @Query("SELECT * FROM LoginCredential")
    suspend fun getAll(): List<LoginCredential>?

    @Insert
    suspend fun insertUser(vararg loginCredential: LoginCredential)
}