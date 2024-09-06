package com.example.authentication.model

import com.example.authentication.services.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutModel @Inject constructor(
    private val db: AppDatabase
)
{
    private suspend fun resetDb() = withContext(Dispatchers.IO){
        db.runInTransaction{
            runBlocking {
                db.clearAllTables()
            }
        }
    }
}