package com.hmoa.feature_hbti.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectSpiceViewModel @Inject constructor(
    private val surveyRepository: SurveyRepository
) : ViewModel() {
    private val _selectedSpices = MutableStateFlow<MutableList<String>>(mutableListOf())
    val selectedSpices get() = _selectedSpices.asStateFlow()
    val isEnabledBtn = _selectedSpices.mapLatest{it.isNotEmpty()}

    //선택 태그 전체 삭제
    fun deleteAllTags() = _selectedSpices.update{mutableListOf()}
    //선택 태그 일부 삭제
    fun deleteTag(tag: String) = _selectedSpices.update{it.apply{it.remove(tag)}}
    //서낵 태그 추가
    fun addTag(tag: String) = _selectedSpices.update{it.apply{it.add(tag)}}

    //서버에 선택 태그 정보들 추가
    fun postSurveyResult(){
        viewModelScope.launch{
            /** 여기는 서버에 저장하는 부분
             * API 변경되면 그 때 마저 변경 **/
            Log.d("Survey TAG","서버에 정보 전송 in Select Spice View Model")
        }
    }
}