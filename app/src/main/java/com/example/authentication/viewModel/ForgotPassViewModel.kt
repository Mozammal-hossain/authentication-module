package com.example.authentication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.data.forgotPassword.ForgotPassResModel
import com.example.authentication.model.ForgotPassModel
import com.example.authentication.model.ForgotPassResult
import kotlinx.coroutines.launch
import timber.log.Timber

class ForgotPassViewModel : ViewModel() {
    private val forgotPassModel = ForgotPassModel()

    /// LiveData for forgot password state
    private val _forgotPassState = MutableLiveData<ForgotPassResult? >()
    val forgotPassState: MutableLiveData<ForgotPassResult?> = _forgotPassState


    /// LiveData for error state
    private  val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState


    fun forgotPassword(email: String) {
        viewModelScope.launch {
            when (val result = forgotPassModel.forgotPassword(email)) {
                is ForgotPassResult.Success -> {
                    _forgotPassState.value = result
                    _errorState.value = null


                    Timber.i("Login successful: ${result.response.message}")
                }
                is ForgotPassResult.Error -> {
                    _errorState.value = result.errorModel.message
                    Timber.e("Login failed: ${result.errorModel.message}")
                }
            }
        }
    }

    fun resetForgotPassState() {
        _forgotPassState.value = null
    }


}