package com.hmoa.core_datastore

import com.hmoa.core_database.SignupInfoManager
import com.hmoa.core_database.SignupInfoManagerImpl
import com.hmoa.core_database.TokenManager
import com.hmoa.core_database.TokenManagerImpl
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