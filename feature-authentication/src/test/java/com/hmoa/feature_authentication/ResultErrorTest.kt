package com.hmoa.feature_authentication

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable) : Result<Nothing>
    data object Loading : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> = map<T, Result<T>> { Result.Success(it) }
    .onStart { emit(Result.Loading) }
    .catch { emit(Result.Error(it)) }

class ExampleUnitTest {

    //언제 Result.Error가 언제 나오는지 테스트
    @Test
    @ExperimentalCoroutinesApi
    fun ResultResponseErrorTest() = runTest {
        val exceptionCases = listOf<Any>("good", "good", Exception("500"))
        launch {
            flow { exceptionCases.map { emit(it) } }.asResult().collectLatest {
                Assertions.assertEquals(it, "good")
            }
            //Assertions.assertEquals(exceptionCases[0], "good")
//            flow {
//                exceptionCases.map { emit(it) }
//            }.asResult().collectLatest {
//                when (it) {
//                    is Result.Success -> {
//                        Assertions.assertEquals(it.data, "good")
//                    }
//
//                    is Result.Loading -> {
//                        Log.i("Loading", "Loading...")
//                    }
//
//                    is Result.Error -> {
//                        Assertions.assertEquals(it, Exception("500"))
//                    }
//                }
//
//            }
        }
    }
}