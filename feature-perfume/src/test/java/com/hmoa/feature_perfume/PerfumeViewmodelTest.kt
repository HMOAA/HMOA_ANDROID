package com.hmoa.feature_perfume

import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_domain.usecase.GetPerfumeUsecase
import com.hmoa.core_domain.usecase.UpdatePerfumeAgeUseCase
import com.hmoa.core_domain.usecase.UpdatePerfumeGenderUseCase
import com.hmoa.core_domain.usecase.UpdatePerfumeWeatherUseCase
import com.hmoa.feature_perfume.viewmodel.PerfumeViewmodel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class PerfumeViewmodelTest {
    private lateinit var updatePerfumeAge: UpdatePerfumeAgeUseCase
    private lateinit var updatePerfumeGender: UpdatePerfumeGenderUseCase
    private lateinit var updatePerfumeWeather: UpdatePerfumeWeatherUseCase
    private lateinit var getPerfume: GetPerfumeUsecase
    private lateinit var perfumeRepository: PerfumeRepository
    private lateinit var perfumeViewmodel: PerfumeViewmodel

    @BeforeAll()
    fun setUpViewModel() {
        updatePerfumeAge = UpdatePerfumeAgeUseCase(perfumeRepository)
        updatePerfumeGender = UpdatePerfumeGenderUseCase(perfumeRepository)
        updatePerfumeWeather = UpdatePerfumeWeatherUseCase(perfumeRepository)
        getPerfume = GetPerfumeUsecase(perfumeRepository)
        perfumeViewmodel =
            PerfumeViewmodel(updatePerfumeAge, updatePerfumeGender, updatePerfumeWeather, getPerfume, perfumeRepository)
    }


    @DisplayName("뷰모델이 생성되고 향수정보를 불러오기 전 화면진입한 경우 로딩상태 확인")
    @Test
    fun When_beforeinitializePerfume_Expect_ResultLoading() {

        Assertions.assertEquals(PerfumeViewmodel.PerfumeUiState.Loading, perfumeViewmodel.uiState.value)
    }
}