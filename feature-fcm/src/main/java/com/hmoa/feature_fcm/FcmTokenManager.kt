package com.hmoa.feature_fcm

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.fcmTokenDatabase : DataStore<Preferences> by preferencesDataStore (
    corruptionHandler = ReplaceFileCorruptionHandler {
        it.printStackTrace()
        emptyPreferences()
    },
    scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    name = "FcmTokenDatabase"
)

class FcmTokenManager @Inject constructor(
    @ApplicationContext context : Context
) {
    companion object{
        private val FCM_TOKEN = stringPreferencesKey("FcmToken")
    }
    private val dataStore = context.fcmTokenDatabase
    suspend fun saveToken(token : String) {
        dataStore.edit{ preferences ->
            preferences[FCM_TOKEN] = token
        }
    }

    suspend fun getToken() : Flow<String?> {
        return dataStore.data.map{
            it[FCM_TOKEN]
        }
    }

    suspend fun deleteToken(){
        dataStore.edit{
            it.remove(FCM_TOKEN)
        }
    }
}