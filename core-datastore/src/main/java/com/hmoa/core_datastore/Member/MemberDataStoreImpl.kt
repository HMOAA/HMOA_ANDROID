package com.hmoa.core_datastore.Member

import ResultResponse
import com.hmoa.core_datastore.Mapper.transformToMultipartBody
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.MemberResponseDto
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.MemberService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import java.io.File
import javax.inject.Inject

class MemberDataStoreImpl @Inject constructor(
    private val memberService: MemberService,
    private val authenticator: Authenticator
) : MemberDataStore {
    override suspend fun getMember(): ResultResponse<MemberResponseDto> {
        val result = ResultResponse<MemberResponseDto>()
        memberService.getMember()
            .suspendOnSuccess {
                result.data = this.data
            }.suspendOnError {
                authenticator.handleApiError(
                    rawMessage = this.message(),
                    handleErrorMesssage = { result.errorMessage = it },
                    onCompleteTokenRefresh = {
                        memberService.getMember().suspendOnSuccess { result.data = this.data }
                    }
                )
            }
        return result
    }

    override suspend fun updateAge(request: AgeRequestDto): DataResponseDto<Any> {
        return memberService.updateAge(request)
    }

    override suspend fun getCommunities(page: Int): ResultResponse<List<CommunityByCategoryResponseDto>> {
        val result = ResultResponse<List<CommunityByCategoryResponseDto>>()
        memberService.getCommunities(page)
            .suspendOnSuccess {
                result.data = this.data
            }.suspendOnError {
                authenticator.handleApiError(
                    rawMessage = this.message(),
                    handleErrorMesssage = { result.errorMessage = it },
                    onCompleteTokenRefresh = {
                        memberService.getCommunities(page).suspendOnSuccess { result.data = this.data }
                    }
                )
            }
        return result
    }

    override suspend fun getCommunityComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<List<CommunityCommentDefaultResponseDto>>()
        memberService.getCommunityComments(page)
            .suspendOnSuccess {
                result.data = this.data
            }.suspendOnError {
                authenticator.handleApiError(
                    rawMessage = this.message(),
                    handleErrorMesssage = { result.errorMessage = it },
                    onCompleteTokenRefresh = {
                        memberService.getCommunityComments(page).suspendOnSuccess { result.data = this.data }
                    }
                )
            }
        return result
    }

    override suspend fun deleteMember(): DataResponseDto<Any> {
        return memberService.deleteMember()
    }

    override suspend fun postExistsNickname(request: NickNameRequestDto): ResultResponse<Boolean> {
        val result = ResultResponse<Boolean>()
        memberService.postExistsNickname(request)
            .suspendOnSuccess {
                result.data = false
            }.suspendOnError {
                if (this.statusCode.code == 409) {
                    result.data = true
                } else {
                    authenticator.handleApiError(
                        rawMessage = this.message(),
                        handleErrorMesssage = { result.errorMessage = it },
                        onCompleteTokenRefresh = {
                            memberService.postExistsNickname(request).suspendOnSuccess { result.data = this.data }
                        }
                    )
                }
            }
        return result
    }

    override suspend fun updateJoin(request: JoinUpdateRequestDto): ResultResponse<MemberResponseDto> {
        val result = ResultResponse<MemberResponseDto>()
        memberService.updateJoin(request)
            .suspendMapSuccess {
                result.data = this
            }.suspendOnError {
                authenticator.handleApiError(
                    rawMessage = this.message(),
                    handleErrorMesssage = { result.errorMessage = it },
                    onCompleteTokenRefresh = {
                        memberService.updateJoin(request).suspendOnSuccess { result.data = this.data }
                    }
                )
            }
        return result
    }

    override suspend fun updateNickname(request: NickNameRequestDto): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        memberService.updateNickname(request)
            .suspendOnSuccess {
                result.data = this.data
            }.suspendOnError {
                authenticator.handleApiError(
                    rawMessage = this.message(),
                    handleErrorMesssage = { result.errorMessage = it },
                    onCompleteTokenRefresh = {
                        memberService.updateNickname(request).suspendOnSuccess { result.data = this.data }
                    }
                )
            }
        return result
    }

    override suspend fun getCommunityFavoriteComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<List<CommunityCommentDefaultResponseDto>>()
        memberService.getCommunityFavoriteComments(page)
            .suspendMapSuccess {
                result.data = this
            }.suspendOnError {
                authenticator.handleApiError(
                    rawMessage = this.message(),
                    handleErrorMesssage = { result.errorMessage = it },
                    onCompleteTokenRefresh = {
                        memberService.getCommunityFavoriteComments(page).suspendOnSuccess { result.data = this.data }
                    }
                )
            }
        return result
    }

    override suspend fun getPerfumeFavoriteComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<List<CommunityCommentDefaultResponseDto>>()
        memberService.getPerfumeFavoriteComments(page)
            .suspendMapSuccess {
                result.data = this
            }.suspendOnError {
                authenticator.handleApiError(
                    rawMessage = this.message(),
                    handleErrorMesssage = { result.errorMessage = it },
                    onCompleteTokenRefresh = {
                        memberService.getPerfumeFavoriteComments(page).suspendOnSuccess { result.data = this.data }
                    }
                )
            }
        return result
    }

    override suspend fun getPerfumeComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<List<CommunityCommentDefaultResponseDto>>()
        memberService.getPerfumeComments(page)
            .suspendMapSuccess {
                result.data = this
            }.suspendOnError {
                authenticator.handleApiError(
                    rawMessage = this.message(),
                    handleErrorMesssage = { result.errorMessage = it },
                    onCompleteTokenRefresh = {
                        memberService.getPerfumeComments(page).suspendOnSuccess { result.data = this.data }
                    }
                )
            }
        return result
    }

    override suspend fun postProfilePhoto(image: File): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        memberService.postProfilePhoto(image.transformToMultipartBody())
            .suspendOnSuccess {
                result.data = this.data
            }.suspendOnError {
                authenticator.handleApiError(
                    rawMessage = this.message(),
                    handleErrorMesssage = { result.errorMessage = it },
                    onCompleteTokenRefresh = {
                        memberService.postProfilePhoto(image.transformToMultipartBody())
                            .suspendOnSuccess { result.data = this.data }
                    }
                )
            }
        return result
    }

    override suspend fun deleteProfilePhoto(): DataResponseDto<Any> {
        return memberService.deleteProfilePhoto()
    }

    override suspend fun updateSex(request: SexRequestDto): DataResponseDto<Any> {
        return memberService.updateSex(request)
    }
}