package com.hmoa.core_datastore.Term

import ResultResponse
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.TermDefaultResponseDto
import com.hmoa.core_model.response.TermDescResponseDto
import com.hmoa.core_network.service.TermService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TermDataStoreImpl @Inject constructor(
    private val termService: TermService
) : TermDataStore {

    override suspend fun getTerms(page: Int): DataResponseDto<List<TermDefaultResponseDto>> {
        return termService.getTerms(page)
    }

    override suspend fun getTerm(termId: Int): ResultResponse<DataResponseDto<TermDescResponseDto>> {
        val result = ResultResponse<DataResponseDto<TermDescResponseDto>>()
        termService.getTerm(termId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun delTerm(termId: Int): DataResponseDto<Any> {
        return termService.delTerm(termId)
    }

    override suspend fun updateTerm(content: String, termId: Int): DataResponseDto<Any> {
        return termService.updateTerm(content, termId)
    }

    override suspend fun saveTerm(
        content: String,
        termEnglishTitle: String,
        termTitle: String
    ): DataResponseDto<Any> {
        return termService.saveTerm(content, termEnglishTitle, termTitle)
    }
}