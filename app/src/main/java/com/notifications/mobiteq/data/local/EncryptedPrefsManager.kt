package com.notifications.mobiteq.data.local

import androidx.security.crypto.EncryptedSharedPreferences
import com.notifications.mobiteq.domain.model.AppConfig
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedPrefsManager @Inject constructor(
    private val prefs: EncryptedSharedPreferences
) {
    companion object {
        private const val KEY_URL = "app_url"
        private const val KEY_HEADERS = "app_headers"
    }

    // Save AppConfig to encrypted prefs
    fun saveAppConfig(config: AppConfig) {
        val headersJson = JSONObject(config.headers).toString()
        prefs.edit()
            .putString(KEY_URL, config.url)
            .putString(KEY_HEADERS, headersJson)
            .apply()
    }

    // Read AppConfig from encrypted prefs
    fun getAppConfig(): AppConfig? {
        val url = prefs.getString(KEY_URL, null) ?: return null
        val headersJson = prefs.getString(KEY_HEADERS, null) ?: return null

        return try {
            val jsonObject = JSONObject(headersJson)
            val headers = mutableMapOf<String, String>()
            jsonObject.keys().forEach { key ->
                headers[key] = jsonObject.getString(key)
            }
            AppConfig(url = url, headers = headers)
        } catch (e: Exception) {
            null
        }
    }

    // Clear everything on logout
    fun clearAll() {
        prefs.edit().clear().apply()
    }

    // Check if config is already cached
    fun hasAppConfig(): Boolean {
        return prefs.getString(KEY_URL, null) != null
    }
}
