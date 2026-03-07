package com.notifications.mobiteq.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.notifications.mobiteq.core.utils.AppResult as AppResult

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun signInWithGoogle(idToken: String): AppResult<FirebaseUser>
    suspend fun signOut()
}
