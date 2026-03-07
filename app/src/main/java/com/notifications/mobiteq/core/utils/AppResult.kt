package com.notifications.mobiteq.core.utils

sealed class AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>()
    data class Error(val exception: Throwable) : AppResult<Nothing>()
    object Loading : AppResult<Nothing>()
}