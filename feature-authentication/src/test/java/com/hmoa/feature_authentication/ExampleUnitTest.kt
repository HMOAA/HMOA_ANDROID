package com.hmoa.feature_authentication

import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    //언제 Result.Error가 언제 나오는지 테스트
    @Test
    suspend fun ResultResponseErrorTest() {
        val exceptionCases = listOf<Any>("good", "nice", Exception("500"))
        flow {
            exceptionCases.map { emit(it) }
        }.asResult().collectLatest {
            when (it) {
                is Result.Success -> {

                }

                is Result.Loading -> {

                }

                is Result.Error -> {

                }
            }
        }
    }
}