package com.danielnascimento.bancodigital.domain.auth

import com.danielnascimento.bancodigital.data.repository.auth.AuthFirebaseDataSourceImpl

class RecoverUseCase(
    private val authFirebaseDataSourceImpl: AuthFirebaseDataSourceImpl
) {
    suspend operator fun invoke(email: String) {
        return authFirebaseDataSourceImpl.recover(email)
    }
}