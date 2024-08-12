package com.hyangmoa.core_datastore.Term

import ResultResponse
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.TermDefaultResponseDto
import com.hyangmoa.core_model.response.TermDescResponseDto

interface TermDataStore {

    suspend fun getTerms(page : Int) : DataResponseDto<List<TermDefaultResponseDto>>
    suspend fun getTerm(termId : Int) : ResultResponse<DataResponseDto<TermDescResponseDto>>
    suspend fun delTerm(termId : Int) : DataResponseDto<Any>
    suspend fun updateTerm(content : String, termId : Int) : DataResponseDto<Any>
    suspend fun saveTerm(content : String, termEnglishTitle : String, termTitle : String) : DataResponseDto<Any>

}