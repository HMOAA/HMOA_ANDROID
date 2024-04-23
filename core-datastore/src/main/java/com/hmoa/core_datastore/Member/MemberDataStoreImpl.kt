package com.hmoa.core_datastore.Member

import ResultResponse
import com.hmoa.core_datastore.Mapper.transformToMultipartBody
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.MemberResponseDto
import com.hmoa.core_network.service.MemberService
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import java.io.File
import javax.inject.Inject

class MemberDataStoreImpl @Inject constructor(
    private val memberService: MemberService
) : MemberDataStore {
    override suspend fun getMember(): ResultResponse<MemberResponseDto> {
        val result = ResultResponse<MemberResponseDto>()
        memberService.getMember().suspendOnSuccess {
            result.data= this.data
        }.suspendOnError{
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun updateAge(request: AgeRequestDto): DataResponseDto<Any> {
        return memberService.updateAge(request)
    }

    override suspend fun getCommunities(page: Int): ResultResponse<List<CommunityByCategoryResponseDto>> {
        val result = ResultResponse<List<CommunityByCategoryResponseDto>>()
        memberService.getCommunities(page).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun getCommunityComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<List<CommunityCommentDefaultResponseDto>>()
        memberService.getCommunityComments(page).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun deleteMember(): DataResponseDto<Any> {
        return memberService.deleteMember()
    }

    override suspend fun postExistsNickname(request: NickNameRequestDto): ResultResponse<Boolean> {
        val result = ResultResponse<Boolean>()
        memberService.postExistsNickname(request).suspendOnSuccess {
            result.data = false
        }.suspendOnError{
            if (this.statusCode.code == 409){
                result.data = true
            } else {
                result.exception = Exception(this.statusCode.code.toString())
            }
        }
        return result
    }

    override suspend fun updateJoin(request: JoinUpdateRequestDto): ResultResponse<MemberResponseDto> {
        val result = ResultResponse<MemberResponseDto>()
        memberService.updateJoin(request).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun updateNickname(request: NickNameRequestDto): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        memberService.updateNickname(request).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun getCommunityFavoriteComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<List<CommunityCommentDefaultResponseDto>>()
        memberService.getCommunityFavoriteComments(page).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun getPerfumeFavoriteComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<List<CommunityCommentDefaultResponseDto>>()
        memberService.getPerfumeFavoriteComments(page).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun getPerfumeComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<List<CommunityCommentDefaultResponseDto>>()
        memberService.getPerfumeComments(page).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun postProfilePhoto(image: File): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        memberService.postProfilePhoto(image.transformToMultipartBody()).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError{
            result.exception = Exception(this.statusCode.code.toString())
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