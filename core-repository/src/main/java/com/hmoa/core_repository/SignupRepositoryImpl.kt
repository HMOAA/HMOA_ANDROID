package com.hmoa.core_repository

import com.hmoa.core_datastore.Signup.SignupDataStore
import com.hmoa.core_domain.repository.SignupRepository
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(private val signupDatastore: SignupDataStore) : SignupRepository {
    override suspend fun getNickname(): String? {
        return signupDatastore.getNickname()
    }

    override suspend fun getSex(): String? {
        return signupDatastore.getSex()
    }

    override suspend fun getAge(): String? {
        return signupDatastore.getAge()
    }

    override suspend fun saveNickname(value: String) {
        signupDatastore.saveNickname(value)
    }

    override suspend fun saveSex(value: String) {
        signupDatastore.saveSex(value)
    }

    override suspend fun saveAge(value: String) {
        signupDatastore.saveAge(value)
    }
}