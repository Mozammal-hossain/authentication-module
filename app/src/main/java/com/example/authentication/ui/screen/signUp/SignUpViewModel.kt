package com.example.authentication.ui.screen.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.model.SignUpModel
import com.example.authentication.model.SignUpResult
import com.example.authentication.model.data.remote.signUp.SignUpRequestModel
import com.example.authentication.ui.screen.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpModel: SignUpModel,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {


    /// LiveData for sign up  state
    private val _signUpState = MutableLiveData<SignUpResult.Success?>()
    val signUpState: LiveData<SignUpResult.Success?> get() = _signUpState


    /// LiveData for error state
    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState


    fun signUp(email: String, firstname: String, lastname: String, password: String) {
        val requestBody = SignUpRequestModel(email, firstname, lastname, password)

        Timber.i("In SignUpViewModel: $requestBody")

        viewModelScope.launch {
            when (val result = signUpModel.signUp(requestBody)) {
                is SignUpResult.Success -> {
                    _signUpState.value = result
                    _errorState.value = null
                    sharedViewModel.setEmail(email)

                }

                is SignUpResult.Error -> {
                    _errorState.value = result.error.message
                    _signUpState.value = null
                }
            }
        }
    }

}