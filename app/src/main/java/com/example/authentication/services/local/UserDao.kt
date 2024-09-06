package com.example.authentication.services.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.authentication.model.data.login.LoginCredential


@Dao
interface UserDao {
    @Query("SELECT * FROM LoginCredential")
    suspend fun getAll(): List<LoginCredential>?

    @Insert
    suspend fun insertUser(vararg loginCredential: LoginCredential)

    @Delete
    suspend fun delete(loginCredential: LoginCredential)
}