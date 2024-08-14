package com.hmoa.core_database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.runBlocking

class TestTokenManagerImpl(private val dataStore: DataStore<Preferences>) {

    companion object {
        val AUTH_TOKEN_KEY = stringPreferencesKey("AUTH_TOKEN")
    }

    fun getAuthTokenForHeader(): String? {
        val token = runBlocking {
            getAuthToken().first()
        }
        return token
    }

    fun getAuthToken(): Flow<String?> {
        return dataStore.data.mapLatest { preferences ->
            preferences[AUTH_TOKEN_KEY]
        }
    }


    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }
}