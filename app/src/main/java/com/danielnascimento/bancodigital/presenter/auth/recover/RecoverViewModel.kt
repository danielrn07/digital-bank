package com.danielnascimento.bancodigital.presenter.auth.recover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.danielnascimento.bancodigital.domain.auth.RecoverUseCase
import com.danielnascimento.bancodigital.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RecoverViewModel @Inject constructor(
    private val recoverUseCase: RecoverUseCase
): ViewModel() {
    fun recover(email: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            recoverUseCase.invoke(email)

            emit(StateView.Success(null))
        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}