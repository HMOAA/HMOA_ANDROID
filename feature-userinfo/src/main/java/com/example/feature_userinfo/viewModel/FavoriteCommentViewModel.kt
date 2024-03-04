package com.example.feature_userinfo.viewModel

import com.hmoa.core_repository.Member.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteCommentViewModel @Inject constructor(
    private val repository : MemberRepository
){



}