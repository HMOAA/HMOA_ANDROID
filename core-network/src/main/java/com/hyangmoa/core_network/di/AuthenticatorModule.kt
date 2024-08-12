package com.hyangmoa.core_network.di

import com.hyangmoa.core_network.authentication.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthenticatorModule {

    @Binds
    @Singleton
    fun bindAuthenticatorImpl(authAuthenticator: AuthAuthenticator): Authenticator

    @Binds
    @Singleton
    fun bindRefreshTokenManager(refreshTokenManagerImpl: RefreshTokenManagerImpl): RefreshTokenManager

    @Binds
    @Singleton
    fun bindGoogleServerAuthCodeService(googleServerAuthCodeServiceImpl: GoogleServerAuthCodeServiceImpl): GoogleServerAuthCodeService

}