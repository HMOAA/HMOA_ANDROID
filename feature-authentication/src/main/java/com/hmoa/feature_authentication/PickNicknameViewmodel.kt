package com.hmoa.feature_authentication

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.hmoa.core_domain.usecase.CheckNicknameDuplicationUseCase
import com.hmoa.core_domain.usecase.SaveSignupInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class PickNicknameViewmodel @Inject constructor(
    private val checkNicknameDuplicateUseCase: CheckNicknameDuplicationUseCase,
    private val saveSignupInfoUseCase: SaveSignupInfoUseCase
) : ViewModel() {
    var isAvailabeNickname: Flow<Boolean> = flow { false }

    fun saveNickname(nickname: String) {
        saveSignupInfoUseCase.saveNickname(nickname)
    }

    suspend fun checkNicknameDuplication(nickname: String) {
        Log.i(ContentValues.TAG, "viewmodel:postExistsNickname")
        checkNicknameDuplicateUseCase.invoke(nickname)
        //isAvailabeNickname = flow {  }
    }
}