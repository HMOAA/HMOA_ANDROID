package com.example.feature_userinfo.viewModel

import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_repository.Member.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository : MemberRepository
){

    //작성한 게시글
    private var _postList : Flow<List<CommunityByCategoryResponseDto>> = flowOf(emptyList())
    val postList : Flow<List<CommunityByCategoryResponseDto>>
        get() = _postList

}