package com.hmoa.feature_fcm

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class TestCoroutineRule(
    private val testScope : TestScope = TestScope(),
    val testDispatcher: TestDispatcher = StandardTestDispatcher(testScope.testScheduler)
) : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)

        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)

        Dispatchers.resetMain()
    }

    fun runTest(block : suspend TestScope.() -> Unit) = testScope.runTest(testBody = block)
}