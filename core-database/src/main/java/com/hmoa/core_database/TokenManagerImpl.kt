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
import kotlinx.coroutines.flow.mapLatest
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

    override fun getAuthTokenForHeader(): String? {
        var token = runBlocking {
            getAuthToken().first()
        }
        //한 번 더 하는 이유는 첫 값이 null일 때가 있기 때문, 아직 원인은 모르는 문제이지만 임시방편으로 해결은 하는 것 뿐
        token = runBlocking {
            getAuthToken().first()
        }
        return token
    }

    override suspend fun getAuthToken(): Flow<String?> {
        return dataStore.data.mapLatest { preferences ->
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