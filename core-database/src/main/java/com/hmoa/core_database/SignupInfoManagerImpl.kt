package com.hmoa.core_database

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignupInfoManagerImpl @Inject constructor(@ApplicationContext context: Context) : SignupInfoManager {
    private val dataStore = context.datastore

    companion object {
        private val NICKNAME_KEY = stringPreferencesKey("NICKNAME")
        private val SEX_KEY = stringPreferencesKey("SEX")
        private val AGE_KEY = stringPreferencesKey("AGE")
    }

    override fun getNickname(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[NICKNAME_KEY]
        }
    }

    override fun getSex(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[SEX_KEY]
        }
    }

    override fun getAge(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[AGE_KEY]
        }
    }

    override suspend fun saveNickname(value: String) {
        dataStore.edit { preferences ->
            preferences[NICKNAME_KEY] = value
        }
    }

    override suspend fun saveSex(value: String) {
        dataStore.edit { preferences ->
            preferences[SEX_KEY] = value
        }
    }

    override suspend fun saveAge(value: String) {
        dataStore.edit { preferences ->
            preferences[AGE_KEY] = value
        }
    }
}