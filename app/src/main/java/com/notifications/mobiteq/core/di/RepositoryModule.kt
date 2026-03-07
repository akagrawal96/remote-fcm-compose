package com.notifications.mobiteq.core.di

import com.notifications.mobiteq.data.repository.AppConfigRepositoryImpl
import com.notifications.mobiteq.data.repository.AuthRepositoryImpl
import com.notifications.mobiteq.domain.repository.AppConfigRepository
import com.notifications.mobiteq.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindAppConfigRepository(impl: AppConfigRepositoryImpl): AppConfigRepository
}
