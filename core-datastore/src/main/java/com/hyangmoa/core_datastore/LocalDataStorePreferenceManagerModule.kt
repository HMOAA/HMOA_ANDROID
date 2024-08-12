package com.hyangmoa.core_datastore

import com.hyangmoa.core_database.SignupInfoManager
import com.hyangmoa.core_database.SignupInfoManagerImpl
import com.hyangmoa.core_database.TokenManager
import com.hyangmoa.core_database.TokenManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataStorePreferenceManagerModule {

    @Binds
    @Singleton
    fun bindTokenManager(tokenManagerImpl: TokenManagerImpl): TokenManager

    @Binds
    @Singleton
    fun bindSignupInfoManager(signupInfoManagerImpl: SignupInfoManagerImpl): SignupInfoManager
}