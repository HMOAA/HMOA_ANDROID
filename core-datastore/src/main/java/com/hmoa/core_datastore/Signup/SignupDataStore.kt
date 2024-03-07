package com.hmoa.core_datastore.Signup

interface SignupDataStore {
    suspend fun getNickname(): String?
    suspend fun getSex(): String?
    suspend fun getAge(): String?
}