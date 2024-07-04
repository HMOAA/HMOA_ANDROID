package com.hmoa.core_database

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppInfoManagerImpl @Inject constructor(
    @ApplicationContext context : Context
) : AppInfoManager {
    companion object{
        private val IS_NOTIFICATION_ENABLED = booleanPreferencesKey("is_notification_enabled")
    }
    private val dataStore = context.datastore

    override suspend fun saveNotificationEnabled(isEnabled: Boolean) {
        dataStore.edit{
            it[IS_NOTIFICATION_ENABLED] = isEnabled
        }
    }

    override suspend fun getNotificationEnabled(): Flow<Boolean> {
        return dataStore.data.map{
            it[IS_NOTIFICATION_ENABLED] ?: false
        }
    }
}