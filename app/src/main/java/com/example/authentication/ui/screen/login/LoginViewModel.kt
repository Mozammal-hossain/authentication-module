package com.example.authentication.ui.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.model.data.login.LoginResponseModel
import com.example.authentication.model.LoginModel
import com.example.authentication.model.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginModel: LoginModel
) : ViewModel() {

    /// LiveData for profile state
    private val _profileState = MutableLiveData<LoginResponseModel?>()
    val profileState: LiveData<LoginResponseModel?> get() = _profileState

    /// LiveData for error state
    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState

    fun login(email: String, password: String){
        viewModelScope.launch {
            when (val result = loginModel.login(email, password)) {
                is LoginResult.Success -> {
                    _profileState.value = result.response
                    _errorState.value = null  // Clear any previous errors
                    Timber.i("Login successful: ${result.response.user}")
                }
                is LoginResult.Error -> {
                    _errorState.value = result.error.message
                    Timber.e("Login failed: ${result.error.message}")
                }
            }
        }
    }

}