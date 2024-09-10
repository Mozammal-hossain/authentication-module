package com.example.authentication.ui.screen.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.model.SignUpModel
import com.example.authentication.model.SignUpResult
import com.example.authentication.model.data.remote.signUp.SignUpRequestModel
import com.example.authentication.model.data.remote.signUp.SignUpResponseModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val signUpModel: SignUpModel
) : ViewModel() {


    /// LiveData for sign up  state
    private val _signUpState = MutableLiveData<SignUpResponseModel?>()
    val signUpState: LiveData<SignUpResponseModel?> get() = _signUpState


    /// LiveData for error state
    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState


    fun signUp(email: String, firstname: String, lastname: String, password: String) {
        val requestBody = SignUpRequestModel(email, firstname, lastname, password)

        viewModelScope.launch {
            when (val result = signUpModel.signUp(requestBody)) {
                is SignUpResult.Success -> {
                    _signUpState.value = result.response
                    _errorState.value = null
                }

                is SignUpResult.Error -> {
                    _errorState.value = result.error.message
                    _signUpState.value = null
                }
            }
        }
    }

}