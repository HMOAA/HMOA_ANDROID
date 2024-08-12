package com.hmoa.core_database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DataStorePreferenceTest : TestCase() {
    @get:Rule(order = 0)
    val coroutineRule = TestCoroutineRule()

    lateinit var tokenManagerImpl: TokenManagerImpl
    val Context.datastore: DataStore<Preferences> by preferencesDataStore(
        corruptionHandler = ReplaceFileCorruptionHandler {
            it.printStackTrace()
            emptyPreferences()
        },
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        name = BuildConfig.LIBRARY_PACKAGE_NAME
    )

    @Before
    override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        tokenManagerImpl = TokenManagerImpl(context)
    }

    @Test
    fun test_dataStorePreference_tokenInput_tokenOutput() = coroutineRule.runTest {
        val expectedToken = "token"
        launch { tokenManagerImpl.saveAuthToken(expectedToken) }.join()
        val token = tokenManagerImpl.getAuthTokenForHeader()
        assertEquals(expectedToken, token)
    }
}