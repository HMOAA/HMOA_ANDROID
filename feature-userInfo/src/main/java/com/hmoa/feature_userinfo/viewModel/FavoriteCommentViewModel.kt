package com.example.feature_userinfo.viewModel

import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class FavoriteCommentViewModel @Inject constructor(
    private val repository: MemberRepository
) {

    //좋아요 누른 댓글 목록
    private var _favoriteCommentList: Flow<List<CommunityCommentDefaultResponseDto>> = flowOf(emptyList())
    val favoriteCommentList: Flow<List<CommunityCommentDefaultResponseDto>>
        get() = _favoriteCommentList

}