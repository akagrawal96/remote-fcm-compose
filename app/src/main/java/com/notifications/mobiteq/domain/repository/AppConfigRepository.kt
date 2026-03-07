package com.notifications.mobiteq.domain.repository

import com.notifications.mobiteq.core.utils.AppResult as AppResult
import com.notifications.mobiteq.domain.model.AppConfig

interface AppConfigRepository {
    suspend fun getAppConfig(): AppResult<AppConfig>
    fun getCachedAppConfig(): AppConfig?
    fun saveAppConfigToCache(config: AppConfig)
    fun clearCache()
}
