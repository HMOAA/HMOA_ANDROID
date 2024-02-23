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
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Singleton
    @Provides
    fun providePerfumeService(httpClient: HttpClient): PerfumeService = PerfumeServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerFcmService(httpClient: HttpClient): FcmService = FcmServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerAdminService(httpClient: HttpClient): AdminService = AdminServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerBrandService(httpClient: HttpClient): BrandService = BrandServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerBrandHPediaService(httpClient: HttpClient): BrandHPediaService = BrandHPediaServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerLoginService(httpClient: HttpClient): LoginService = LoginServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerMainService(httpClient: HttpClient): MainService = MainServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerMemberService(httpClient: HttpClient): MemberService = MemberServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerNoteService(httpClient: HttpClient): NoteService = NoteServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerSearchService(httpClient: HttpClient): SearchService = SearchServiceImpl(httpClient)
}
