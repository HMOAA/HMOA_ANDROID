package com.hyangmoa.core_database

import kotlinx.coroutines.flow.Flow

interface SignupInfoManager {
    fun getNickname(): Flow<String?>
    fun getSex(): Flow<String?>
    fun getAge(): Flow<String?>
    suspend fun saveNickname(value: String)
    suspend fun saveSex(value: String)
    suspend fun saveAge(value: String)
}