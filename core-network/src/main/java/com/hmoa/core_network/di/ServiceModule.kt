package com.hmoa.core_network.di

import com.google.gson.GsonBuilder
import com.hmoa.core_database.TokenManager
import com.hmoa.core_network.BuildConfig
import com.hmoa.core_network.service.*
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(headerInterceptor: Interceptor, authenticator: Authenticator): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        okHttpClientBuilder.authenticator(authenticator)
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideHeaderInterceptor(tokenManager: TokenManager): Interceptor {
        val token = runBlocking {
            tokenManager.getAuthToken().firstOrNull()
        }
        return Interceptor { chain ->
            with(chain) {
                val newRequest = request().newBuilder()
                    .addHeader("X-AUTH-TOKEN", "${token}")
                    .build()
                proceed(newRequest)
            }
        }
    }

    @Singleton
    @Provides
    fun providePerfumeService(retrofit: Retrofit): PerfumeService {
        return retrofit.create(PerfumeService::class.java)
    }

    @Singleton
    @Provides
    fun providePerfumerService(retrofit: Retrofit): PerfumerService {
        return retrofit.create(PerfumerService::class.java)
    }

    @Singleton
    @Provides
    fun providePerfumeCommentService(retrofit: Retrofit): PerfumeCommentService {
        return retrofit.create(PerfumeCommentService::class.java)
    }

    @Singleton
    @Provides
    fun providerFcmService(retrofit: Retrofit): FcmService {
        return retrofit.create(FcmService::class.java)
    }

    @Singleton
    @Provides
    fun providerAdminService(retrofit: Retrofit): AdminService {
        return retrofit.create(AdminService::class.java)
    }

    @Singleton
    @Provides
    fun providerBrandService(retrofit: Retrofit): BrandService {
        return retrofit.create(BrandService::class.java)
    }

    @Singleton
    @Provides
    fun providerBrandHPediaService(retrofit: Retrofit): BrandHPediaService {
        return retrofit.create(BrandHPediaService::class.java)
    }

    @Singleton
    @Provides
    fun providerLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Singleton
    @Provides
    fun providerMainService(retrofit: Retrofit): MainService {
        return retrofit.create(MainService::class.java)
    }

    @Singleton
    @Provides
    fun providerMemberService(retrofit: Retrofit): MemberService {
        return retrofit.create(MemberService::class.java)
    }

    @Singleton
    @Provides
    fun providerNoteService(retrofit: Retrofit): NoteService {
        return retrofit.create(NoteService::class.java)
    }

    @Singleton
    @Provides
    fun providerSearchService(retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }

    @Singleton
    @Provides
    fun providerReportService(retrofit: Retrofit): ReportService {
        return retrofit.create(ReportService::class.java)
    }
}
