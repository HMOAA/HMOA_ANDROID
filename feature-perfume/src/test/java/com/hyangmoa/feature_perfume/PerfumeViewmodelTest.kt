package com.hyangmoa.feature_perfume

//
//class PerfumeViewmodelTest {
//
//    private var perfumeViewmodel: PerfumeViewmodel = hiltViewModel()
//    private lateinit var updatePerfumeAge: UpdatePerfumeAgeUseCase
//    private lateinit var updatePerfumeGender: UpdatePerfumeGenderUseCase
//    private lateinit var updatePerfumeWeather: UpdatePerfumeWeatherUseCase
//    private lateinit var getPerfume: GetPerfumeUsecase
//    private lateinit var updateLikePerfumeComment: UpdateLikePerfumeCommentUseCase
//    private lateinit var perfumeRepository: PerfumeRepository
//    private lateinit var reportRepository: ReportRepository
//
//    @BeforeAll()
//    fun setUpViewModel() {
//        updatePerfumeAge = UpdatePerfumeAgeUseCase(perfumeRepository)
//        updatePerfumeGender = UpdatePerfumeGenderUseCase(perfumeRepository)
//        updatePerfumeWeather = UpdatePerfumeWeatherUseCase(perfumeRepository)
//        getPerfume = GetPerfumeUsecase(perfumeRepository)
//        perfumeViewmodel =
//            PerfumeViewmodel(
//                updatePerfumeAge,
//                updatePerfumeGender,
//                updatePerfumeWeather,
//                getPerfume,
//                updateLikePerfumeComment,
//                perfumeRepository,
//                reportRepository
//            )
//    }
//
//
//    @DisplayName("뷰모델이 생성되고 향수정보를 불러오기 전 화면진입한 경우 로딩상태 확인")
//    @Test
//    fun When_beforeinitializePerfume_Expect_ResultLoading() {
//
//        Assertions.assertEquals(PerfumeViewmodel.PerfumeUiState.Loading, perfumeViewmodel.uiState.value)
//    }
//}