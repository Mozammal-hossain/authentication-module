package com.example.authentication.ui.screen.setPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.model.SetNewPassModel
import com.example.authentication.model.SetNewPassResult
import com.example.authentication.ui.screen.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SetPasswordViewModel @Inject constructor(
    private val setNewPassModel: SetNewPassModel,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {


    private val _setNewPassState = MutableLiveData<SetNewPassResult?>()
    val setNewPassState: LiveData<SetNewPassResult?> get() = _setNewPassState

    /// LiveData for error state
    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState


    private val email = sharedViewModel.getEmail() ?: ""


    fun setPass(password: String, confirmPassword: String) {
        viewModelScope.launch {
            when (val result =
                setNewPassModel.setNewPass(email, password, confirmPassword)) {
                is SetNewPassResult.Success -> {
                    _setNewPassState.value = result
                    _errorState.value = null  // Clear any previous errors
                    Timber.i("Reset successful: ${result.response.message}")
                }

                is SetNewPassResult.Error -> {
                    _errorState.value = result.errorModel.message
                    Timber.e("Reset failed: ${result.errorModel.message}")
                }
            }

        }
    }

    fun resetSetPassState() {
        _setNewPassState.value = null
    }
}