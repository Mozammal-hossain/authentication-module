package com.example.authentication.services.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.authentication.model.data.local.login.LoginCredential


@Dao
interface UserDao {
    @Query("SELECT * FROM LoginCredential")
    suspend fun getAll(): List<LoginCredential>?

    @Insert
    suspend fun insertUser(vararg loginCredential: LoginCredential)

    @Update
    suspend fun updateUser(vararg loginCredential: LoginCredential)

    @Query("DELETE FROM LoginCredential")
    suspend fun deleteAll()
}