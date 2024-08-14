package com.hmoa.core_database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import junit.framework.TestCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.mockito.Mockito.mock

class DataStorePreferenceTest : TestCase() {
    @get:Rule(order = 0)
    val coroutineRule = TestCoroutineRule()
    private val dataStore = mock(DataStore::class.java) as DataStore<Preferences>
    private val tokenManagerImpl = TestTokenManagerImpl(dataStore)
    private val authTokenKey = TestTokenManagerImpl.AUTH_TOKEN_KEY

    @Test
    fun test_dataStorePreference_tokenInput() = coroutineRule.runTest {
        val tokenValue = "token"
        val inputPreference = mock(Preferences::class.java)

        Mockito.`when`(dataStore.edit { any() }).thenAnswer { invocation ->
            val editAction = invocation.getArgument<Preferences.() -> Unit>(0)
            editAction(inputPreference)
        }

        launch { tokenManagerImpl.saveAuthToken(tokenValue) }.join()
        Mockito.verify(inputPreference)[eq(TestTokenManagerImpl.AUTH_TOKEN_KEY)]
    }

    @Test
    fun test_dataStorePreference_tokenOutput() = coroutineRule.runTest {
        val tokenValue = "token"
        val outputPreferences = preferencesOf(authTokenKey to tokenValue)
        Mockito.`when`(dataStore.data).thenReturn(flowOf(outputPreferences))
        val result = tokenManagerImpl.getAuthTokenForHeader()
        assertEquals(tokenValue, result)
    }
}

