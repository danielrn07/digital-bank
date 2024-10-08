package com.danielnascimento.bancodigital.presenter.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.danielnascimento.bancodigital.data.model.User
import com.danielnascimento.bancodigital.domain.auth.RegisterUseCase
import com.danielnascimento.bancodigital.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    fun register(user: User) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            registerUseCase.invoke(user)

            emit(StateView.Success(user))
        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}