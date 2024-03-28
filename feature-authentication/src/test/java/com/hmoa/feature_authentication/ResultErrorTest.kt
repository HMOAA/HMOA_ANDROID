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
    .catch {
        emit(Result.Error(it))
    }

class ResponseException(override var message: String) : Throwable() {

}


class ExampleUnitTest {

    @Test
    @ExperimentalCoroutinesApi
    fun When_Exception_Expected_ResultError_Test() = runTest {
        val exceptionCases = listOf<Any?>("data", ResponseException("500"))
        launch {
            flow {
                exceptionCases.map {
                    if (it is Exception) {
                        throw it
                    } else {
                        emit(it)
                    }
                }
            }.asResult().collectLatest {
                when (it) {
                    is Result.Success -> {
                        Assertions.assertEquals(Result.Success(it).data, it)
                        println("Result.Success 상태일 때 기대값=${Result.Success(it).data}, 실제값=${it.data}")
                    }

                    is Result.Loading -> {
                        Assertions.assertEquals(Result.Loading, it)
                        println("Result.Loading 상태일 때 기대값=${Result.Loading}, 실제값=${it}")
                    }

                    is Result.Error -> {
                        Assertions.assertNotEquals(Result.Error(it.exception), it.exception)
                        println("Result.Error 상태일 때 기대값=${Result.Error(it.exception)}, 실제값=${it.exception}")
                    }
                }

            }
        }
    }
}