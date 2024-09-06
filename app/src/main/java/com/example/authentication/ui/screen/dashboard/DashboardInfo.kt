package com.example.authentication.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import com.example.authentication.model.data.login.LoginCredential
import com.example.authentication.services.local.AppDatabase
import com.example.authentication.services.local.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DashboardInfo @Inject constructor(
    private val database: AppDatabase,
    private val userDao: UserDao,
): ViewModel() {
    suspend fun getUser(): List<LoginCredential>? {
        return userDao.getAll()
    }

}