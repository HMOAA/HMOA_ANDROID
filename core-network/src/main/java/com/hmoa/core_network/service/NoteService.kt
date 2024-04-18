package com.hmoa.core_network.service

import ResultResponse
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.NoteDescResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface NoteService {

    @GET("/note")
    suspend fun getNoteAll(@Field("pageNum") pageNum: Int): DataResponseDto<Any>

    @GET("/note/{noteId}")
    suspend fun getNote(@Path("noteId") noteId: Int): ApiResponse<DataResponseDto<NoteDescResponseDto>>

    @DELETE("/note/{noteId}")
    suspend fun deleteNote(noteId: Int): DataResponseDto<Any>

    @PUT("/note/{noteId}/update")
    suspend fun putNote(@Field("content") content: String, @Path("noteId") noteId: Int): DataResponseDto<Any>

    @FormUrlEncoded
    @POST("/note/new")
    suspend fun postNote(
        @Field("content") content: String,
        @Field("noteSubtitle") noteSubtitle: String,
        @Field("noteTitle") noteTitle: String
    ): DataResponseDto<Any>
}