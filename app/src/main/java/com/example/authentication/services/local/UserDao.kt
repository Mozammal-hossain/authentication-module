package com.example.authentication.services.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.authentication.model.data.local.login.LoginCredential


@Dao
interface UserDao {

    @Query("SELECT * FROM LoginCredential")
    suspend fun getAll(): List<LoginCredential>?

    @Query("SELECT * FROM LoginCredential WHERE id = :id")
    suspend fun getUserById(id: String): LoginCredential?

    @Upsert
    suspend fun upsertUser(vararg loginCredential: LoginCredential)

    @Query("UPDATE LoginCredential SET profilePic = :newProfilePic WHERE id = :id")
    suspend fun updateProfilePicById(id: String, newProfilePic: String)

    @Query("DELETE FROM LoginCredential")
    suspend fun deleteAll()

}