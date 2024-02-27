package com.hmoa.core_network

import corenetwork.Admin.AdminService
import corenetwork.Admin.AdminServiceImpl
import corenetwork.Brand.BrandService
import corenetwork.Brand.BrandServiceImpl
import corenetwork.BrandHPedia.BrandHPediaService
import corenetwork.BrandHPedia.BrandHPediaServiceImpl
import corenetwork.Fcm.FcmService
import corenetwork.Fcm.FcmServiceImpl
import corenetwork.Login.LoginService
import corenetwork.Login.LoginServiceImpl
import corenetwork.Main.MainService
import corenetwork.Main.MainServiceImpl
import corenetwork.Member.MemberService
import corenetwork.Member.MemberServiceImpl
import corenetwork.Note.NoteService
import corenetwork.Note.NoteServiceImpl
import corenetwork.Perfume.PerfumeService
import corenetwork.Perfume.PerfumeServiceImpl
import corenetwork.Search.SearchService
import corenetwork.Search.SearchServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun providePerfumeService(httpClientProvider: HttpClientProvider): PerfumeService =
        PerfumeServiceImpl(httpClientProvider)

    @Singleton
    @Provides
    fun providerFcmService(httpClientProvider: HttpClientProvider): FcmService = FcmServiceImpl(httpClientProvider)

    @Singleton
    @Provides
    fun providerAdminService(httpClientProvider: HttpClientProvider): AdminService =
        AdminServiceImpl(httpClientProvider)

    @Singleton
    @Provides
    fun providerBrandService(httpClientProvider: HttpClientProvider): BrandService =
        BrandServiceImpl(httpClientProvider)

    @Singleton
    @Provides
    fun providerBrandHPediaService(httpClientProvider: HttpClientProvider): BrandHPediaService =
        BrandHPediaServiceImpl(httpClientProvider)

    @Singleton
    @Provides
    fun providerLoginService(httpClientProvider: HttpClientProvider): LoginService =
        LoginServiceImpl(httpClientProvider)

    @Singleton
    @Provides
    fun providerMainService(httpClientProvider: HttpClientProvider): MainService = MainServiceImpl(httpClientProvider)

    @Singleton
    @Provides
    fun providerMemberService(httpClientProvider: HttpClientProvider): MemberService =
        MemberServiceImpl(httpClientProvider)

    @Singleton
    @Provides
    fun providerNoteService(httpClientProvider: HttpClientProvider): NoteService = NoteServiceImpl(httpClientProvider)

    @Singleton
    @Provides
    fun providerSearchService(httpClientProvider: HttpClientProvider): SearchService =
        SearchServiceImpl(httpClientProvider)
}
