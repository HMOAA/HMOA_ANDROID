package com.hmoa.core_datastore

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.*

private class PerfumeDataStoreImpl : PerfumeDataStore {
    override fun getPerfumeTopDetail(perfumeId: Int): PerfumeDetailResponseDto {
        TODO("Not yet implemented")
    }

    override fun getPerfumeBottomDetail(perfumeId: Int): PerfumeDetailSecondResponseDto {
        TODO("Not yet implemented")
    }

    override fun postPerfumeMainPhoto(imageUrl: String): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun postPerfumeAge(dto: AgeRequestDto, perfumeId: Int): PerfumeAgeResponseDto {
        TODO("Not yet implemented")
    }

    override fun deletePerfumeAge(perfumeId: Int): PerfumeAgeResponseDto {
        TODO("Not yet implemented")
    }

    override fun postPerfumeGender(dto: PerfumeGenderRequestDto, perfumeId: Int): PerfumeGenderResponseDto {
        TODO("Not yet implemented")
    }

    override fun deletePerfumeGender(perfumeId: Int): PerfumeGenderResponseDto {
        TODO("Not yet implemented")
    }

    override fun putPerfumeLike(perfumeId: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun deletePerfumeLike(perfumeId: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun postPerfumeWeather(dto: PerfumeWeatherRequestDto): PerfumeWeatherResponseDto {
        TODO("Not yet implemented")
    }

    override fun deletePerfumeWeather(perfumeId: Int): PerfumeWeatherResponseDto {
        TODO("Not yet implemented")
    }

    override fun getLikePerfumeList(): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

}