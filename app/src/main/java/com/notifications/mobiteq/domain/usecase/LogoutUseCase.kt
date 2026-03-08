package com.notifications.mobiteq.domain.usecase

import com.notifications.mobiteq.domain.repository.AppConfigRepository
import com.notifications.mobiteq.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val appConfigRepository: AppConfigRepository
) {

    suspend operator fun invoke(){
        authRepository.signOut()
        appConfigRepository.clearCache()
    }




}