package com.example.authentication.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedViewModel @Inject constructor() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    fun setEmail(email: String) {
        _email.value = email
    }

    fun getEmail(): String? {
        return _email.value
    }

}