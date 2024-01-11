package com.example.beginvegan.src.ui.viewModel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.beginvegan.src.data.repository.auth.AuthRepository
import java.lang.IllegalArgumentException

class LoginViewModelFactory(
    private val authRepository: AuthRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(authRepository) as T
        }
        throw IllegalArgumentException("LoginViewModel class not found")
    }
}