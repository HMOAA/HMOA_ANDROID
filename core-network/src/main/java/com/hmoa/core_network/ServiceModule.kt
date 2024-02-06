package com.hmoa.core_network

import com.hmoa.core_network.Admin.AdminService
import com.hmoa.core_network.Admin.AdminServiceImpl
import com.hmoa.core_network.Brand.BrandService
import com.hmoa.core_network.Brand.BrandServiceImpl
import com.hmoa.core_network.BrandHPedia.BrandHPediaService
import com.hmoa.core_network.BrandHPedia.BrandHPediaServiceImpl
import com.hmoa.core_network.Fcm.FcmService
import com.hmoa.core_network.Fcm.FcmServiceImpl
import com.hmoa.core_network.Login.LoginService
import com.hmoa.core_network.Login.LoginServiceImpl
import com.hmoa.core_network.Main.MainService
import com.hmoa.core_network.Main.MainServiceImpl
import com.hmoa.core_network.Member.MemberService
import com.hmoa.core_network.Member.MemberServiceImpl
import com.hmoa.core_network.Note.NoteService
import com.hmoa.core_network.Note.NoteServiceImpl
import com.hmoa.core_network.Search.SearchService
import com.hmoa.core_network.Search.SearchServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun provideKtorHttpClient(): HttpClient {

        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
//                url(TODO("util모듈 함수로 core-network-secret.json에서 값 추출해서 붙이기"))
                header(HttpHeaders.ContentType, ContentType.Application.Json)
//                header("X-AUTH-TOKEN", TODO("authentication 모듈에서 토큰을 주입해야 함"))
            }
            install(ContentNegotiation) {
                json()
            }
            install(HttpCache) {
//                TODO("캐쉬 추가 설정 필요")
            }
        }
    }

    @Singleton
    @Provides
    fun providePerfumeService(httpClient: HttpClient): PerfumeService = PerfumeServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerFcmService(httpClient : HttpClient) : FcmService = FcmServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerAdminService(httpClient: HttpClient) : AdminService = AdminServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerBrandService(httpClient : HttpClient) : BrandService = BrandServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerBrandHPediaService(httpClient : HttpClient) : BrandHPediaService = BrandHPediaServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerLoginService(httpClient : HttpClient) : LoginService = LoginServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerMainService(httpClient : HttpClient) : MainService = MainServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerMemberService(httpClient : HttpClient) : MemberService = MemberServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerNoteService(httpClient : HttpClient) : NoteService = NoteServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerSearchService(httpClient : HttpClient) : SearchService = SearchServiceImpl(httpClient)
}