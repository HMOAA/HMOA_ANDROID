package com.hmoa.feature_fcm

import com.hmoa.core_model.response.AlarmResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FcmViewModelTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val fcmTestRepository = TestFcmRepository()
    lateinit var fcmViewModel : AlarmViewModel

    @Before
    fun setUp(){
        fcmViewModel = AlarmViewModel(
            fcmTestRepository
        )
        fcmTestRepository.setTestData(
            AlarmResponse(
                "content",
                createdAt = "4/10",
                deeplink = "",
                id = 0,
                read = false,
                title = "Event"
            )
        )
        fcmTestRepository.setType("success")
    }

    @Test
    fun `view model 초기 값`() = coroutineRule.runTest{
        Assert.assertEquals(AlarmUiState.Error, fcmViewModel.uiState.value)
    }

    @Test
    fun `view model fcm list 받아오기`() = coroutineRule.runTest{
        fcmTestRepository.setType("error")

        var result = fcmTestRepository.getFcmList()
        Assert.assertEquals(result.data, null)
        Assert.assertEquals(result.errorMessage?.code, "400")

        fcmTestRepository.setType("success")
        result = fcmTestRepository.getFcmList()
        Assert.assertEquals(1, result.data?.size)
    }

    @Test
    fun `알림을 눌러서 read를 변경할 경우`() = coroutineRule.runTest{
        fcmTestRepository.checkAlarm(0)
        val result = fcmTestRepository.getFcmList().data
        result?.forEach{
            Assert.assertEquals(true, it.read)
        }
    }
}