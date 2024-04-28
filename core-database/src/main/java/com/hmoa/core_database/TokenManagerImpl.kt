package com.hmoa.core_database

import android.content.Context
import android.util.Log
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
import kotlinx.coroutines.launch
import javax.inject.Inject

val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    corruptionHandler = ReplaceFileCorruptionHandler {
        it.printStackTrace()
        emptyPreferences()
    },
    scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    name = BuildConfig.LIBRARY_PACKAGE_NAME
)

class TokenManagerImpl @Inject constructor(@ApplicationContext context: Context) : TokenManager {
    private val dataStore = context.datastore

    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("AUTH_TOKEN")
        private val REMEMBERED_TOKEN_KEY = stringPreferencesKey("REMEMBERED_TOKEN")
        private val KAKAO_ACCESS_TOKEN_KEY = stringPreferencesKey("KAKAO_ACCESS_TOKEN")
        private val GOOGLE_ACCESS_TOKEN_KEY = stringPreferencesKey("GOOGLE_ACCESS_TOKEN")
    }

    override suspend fun getAuthToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            Log.d("TokenManagerImpl", "getAuthToken:${preferences[AUTH_TOKEN_KEY]}")
            preferences[AUTH_TOKEN_KEY]
        }
    }

    override suspend fun getRememberedToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            Log.d("TokenManagerImpl", "getRememeberedToken:${preferences[REMEMBERED_TOKEN_KEY]}")
            preferences[REMEMBERED_TOKEN_KEY]
        }
    }

    override suspend fun getKakaoAccessToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[KAKAO_ACCESS_TOKEN_KEY]
        }
    }

    override suspend fun getGoogleAccessToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[GOOGLE_ACCESS_TOKEN_KEY]
        }
    }

    override suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
            Log.d("TokenManagerImpl", "let's save authToken:${token}")
            Log.d("TokenManagerImpl", "it's saved authToken:${preferences[AUTH_TOKEN_KEY]}")
        }
    }

    override suspend fun saveRememberedToken(token: String) {
        dataStore.edit { preferences ->
            preferences[REMEMBERED_TOKEN_KEY] = token
            Log.d("TokenManagerImpl", "let's save rememberedToken:${token}}")
            Log.d("TokenManagerImpl", "it's saved rememberedToken:${preferences[REMEMBERED_TOKEN_KEY]}")
        }
    }

    override suspend fun saveKakaoAccessToken(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { preferences ->
                preferences[KAKAO_ACCESS_TOKEN_KEY] = token
            }
        }
    }

    override suspend fun saveGoogleAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[GOOGLE_ACCESS_TOKEN_KEY] = token
        }
    }

    override suspend fun deleteAuthToken() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
        }
    }

    override suspend fun deleteRememberedToken() {
        dataStore.edit { preferences ->
            preferences.remove(REMEMBERED_TOKEN_KEY)
        }
    }

    override suspend fun deleteKakaoAccessToken() {
        dataStore.edit { preferences ->
            preferences.remove(KAKAO_ACCESS_TOKEN_KEY)
        }
    }

    override suspend fun deleteGoogleAccessToken() {
        dataStore.edit { preferences ->
            preferences.remove(GOOGLE_ACCESS_TOKEN_KEY)
        }
    }
}