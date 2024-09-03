package com.example.authentication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.data.forgotPassword.ForgotPassResModel
import com.example.authentication.model.ForgotPassModel
import com.example.authentication.model.ForgotPassResult
import com.example.authentication.services.network.NetworkService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ForgotPassViewModel @Inject constructor(
    private val forgotPassModel: ForgotPassModel
) : ViewModel() {


    // Email for password reset
    var correctEmail: String = ""

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

                    correctEmail = email;


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