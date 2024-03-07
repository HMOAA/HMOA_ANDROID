package com.hmoa.core_domain.repository

interface SignupRepository {
    suspend fun getNickname(): String?
    suspend fun getSex(): String?
    suspend fun getAge(): String?

    suspend fun saveNickname(value: String)
    suspend fun saveSex(value: String)
    suspend fun saveAge(value: String)
}