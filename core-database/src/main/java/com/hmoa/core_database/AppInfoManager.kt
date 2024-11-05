package com.hmoa.core_database

import kotlinx.coroutines.flow.Flow

interface AppInfoManager {
    suspend fun saveNotificationEnabled(isEnabled : Boolean)
    suspend fun getNotificationEnabled() : Flow<Boolean>
    suspend fun updateGalleryEnabled(isEnabled : Boolean)
    suspend fun isGalleryEnabled() : Flow<Boolean>
}