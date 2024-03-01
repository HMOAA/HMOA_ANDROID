package com.hmoa.core_repository

import com.hmoa.core_repository.Admin.AdminRepository
import com.hmoa.core_repository.Admin.AdminRepositoryImpl
import com.hmoa.core_repository.Brand.BrandRepository
import com.hmoa.core_repository.Brand.BrandRepositoryImpl
import com.hmoa.core_repository.BrandHPedia.BrandHPediaRepository
import com.hmoa.core_repository.BrandHPedia.BrandHPediaRepositoryImpl
import com.hmoa.core_repository.Community.CommunityRepository
import com.hmoa.core_repository.Community.CommunityRepositoryImpl
import com.hmoa.core_repository.CommunityComment.CommunityCommentRepository
import com.hmoa.core_repository.CommunityComment.CommunityCommentRepositoryImpl
import com.hmoa.core_repository.Fcm.FcmRepository
import com.hmoa.core_repository.Fcm.FcmRepositoryImpl
import com.hmoa.core_repository.Login.LoginRepository
import com.hmoa.core_repository.Login.LoginRepositoryImpl
import com.hmoa.core_repository.Main.MainRepository
import com.hmoa.core_repository.Main.MainRepositoryImpl
import com.hmoa.core_repository.Member.MemberRepository
import com.hmoa.core_repository.Member.MemberRepositoryImpl
import com.hmoa.core_repository.Note.NoteRepository
import com.hmoa.core_repository.Note.NoteRepositoryImpl
import com.hmoa.core_repository.Perfume.PerfumeRepository
import com.hmoa.core_repository.Perfume.PerfumeRepositoryImpl
import com.hmoa.core_repository.PerfumeComment.PerfumeCommentRepository
import com.hmoa.core_repository.PerfumeComment.PerfumeCommentRepositoryImpl
import com.hmoa.core_repository.Perfumer.PerfumerRepository
import com.hmoa.core_repository.Perfumer.PerfumerRepositoryImpl
import com.hmoa.core_repository.Search.SearchRepository
import com.hmoa.core_repository.Search.SearchRepositoryImpl
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

}