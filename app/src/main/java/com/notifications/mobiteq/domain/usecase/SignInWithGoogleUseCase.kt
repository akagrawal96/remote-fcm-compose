package com.notifications.mobiteq.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.notifications.mobiteq.core.utils.AppResult
import com.notifications.mobiteq.data.datasource.FirestoreDataSource
import com.notifications.mobiteq.domain.model.User
import com.notifications.mobiteq.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreDataSource: FirestoreDataSource
) {
    suspend operator fun invoke(idToken: String): AppResult<FirebaseUser> {
        return when (val result = authRepository.signInWithGoogle(idToken)) {
            is AppResult.Success -> {
                val firebaseUser = result.data
                // Only save to Firestore if this is a NEW user
                val exists = firestoreDataSource.userExists(firebaseUser.uid)
                if (!exists) {
                    val user = User(
                        uid = firebaseUser.uid,
                        name = firebaseUser.displayName ?: "",
                        email = firebaseUser.email ?: "",
                        photoUrl = firebaseUser.photoUrl?.toString() ?: "",
                        createdAt = System.currentTimeMillis()
                    )
                    firestoreDataSource.saveUser(user)
                }
                AppResult.Success(firebaseUser)
            }
            is AppResult.Error -> AppResult.Error(result.exception)
            is AppResult.Loading -> AppResult.Loading
        }
    }
}