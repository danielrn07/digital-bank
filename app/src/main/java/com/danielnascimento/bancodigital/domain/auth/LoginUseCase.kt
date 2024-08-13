package com.danielnascimento.bancodigital.domain.auth

import com.danielnascimento.bancodigital.data.repository.auth.AuthFirebaseDataSourceImpl

class LoginUseCase(
    private val authFirebaseDataSourceImpl: AuthFirebaseDataSourceImpl
) {
    suspend operator fun invoke(email: String, password: String) {
        return authFirebaseDataSourceImpl.login(email, password)
    }
}