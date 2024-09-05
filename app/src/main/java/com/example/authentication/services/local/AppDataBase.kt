package com.example.authentication.services.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.authentication.model.data.login.LoginCredential
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
@Database(entities = [LoginCredential::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
