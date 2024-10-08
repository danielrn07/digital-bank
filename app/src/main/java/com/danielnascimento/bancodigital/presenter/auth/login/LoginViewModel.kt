package com.danielnascimento.bancodigital.presenter.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.danielnascimento.bancodigital.domain.auth.LoginUseCase
import com.danielnascimento.bancodigital.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            loginUseCase.invoke(email, password)

            emit(StateView.Success(null))
        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}