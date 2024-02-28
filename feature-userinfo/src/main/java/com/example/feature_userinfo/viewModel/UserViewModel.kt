package com.example.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityCommentByMemberResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.MemberResponseDto
import com.hmoa.core_repository.Member.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository : MemberRepository
) : ViewModel() {

    //Login 여부
    private var _isLogin = flowOf(false)
    val isLogin : Flow<Boolean>
        get() = _isLogin

    //좋아요 누른 댓글 목록
    private var _favoriteCommentList : Flow<List<CommunityCommentDefaultResponseDto>> = flowOf(emptyList())
    val favoriteCommentList : Flow<List<CommunityCommentDefaultResponseDto>>
        get() = _favoriteCommentList

    //작성한 댓글
    private var _commentList : Flow<List<CommunityCommentByMemberResponseDto>> = flowOf(emptyList())
    val commentList : Flow<List<CommunityCommentByMemberResponseDto>>
        get() = _commentList

    //작성한 게시글
    private var _postList : Flow<List<CommunityByCategoryResponseDto>> = flowOf(emptyList())
    val postList : Flow<List<CommunityByCategoryResponseDto>>
        get() = _postList

    //내 정보
    private var _myInfo = flowOf(null)
    val myInfo : Flow<MemberResponseDto?>
        get() = _myInfo
}