package com.notifications.mobiteq.data.repository

import com.notifications.mobiteq.core.utils.AppResult as AppResult
import com.notifications.mobiteq.data.datasource.FirestoreDataSource
import com.notifications.mobiteq.data.local.EncryptedPrefsManager
import com.notifications.mobiteq.domain.model.AppConfig
import com.notifications.mobiteq.domain.repository.AppConfigRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfigRepositoryImpl @Inject constructor(
    private val firestoreDataSource: FirestoreDataSource,
    private val encryptedPrefsManager: EncryptedPrefsManager
) : AppConfigRepository {

    override suspend fun getAppConfig(): AppResult<AppConfig> =
        firestoreDataSource.getAppConfig()

    override fun getCachedAppConfig(): AppConfig? =
        encryptedPrefsManager.getAppConfig()

    override fun saveAppConfigToCache(config: AppConfig) =
        encryptedPrefsManager.saveAppConfig(config)

    override fun clearCache() =
        encryptedPrefsManager.clearAll()
}
