package com.hmoa.core_network

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@Serializable
data class TestRequestData(
    val consonant : Int,
    val brandList : List<Item>
)

@Serializable
data class Item(
    val brandId : Int,
    val brandName : String,
    val englishName : String,
    val brandImageUrl : String,
)

@RunWith(AndroidJUnit4::class)
class ExampleServiceTest{

    private lateinit var httpClient : HttpClient

    @Before
    fun setUp(){
        httpClient = HttpClient(Android){
            install(Logging) {
                level = LogLevel.ALL
            }
            install(DefaultRequest){
                url("https://hmoa.inuappcenter.kr")
            }
            install(ContentNegotiation) {
                json()
            }
        }
    }

    @Test
    fun testService(){
        runBlocking{
            val response = api()
            assertNotNull(response)

            assertEquals(4, response.brandList.size)
        }
    }

    suspend fun api() : TestRequestData{
        val response : HttpResponse = httpClient.get("/search/brand"){
            url{
                parameters.append("searchWord","조")
            }
        }
        return response.body()
    }
}
