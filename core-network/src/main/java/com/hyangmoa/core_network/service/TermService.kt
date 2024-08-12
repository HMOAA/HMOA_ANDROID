package com.hyangmoa.core_network.service

import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.TermDefaultResponseDto
import com.hyangmoa.core_model.response.TermDescResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TermService {
    @GET("/term")
    suspend fun getTerms(
        @Query("pageNum") page : Int
    ) : DataResponseDto<List<TermDefaultResponseDto>>

    @GET("/term/{termId}")
    suspend fun getTerm(
        @Path("termId") termId : Int
    ) : ApiResponse<DataResponseDto<TermDescResponseDto>>

    @DELETE("/term/{termId}")
    suspend fun delTerm(
        @Path("termId") termId : Int
    ) : DataResponseDto<Any>

    @PUT("term/{termId}/update")
    suspend fun updateTerm(
        @Query("content") content : String,
        @Path("termId") termId : Int,
    ) : DataResponseDto<Any>

    @POST("/term/new")
    suspend fun saveTerm(
        @Query("content") content : String,
        @Query("termEnglishTitle") termEnglishTitle : String,
        @Query("termTitle") termTitle : String
    ) : DataResponseDto<Any>
}