package com.notifications.mobiteq.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.notifications.mobiteq.core.utils.AppResult as AppResult
import com.notifications.mobiteq.domain.model.AppConfig
import com.notifications.mobiteq.domain.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun saveUser(user: User): AppResult<Unit> {
        return try {
            firestore.collection("users")
                .document(user.uid)
                .set(user)
                .await()
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

    suspend fun userExists(uid: String): Boolean {
        return try {
            val doc = firestore.collection("users")
                .document(uid)
                .get()
                .await()
            doc.exists()
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getAppConfig(): AppResult<AppConfig> {
        return try {
            val doc = firestore.collection("app_config")
                .document("webview")
                .get()
                .await()

            val url = doc.getString("url") ?: ""

            @Suppress("UNCHECKED_CAST")
            val headers = (doc.get("headers") as? Map<String, String>) ?: emptyMap()

            AppResult.Success(AppConfig(url = url, headers = headers))
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}
