package com.example.authentication.ui.screen.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.model.ForgotPassResult
import com.example.authentication.model.LogoutModel
import com.example.authentication.model.LogoutResult
import com.example.authentication.model.data.local.login.LoginCredential
import com.example.authentication.services.local.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class DashboardInfoViewModel @Inject constructor(
    private val userDao: UserDao,
    private  val logoutModel: LogoutModel
): ViewModel() {

    /// LiveData for user state
    private val _user = MutableLiveData<LoginCredential?>()
    val userState: LiveData<LoginCredential?> = _user

    /// LiveData for success state
    private  val _logoutSuccessState = MutableLiveData<Boolean?>()
    val logoutSuccessState: LiveData<Boolean?> = _logoutSuccessState


    /// LiveData for error state
    private  val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState


    suspend fun getUser()  {
        val users = userDao.getAll()

        _user.value = users?.get(0);
    }

     fun logout() {

        viewModelScope.launch {
            when (val result = logoutModel.logout()) {
                is LogoutResult.Success -> {
                    _logoutSuccessState.value = true
                    _errorState.value = null  // Clear any previous errors
                    Timber.i("Logout successful: ${result.response}")
                }
                is LogoutResult.Error -> {
                    _errorState.value = result.error.message
                    Timber.e("Logout failed: ${result.error.message}")
                }
            }
        }
    }



}