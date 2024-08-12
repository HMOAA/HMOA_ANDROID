package com.hyangmoa.core_datastore.Signup

import com.hyangmoa.core_database.SignupInfoManager
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SignupDataStoreImpl @Inject constructor(private val signupInfoManager: SignupInfoManager) : SignupDataStore {
    override suspend fun getNickname(): String? {
        return signupInfoManager.getNickname().firstOrNull()
    }

    override suspend fun getSex(): String? {
        return signupInfoManager.getSex().firstOrNull()
    }

    override suspend fun getAge(): String? {
        return signupInfoManager.getAge().firstOrNull()
    }

    override suspend fun saveNickname(value: String) {
        signupInfoManager.saveNickname(value)
    }

    override suspend fun saveSex(value: String) {
        signupInfoManager.saveSex(value)
    }

    override suspend fun saveAge(value: String) {
        signupInfoManager.saveAge(value)
    }


}