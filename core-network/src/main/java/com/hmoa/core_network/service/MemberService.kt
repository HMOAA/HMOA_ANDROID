package com.hmoa.core_network.service

import com.hmoa.core_model.data.DefaultAddressDto
import com.hmoa.core_model.data.DefaultOrderInfoDto
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.GetRefundRecordResponseDto
import com.hmoa.core_model.response.MemberResponseDto
import com.hmoa.core_model.response.OrderRecordDto
import com.hmoa.core_model.response.PagingData
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface MemberService {
    @GET("/member")
    suspend fun getMember(): ApiResponse<MemberResponseDto>
    @GET("/member/address")
    suspend fun getAddress(): ApiResponse<DefaultAddressDto>
    @POST("/member/address")
    suspend fun postAddress(@Body request: DefaultAddressDto): ApiResponse<DataResponseDto<Any>>
    @PATCH("/member/age")
    suspend fun updateAge(@Body request: AgeRequestDto): DataResponseDto<Any>
    @GET("/member/communities")
    suspend fun getCommunities(@Query("page") page: Int): ApiResponse<List<CommunityByCategoryResponseDto>>
    @GET("/member/communityComments")
    suspend fun getCommunityComments(@Query("page") page: Int): ApiResponse<List<CommunityCommentDefaultResponseDto>>
    @GET("/member/communityHearts")
    suspend fun getCommunityFavoriteComments(@Query("page") page: Int): ApiResponse<List<CommunityCommentDefaultResponseDto>>
    @DELETE("/member/delete")
    suspend fun deleteMember(): DataResponseDto<Any>
    @POST("/member/existsnickname/")
    suspend fun postExistsNickname(@Body request: NickNameRequestDto): ApiResponse<Boolean>
    @PATCH("/member/join")
    suspend fun updateJoin(@Body request: JoinUpdateRequestDto): ApiResponse<MemberResponseDto>
    @PATCH("/member/nickname")
    suspend fun updateNickname(@Body request: NickNameRequestDto): ApiResponse<DataResponseDto<Any>>
    @GET("/member/order")
    suspend fun getOrder(
        @Query("cursor") cursor: Int
    ): ApiResponse<PagingData<OrderRecordDto>>
    @GET("/member/order/cancel")
    suspend fun getRefundRecord(
        @Query("cursor") cursor: Int
    ): ApiResponse<PagingData<GetRefundRecordResponseDto>>
    @GET("/member/orderInfo")
    suspend fun getOrderInfo(): ApiResponse<DefaultOrderInfoDto>
    @POST("/member/orderInfo")
    suspend fun postOrderInfo(@Body request: DefaultOrderInfoDto): ApiResponse<DataResponseDto<Any>>
    @GET("/member/perfumeComments")
    suspend fun getPerfumeComments(@Query("page") page: Int): ApiResponse<List<CommunityCommentDefaultResponseDto>>
    @GET("/member/perfumeHearts")
    suspend fun getPerfumeFavoriteComments(@Query("page") page: Int): ApiResponse<List<CommunityCommentDefaultResponseDto>>
    @Multipart
    @POST("/member/profile-photo")
    suspend fun postProfilePhoto(@Part image: MultipartBody.Part): ApiResponse<DataResponseDto<Any>>
    @DELETE("/member/profile-photo")
    suspend fun deleteProfilePhoto(): DataResponseDto<Any>
    @PATCH("/member/sex")
    suspend fun updateSex(@Body request: SexRequestDto): DataResponseDto<Any>
}