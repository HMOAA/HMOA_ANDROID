package com.hmoa.core_network.di

import com.hmoa.core_network.authentication.AuthAuthenticator
import com.hmoa.core_network.authentication.AuthInterceptor
import com.hmoa.core_network.authentication.RefreshTokenManager
import com.hmoa.core_network.authentication.RefreshTokenManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthenticatorModule {

    @Binds
    @Singleton
    fun bindAuthenticatorImpl(authAuthenticator: AuthAuthenticator): Authenticator


    @Binds
    @Singleton
    fun bindAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor


    @Binds
    @Singleton
    fun bindRefreshTokenManager(refreshTokenManagerImpl: RefreshTokenManagerImpl): RefreshTokenManager
}