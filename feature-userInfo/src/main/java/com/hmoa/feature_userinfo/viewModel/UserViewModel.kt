package com.example.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.response.MemberResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: MemberRepository
) : ViewModel() {

    //Login 여부
    private var _isLogin = flowOf(false)
    val isLogin: Flow<Boolean>
        get() = _isLogin

    //내 정보
    private var _myInfo = flowOf(null)
    val myInfo: Flow<MemberResponseDto?>
        get() = _myInfo
}