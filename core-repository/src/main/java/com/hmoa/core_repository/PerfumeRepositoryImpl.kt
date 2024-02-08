package com.hmoa.core_repository

import com.hmoa.core_datastore.PerfumeDataStore
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.*

private class PerfumeRepositoryImpl(private val perfumeDataStore: PerfumeDataStore) : PerfumeRepository {
    override fun getPerfumeTopDetail(perfumeId: Int): PerfumeDetailResponseDto {
        return perfumeDataStore.getPerfumeTopDetail(perfumeId)
    }

    override fun getPerfumeBottomDetail(perfumeId: Int): PerfumeDetailSecondResponseDto {
        return perfumeDataStore.getPerfumeBottomDetail(perfumeId)
    }

    override fun postPerfumeMainPhoto(imageUrl: String): DataResponseDto<Any> {
        return perfumeDataStore.postPerfumeMainPhoto(imageUrl)
    }

    override fun postPerfumeAge(dto: AgeRequestDto, perfumeId: Int): PerfumeAgeResponseDto {
        return perfumeDataStore.postPerfumeAge(dto, perfumeId)
    }

    override fun deletePerfumeAge(perfumeId: Int): PerfumeAgeResponseDto {
        return perfumeDataStore.deletePerfumeAge(perfumeId)
    }

    override fun postPerfumeGender(dto: PerfumeGenderRequestDto, perfumeId: Int): PerfumeGenderResponseDto {
        return perfumeDataStore.postPerfumeGender(dto, perfumeId)
    }

    override fun deletePerfumeGender(perfumeId: Int): PerfumeGenderResponseDto {
        return perfumeDataStore.deletePerfumeGender(perfumeId)
    }

    override fun putPerfumeLike(perfumeId: Int): DataResponseDto<Any> {
        return perfumeDataStore.putPerfumeLike(perfumeId)
    }

    override fun deletePerfumeLike(perfumeId: Int): DataResponseDto<Any> {
        return perfumeDataStore.deletePerfumeLike(perfumeId)
    }

    override fun postPerfumeWeather(dto: PerfumeWeatherRequestDto): PerfumeWeatherResponseDto {
        return perfumeDataStore.postPerfumeWeather(dto)
    }

    override fun deletePerfumeWeather(perfumeId: Int): PerfumeWeatherResponseDto {
        return perfumeDataStore.deletePerfumeWeather(perfumeId)
    }

    override fun getLikePerfumeList(): DataResponseDto<Any> {
        return perfumeDataStore.getLikePerfumes()
    }
}