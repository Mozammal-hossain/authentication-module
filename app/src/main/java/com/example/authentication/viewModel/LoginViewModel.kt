package com.example.authentication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.model.LoginRequestModel
import com.example.authentication.model.LoginResponseModel
import com.example.authentication.services.network.NetworkModule
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class LoginViewModel : ViewModel() {
    private  val apiService = NetworkModule.api

    /// LiveData for profile state
    private val _profileState = MutableLiveData<LoginResponseModel?>()
    val profileState: LiveData<LoginResponseModel?> get() = _profileState

    /// LiveData for error state
    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState

    fun login(email: String, password: String){
        viewModelScope.launch {
            try {
                val response = apiService.login(
                    LoginRequestModel(
                        FCMToken = "Token1",
                        OS = "Android",
                        email = email,
                        model = "Model1",
                        password = password,
                    )
                )
                Timber.i( "response: $response");

                _profileState.value = response
                _errorState.value = null  // Clear any previous errors

                Timber.i("Login successful: ${response.user}")
            } catch (e: HttpException) {
                // Handle HTTP errors
                _errorState.value = "Server error: ${e.message}"
                Timber.e("HTTP error: ${e.message}")
            } catch (e: IOException) {
                // Handle network errors
                _errorState.value = "Network error: ${e.message}"
                Timber.e("Network error: ${e.message}")
            } catch (e: Exception) {
                // Handle unexpected errors
                _errorState.value = "Unexpected error: ${e.message}"
                Timber.e("Unexpected error: ${e.message}")
            }
        }
    }

}