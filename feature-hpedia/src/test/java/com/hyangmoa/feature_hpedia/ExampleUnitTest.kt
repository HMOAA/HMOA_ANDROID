package com.hyangmoa.feature_hpedia

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    enum class A {
        a,
        b,
        c,
        d
    }

    enum class B {
        a,
        b,
        c,
        d
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(A.a.name == B.a.name, true)
    }
}