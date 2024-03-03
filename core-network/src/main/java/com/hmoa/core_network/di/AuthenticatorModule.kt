package com.hmoa.core_network.di

import com.hmoa.core_network.AuthInterceptor
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.authentication.AuthenticatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthenticatorModule {

    @Binds
    @Singleton
    fun bindAuthenticatorImpl(authenticatorImpl: AuthenticatorImpl): Authenticator


    @Binds
    @Singleton
    fun provideAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor
}