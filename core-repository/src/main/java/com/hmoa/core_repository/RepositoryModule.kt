package com.hmoa.core_repository

import com.hmoa.core_repository.Login.LoginRepository
import com.hmoa.core_repository.Login.LoginRepositoryImpl
import com.hmoa.core_repository.Perfume.PerfumeRepository
import com.hmoa.core_repository.Perfume.PerfumeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindLoginRepositoryImpl(repositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    fun bindPerfumeRepositoryImpl(repositoryImpl: PerfumeRepositoryImpl): PerfumeRepository

}