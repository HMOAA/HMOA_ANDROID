package com.hmoa.core_datastore

import com.hmoa.core_datastore.Admin.AdminDataStore
import com.hmoa.core_datastore.Admin.AdminDataStoreImpl
import com.hmoa.core_datastore.Bootpay.BootpayDataStore
import com.hmoa.core_datastore.Bootpay.BootpayDataStoreImpl
import com.hmoa.core_datastore.Brand.BrandDataStore
import com.hmoa.core_datastore.Brand.BrandDataStoreImpl
import com.hmoa.core_datastore.BrandHPedia.BrandHPediaDataStore
import com.hmoa.core_datastore.BrandHPedia.BrandHPediaDataStoreImpl
import com.hmoa.core_datastore.Community.CommunityDataStore
import com.hmoa.core_datastore.Community.CommunityDataStoreImpl
import com.hmoa.core_datastore.CommunityComment.CommunityCommentDataStore
import com.hmoa.core_datastore.CommunityComment.CommunityCommentDataStoreImpl
import com.hmoa.core_datastore.Fcm.FcmLocalDataStore
import com.hmoa.core_datastore.Fcm.FcmLocalDataStoreImpl
import com.hmoa.core_datastore.Fcm.FcmRemoteDataStore
import com.hmoa.core_datastore.Fcm.FcmRemoteDataStoreImpl
import com.hmoa.core_datastore.Hshop.HshopRemoteDataStore
import com.hmoa.core_datastore.Hshop.HshopRemoteDataStoreImpl
import com.hmoa.core_datastore.Login.LoginLocalDataStore
import com.hmoa.core_datastore.Login.LoginLocalDataStoreImpl
import com.hmoa.core_datastore.Login.LoginRemoteDataStore
import com.hmoa.core_datastore.Login.LoginRemoteDataStoreImpl
import com.hmoa.core_datastore.Magazine.MagazineDataStore
import com.hmoa.core_datastore.Magazine.MagazineDataStoreImpl
import com.hmoa.core_datastore.Main.MainDataStore
import com.hmoa.core_datastore.Main.MainDataStoreImpl
import com.hmoa.core_datastore.Member.MemberDataStore
import com.hmoa.core_datastore.Member.MemberDataStoreImpl
import com.hmoa.core_datastore.Note.NoteDataStore
import com.hmoa.core_datastore.Note.NoteDataStoreImpl
import com.hmoa.core_datastore.Perfume.PerfumeDataStore
import com.hmoa.core_datastore.Perfume.PerfumeDataStoreImpl
import com.hmoa.core_datastore.PerfumeComment.PerfumeCommentDataStore
import com.hmoa.core_datastore.PerfumeComment.PerfumeCommentDataStoreImpl
import com.hmoa.core_datastore.Perfumer.PerfumerDataStore
import com.hmoa.core_datastore.Perfumer.PerfumerDataStoreImpl
import com.hmoa.core_datastore.Report.ReportDataStore
import com.hmoa.core_datastore.Report.ReportDataStoreImpl
import com.hmoa.core_datastore.Search.SearchDataStore
import com.hmoa.core_datastore.Search.SearchDataStoreImpl
import com.hmoa.core_datastore.Signup.SignupDataStore
import com.hmoa.core_datastore.Signup.SignupDataStoreImpl
import com.hmoa.core_datastore.Survey.SurveyLocalDataStore
import com.hmoa.core_datastore.Survey.SurveyLocalDataStoreImpl
import com.hmoa.core_datastore.Survey.SurveyRemoteDataStore
import com.hmoa.core_datastore.Survey.SurveyRemoteDataStoreImpl
import com.hmoa.core_datastore.Term.TermDataStore
import com.hmoa.core_datastore.Term.TermDataStoreImpl
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
    fun provideMagazineDataStore(magazineDataStoreImpl: MagazineDataStoreImpl): MagazineDataStore

    @Singleton
    @Binds
    fun provideSurveyRemoteDataStore(surveyRemoteDataStoreImpl: SurveyRemoteDataStoreImpl): SurveyRemoteDataStore

    @Singleton
    @Binds
    fun provideSurveyLocalDataStore(surveyLocalDataStore: SurveyLocalDataStoreImpl): SurveyLocalDataStore

    @Singleton
    @Binds
    fun provideHshopRemoteDataStore(hshopRemoteDataStoreImpl: HshopRemoteDataStoreImpl): HshopRemoteDataStore

    @Singleton
    @Binds
    fun provideBootpayDataStore(bootpayDataStoreImpl: BootpayDataStoreImpl): BootpayDataStore
}