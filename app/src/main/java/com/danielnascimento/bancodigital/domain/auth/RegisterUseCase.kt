package com.danielnascimento.bancodigital.domain.auth

import com.danielnascimento.bancodigital.data.model.User
import com.danielnascimento.bancodigital.data.repository.auth.AuthFirebaseDataSourceImpl
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authFirebaseDataSourceImpl: AuthFirebaseDataSourceImpl
) {
    suspend operator fun invoke(user: User): User {
        return authFirebaseDataSourceImpl.register(user)
    }
}