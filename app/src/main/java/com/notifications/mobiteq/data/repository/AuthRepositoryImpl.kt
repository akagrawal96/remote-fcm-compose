package com.notifications.mobiteq.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.notifications.mobiteq.core.utils.AppResult as AppResult
import com.notifications.mobiteq.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun signInWithGoogle(idToken: String): AppResult<FirebaseUser> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            AppResult.Success(result.user!!)
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

    override suspend fun signOut() = auth.signOut()
}
