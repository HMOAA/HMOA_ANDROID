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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
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
        private val FCM_TOKEN_KEY = stringPreferencesKey("FCM_TOKEN")
    }

    override fun getAuthTokenForHeader(): String {
        val token: String? = runBlocking {
            getAuthToken().first()
        }
        if (token != null) {
            Log.d("TokenManagerImpl", "getAuthTokenForHeader: AuthToken(accessToken):${token}")
        }
        return token ?: ""
    }

    override suspend fun getAuthToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            Log.d("TokenManagerImpl", "getAuthToken: AuthToken(accessToken):${preferences[AUTH_TOKEN_KEY]}")
            preferences[AUTH_TOKEN_KEY]
        }
    }


    override suspend fun getRememberedToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
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
            val p = preferences[GOOGLE_ACCESS_TOKEN_KEY]
            Log.d("TokenManagerImpl", "${p}")
            preferences[GOOGLE_ACCESS_TOKEN_KEY]
        }
    }

    override suspend fun getFcmToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[FCM_TOKEN_KEY]
        }
    }

    override suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    override suspend fun saveRememberedToken(token: String) {
        dataStore.edit { preferences ->
            preferences[REMEMBERED_TOKEN_KEY] = token
        }
    }

    override suspend fun saveKakaoAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KAKAO_ACCESS_TOKEN_KEY] = token
        }
    }

    override suspend fun saveGoogleAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[GOOGLE_ACCESS_TOKEN_KEY] = token
        }
    }

    override suspend fun saveFcmToken(token: String) {
        dataStore.edit { preferences ->
            preferences[FCM_TOKEN_KEY] = token
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

    override suspend fun deleteFcmToken() {
        dataStore.edit { preferences ->
            preferences.remove(FCM_TOKEN_KEY)
        }
    }
}
