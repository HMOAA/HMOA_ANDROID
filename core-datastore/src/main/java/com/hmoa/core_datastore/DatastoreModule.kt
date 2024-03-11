package com.hmoa.core_datastore

import com.hmoa.core_datastore.Admin.AdminDataStore
import com.hmoa.core_datastore.Admin.AdminDataStoreImpl
import com.hmoa.core_datastore.Brand.BrandDataStore
import com.hmoa.core_datastore.Brand.BrandDataStoreImpl
import com.hmoa.core_datastore.BrandHPedia.BrandHPediaDataStore
import com.hmoa.core_datastore.BrandHPedia.BrandHPediaDataStoreImpl
import com.hmoa.core_datastore.Community.CommunityDataStore
import com.hmoa.core_datastore.Community.CommunityDataStoreImpl
import com.hmoa.core_datastore.CommunityComment.CommunityCommentDataStore
import com.hmoa.core_datastore.CommunityComment.CommunityCommentDataStoreImpl
import com.hmoa.core_datastore.Fcm.FcmDataStore
import com.hmoa.core_datastore.Fcm.FcmDataStoreImpl
import com.hmoa.core_datastore.Login.LoginLocalDataStore
import com.hmoa.core_datastore.Login.LoginLocalDataStoreImpl
import com.hmoa.core_datastore.Login.LoginRemoteDataStore
import com.hmoa.core_datastore.Login.LoginRemoteDataStoreImpl
import com.hmoa.core_datastore.Main.MainDataStore
import com.hmoa.core_datastore.Main.MainDataStoreImpl
import com.hmoa.core_datastore.Member.MemberDataStore
import com.hmoa.core_datastore.Member.MemberDataStoreImpl
import com.hmoa.core_datastore.Note.NoteDataStore
import com.hmoa.core_datastore.Note.NoteDataStoreImpl
import com.hmoa.core_datastore.Perfume.PerfumeDataStore
import com.hmoa.core_datastore.Perfume.PerfumeDataStoreImpl
import com.hmoa.core_datastore.Perfumer.PerfumerDataStore
import com.hmoa.core_datastore.Perfumer.PerfumerDataStoreImpl
import com.hmoa.core_datastore.Search.SearchDataStore
import com.hmoa.core_datastore.Search.SearchDataStoreImpl
import com.hmoa.core_datastore.Signup.SignupDataStore
import com.hmoa.core_datastore.Signup.SignupDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatastoreModule {
    @Singleton
    @Binds
    fun provideAdminDatastore(adminDataStoreImpl: AdminDataStoreImpl): AdminDataStore

    @Singleton
    @Binds
    fun provideBrandDatastore(brandDataStoreImpl: BrandDataStoreImpl): BrandDataStore

    @Singleton
    @Binds
    fun provideBrandHPediaDatastore(brandHPediaDataStoreImpl: BrandHPediaDataStoreImpl): BrandHPediaDataStore

    @Singleton
    @Binds
    fun provideCommunityDatastore(communityDataStoreImpl: CommunityDataStoreImpl): CommunityDataStore

    @Singleton
    @Binds
    fun provideCommunityCommentDatastore(communityCommentDataStoreImpl: CommunityCommentDataStoreImpl): CommunityCommentDataStore

    @Singleton
    @Binds
    fun provideFcmDatastore(fcmDataStoreImpl: FcmDataStoreImpl): FcmDataStore

    @Singleton
    @Binds
    fun provideLoginRemoteDatastore(loginRemoteDataStoreImpl: LoginRemoteDataStoreImpl): LoginRemoteDataStore

    @Singleton
    @Binds
    fun provideLoginLocalDatastore(loginLocalDataStoreImpl: LoginLocalDataStoreImpl): LoginLocalDataStore

    @Singleton
    @Binds
    fun provideMainDatastore(mainDataStoreImpl: MainDataStoreImpl): MainDataStore

    @Singleton
    @Binds
    fun provideMemberDatastore(memberDataStoreImpl: MemberDataStoreImpl): MemberDataStore

    @Singleton
    @Binds
    fun provideNoteDatastore(noteDataStoreImpl: NoteDataStoreImpl): NoteDataStore

    @Singleton
    @Binds
    fun providePerfumeDatastore(perfumeDataStoreImpl: PerfumeDataStoreImpl): PerfumeDataStore

    @Singleton
    @Binds
    fun providePerfumerDatastore(perfumerDataStoreImpl: PerfumerDataStoreImpl): PerfumerDataStore

    @Singleton
    @Binds
    fun provideSearchDatastore(searchDataStoreImpl: SearchDataStoreImpl): SearchDataStore

    @Singleton
    @Binds
    fun provideSignupDatastore(signupDataStoreImpl: SignupDataStoreImpl): SignupDataStore
}