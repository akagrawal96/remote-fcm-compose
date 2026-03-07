package com.notifications.mobiteq.domain.usecase

import com.notifications.mobiteq.core.utils.AppResult
import com.notifications.mobiteq.domain.model.AppConfig
import com.notifications.mobiteq.domain.repository.AppConfigRepository
import javax.inject.Inject

class GetAppConfigUseCase @Inject constructor(
    private val repository: AppConfigRepository
) {
    suspend operator fun invoke(): AppResult<AppConfig> {
        // First try cache
        val cached = repository.getCachedAppConfig()
        if (cached != null) return AppResult.Success(cached)

        // Cache miss — fetch from Firestore and save
        return when (val result = repository.getAppConfig()) {
            is AppResult.Success -> {
                repository.saveAppConfigToCache(result.data)
                AppResult.Success(result.data)
            }
            is AppResult.Error -> AppResult.Error(result.exception)
            is AppResult.Loading -> AppResult.Loading
        }
    }
}
