package com.example.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: MemberRepository
) : ViewModel() {

    //작성한 게시글
    private var _postList: Flow<List<CommunityByCategoryResponseDto>> = flowOf(emptyList())
    val postList: Flow<List<CommunityByCategoryResponseDto>>
        get() = _postList

}