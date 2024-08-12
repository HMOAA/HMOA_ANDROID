package com.hyangmoa.core_datastore

import com.hyangmoa.core_datastore.Admin.AdminDataStore
import com.hyangmoa.core_datastore.Admin.AdminDataStoreImpl
import com.hyangmoa.core_datastore.Brand.BrandDataStore
import com.hyangmoa.core_datastore.Brand.BrandDataStoreImpl
import com.hyangmoa.core_datastore.BrandHPedia.BrandHPediaDataStore
import com.hyangmoa.core_datastore.BrandHPedia.BrandHPediaDataStoreImpl
import com.hyangmoa.core_datastore.Community.CommunityDataStore
import com.hyangmoa.core_datastore.Community.CommunityDataStoreImpl
import com.hyangmoa.core_datastore.CommunityComment.CommunityCommentDataStore
import com.hyangmoa.core_datastore.CommunityComment.CommunityCommentDataStoreImpl
import com.hyangmoa.core_datastore.Fcm.FcmLocalDataStore
import com.hyangmoa.core_datastore.Fcm.FcmLocalDataStoreImpl
import com.hyangmoa.core_datastore.Fcm.FcmRemoteDataStore
import com.hyangmoa.core_datastore.Fcm.FcmRemoteDataStoreImpl
import com.hyangmoa.core_datastore.Login.LoginLocalDataStore
import com.hyangmoa.core_datastore.Login.LoginLocalDataStoreImpl
import com.hyangmoa.core_datastore.Login.LoginRemoteDataStore
import com.hyangmoa.core_datastore.Login.LoginRemoteDataStoreImpl
import com.hyangmoa.core_datastore.Magazine.MagazineDataStore
import com.hyangmoa.core_datastore.Magazine.MagazineDataStoreImpl
import com.hyangmoa.core_datastore.Main.MainDataStore
import com.hyangmoa.core_datastore.Main.MainDataStoreImpl
import com.hyangmoa.core_datastore.Member.MemberDataStore
import com.hyangmoa.core_datastore.Member.MemberDataStoreImpl
import com.hyangmoa.core_datastore.Note.NoteDataStore
import com.hyangmoa.core_datastore.Note.NoteDataStoreImpl
import com.hyangmoa.core_datastore.Perfume.PerfumeDataStore
import com.hyangmoa.core_datastore.Perfume.PerfumeDataStoreImpl
import com.hyangmoa.core_datastore.PerfumeComment.PerfumeCommentDataStore
import com.hyangmoa.core_datastore.PerfumeComment.PerfumeCommentDataStoreImpl
import com.hyangmoa.core_datastore.Perfumer.PerfumerDataStore
import com.hyangmoa.core_datastore.Perfumer.PerfumerDataStoreImpl
import com.hyangmoa.core_datastore.Report.ReportDataStore
import com.hyangmoa.core_datastore.Report.ReportDataStoreImpl
import com.hyangmoa.core_datastore.Search.SearchDataStore
import com.hyangmoa.core_datastore.Search.SearchDataStoreImpl
import com.hyangmoa.core_datastore.Signup.SignupDataStore
import com.hyangmoa.core_datastore.Signup.SignupDataStoreImpl
import com.hyangmoa.core_datastore.Term.TermDataStore
import com.hyangmoa.core_datastore.Term.TermDataStoreImpl
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
    fun provideFcmDatastore(fcmDataStoreImpl: FcmRemoteDataStoreImpl): FcmRemoteDataStore

    @Singleton
    @Binds
    fun provideFcmLocalDatastore(fcmLocalDataStoreImpl: FcmLocalDataStoreImpl): FcmLocalDataStore

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
    fun providePerfumeCommentDatastore(perfumeCommentDataStoreImpl: PerfumeCommentDataStoreImpl): PerfumeCommentDataStore

    @Singleton
    @Binds
    fun provideSearchDatastore(searchDataStoreImpl: SearchDataStoreImpl): SearchDataStore

    @Singleton
    @Binds
    fun provideSignupDatastore(signupDataStoreImpl: SignupDataStoreImpl): SignupDataStore

    @Singleton
    @Binds
    fun provideReportDatastore(reportDataStoreImpl: ReportDataStoreImpl): ReportDataStore

    @Singleton
    @Binds
    fun provideTermDataStore(termDataStoreImpl: TermDataStoreImpl): TermDataStore

    @Singleton
    @Binds
    fun provideMagazineDataStore(magazineDataStoreImpl: MagazineDataStoreImpl) : MagazineDataStore

}