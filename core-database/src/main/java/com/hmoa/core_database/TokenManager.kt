package com.hmoa.core_database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenManager @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("AUTH_TOKEN")
        private val REMEMBERED_TOKEN_KEY = stringPreferencesKey("REMEBERED_TOKEN")
    }

    fun getAuthToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[AUTH_TOKEN_KEY]
        }
    }

    fun getRememberedToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[REMEMBERED_TOKEN_KEY]
        }
    }

    suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    suspend fun saveRememberedToken(token: String) {
        dataStore.edit { preferences ->
            preferences[REMEMBERED_TOKEN_KEY] = token
        }
    }

    suspend fun deleteAccessToken() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
        }
    }

    suspend fun deleteRememberedToken() {
        dataStore.edit { preferences ->
            preferences.remove(REMEMBERED_TOKEN_KEY)
        }
    }
}