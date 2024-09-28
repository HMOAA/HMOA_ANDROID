package com.hmoa.core_repository

import com.hmoa.core_domain.repository.*
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
    fun bindAdminRepositoryImpl(repositoryImpl: AdminRepositoryImpl): AdminRepository

    @Binds
    @Singleton
    fun bindBrandRepositoryImpl(repositoryImpl: BrandRepositoryImpl): BrandRepository

    @Binds
    @Singleton
    fun bindBrandHPediaRepositoryImpl(repositoryImpl: BrandHPediaRepositoryImpl): BrandHPediaRepository

    @Binds
    @Singleton
    fun bindCommunityRepositoryImpl(repositoryImpl: CommunityRepositoryImpl): CommunityRepository

    @Binds
    @Singleton
    fun bindCommunityCommentRepositoryImpl(repositoryImpl: CommunityCommentRepositoryImpl): CommunityCommentRepository

    @Binds
    @Singleton
    fun bindFcmRepositoryImpl(repositoryImpl: FcmRepositoryImpl): FcmRepository

    @Binds
    @Singleton
    fun bindLoginRepositoryImpl(repositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    fun bindMainRepositoryImpl(repositoryImpl: MainRepositoryImpl): MainRepository

    @Binds
    @Singleton
    fun bindMemberRepositoryImpl(repositoryImpl: MemberRepositoryImpl): MemberRepository

    @Binds
    @Singleton
    fun bindNoteRepositoryImpl(repositoryImpl: NoteRepositoryImpl): NoteRepository

    @Binds
    @Singleton
    fun bindPerfumeRepositoryImpl(repositoryImpl: PerfumeRepositoryImpl): PerfumeRepository

    @Binds
    @Singleton
    fun bindPerfumeCommentRepositoryImpl(repositoryImpl: PerfumeCommentRepositoryImpl): PerfumeCommentRepository

    @Binds
    @Singleton
    fun bindPerfumerRepositoryImpl(repositoryImpl: PerfumerRepositoryImpl): PerfumerRepository

    @Binds
    @Singleton
    fun bindSearchRepositoryImpl(repositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    fun bindSignupRepositoryImpl(repositoryImpl: SignupRepositoryImpl): SignupRepository

    @Binds
    @Singleton
    fun bindReportRepositoryImpl(repositoryImpl: ReportRepositoryImpl): ReportRepository

    @Binds
    @Singleton
    fun bindTermRepositoryImpl(repositoryImpl: TermRepositoryImpl): TermRepository

    @Binds
    @Singleton
    fun bindMagazineRepositoryImpl(repositoryImpl: MagazineRepositoryImpl): MagazineRepository

    @Binds
    @Singleton
    fun bindSurveyRepositoryImpl(repositoryImpl: SurveyRepositoryImpl): SurveyRepository

    @Binds
    @Singleton
    fun bindHshopRepositoryImpl(repositoryImpl: HshopRepositoryImpl):HshopRepository

    @Binds
    @Singleton
    fun bindBootpayRepositoryImpl(repositoryImpl: BootpayRepositoryImpl): BootpayRepository
}