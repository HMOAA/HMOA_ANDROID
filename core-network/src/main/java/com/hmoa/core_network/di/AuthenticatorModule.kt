package com.hmoa.core_network.di

import com.hmoa.core_network.authentication.GoogleServerAuthCodeService
import com.hmoa.core_network.authentication.GoogleServerAuthCodeServiceImpl
import com.hmoa.core_network.authentication.RefreshTokenManager
import com.hmoa.core_network.authentication.RefreshTokenManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthenticatorModule {

    @Binds
    @Singleton
    fun bindRefreshTokenManager(refreshTokenManagerImpl: RefreshTokenManagerImpl): RefreshTokenManager

    @Binds
    @Singleton
    fun bindGoogleServerAuthCodeService(googleServerAuthCodeServiceImpl: GoogleServerAuthCodeServiceImpl): GoogleServerAuthCodeService

}