package com.hyangmoa.core_repository

import ResultResponse
import com.hyangmoa.core_datastore.Term.TermDataStore
import com.hyangmoa.core_domain.repository.TermRepository
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.TermDefaultResponseDto
import com.hyangmoa.core_model.response.TermDescResponseDto
import javax.inject.Inject

class TermRepositoryImpl @Inject constructor(
    private val termDataStore: TermDataStore
) : TermRepository {

    override suspend fun getTerms(page: Int): DataResponseDto<List<TermDefaultResponseDto>> {
        return termDataStore.getTerms(page)
    }

    override suspend fun getTerm(termId: Int): ResultResponse<DataResponseDto<TermDescResponseDto>> {
        return termDataStore.getTerm(termId)
    }

    override suspend fun delTerm(termId: Int): DataResponseDto<Any> {
        return termDataStore.delTerm(termId)
    }

    override suspend fun updateTerm(content: String, termId: Int): DataResponseDto<Any> {
        return termDataStore.updateTerm(content, termId)
    }

    override suspend fun saveTerm(
        content: String,
        termEnglishTitle: String,
        termTitle: String
    ): DataResponseDto<Any> {
        return termDataStore.saveTerm(content, termEnglishTitle, termTitle)
    }
}