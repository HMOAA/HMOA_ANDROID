package com.hmoa.core_database.di

import com.hmoa.core_database.*
import com.hmoa.core_database.lrucache.PerfumeRecommendCacheManager
import com.hmoa.core_database.lrucache.PerfumeRecommendCacheManagerImpl
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

    @Binds
    @Singleton
    fun bindAppSettingManager(appInfoManagerImpl: AppInfoManagerImpl): AppInfoManager

    @Binds
    @Singleton
    fun bindPerfumeRecommendCacheManager(perfumeRecommendCacheManagerImpl: PerfumeRecommendCacheManagerImpl): PerfumeRecommendCacheManager
}