package com.hmoa.core_network.di

import com.hmoa.core_network.authentication.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthenticationModule {
    @Binds
    @Singleton
    fun bindAuthenticator(authenticatorImpl: AuthenticatorImpl): Authenticator

    @Binds
    @Singleton
    fun bindRefreshTokenManager(refreshTokenManagerImpl: RefreshTokenManagerImpl): RefreshTokenManager

    @Binds
    @Singleton
    fun bindGoogleServerAuthCodeService(googleServerAuthCodeServiceImpl: GoogleServerAuthCodeServiceImpl): GoogleServerAuthCodeService

}