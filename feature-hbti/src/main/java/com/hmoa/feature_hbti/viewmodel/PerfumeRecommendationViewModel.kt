package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.request.SurveyRespondRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfumeRecommendationViewModel @Inject constructor(
    private val surveyRepository: SurveyRepository
) : ViewModel() {

    // 문답에 대한 결과 (선택지 항목)
    private val _selectedOption = MutableStateFlow<String?>(null)
    val selectedOption get() = _selectedOption.asStateFlow()

    val isEnabledBtn = _selectedOption.map{it != null}

    fun changeOption(changedOption : String?) {_selectedOption.update{ changedOption }}

    //설문 조사 저장
    fun saveSurveyResult(ids: List<Int>){
        viewModelScope.launch{
            /** 여기는 서버에 저장하는 부분
             * API 변경되면 그 때 마저 변경 **/
            val dto = SurveyRespondRequestDto(optionIds = ids)
            surveyRepository.postSurveyResponds(dto)
        }
    }
}