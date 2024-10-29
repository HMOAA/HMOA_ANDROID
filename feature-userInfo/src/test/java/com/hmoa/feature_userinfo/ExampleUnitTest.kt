package com.hmoa.feature_userinfo

import com.hmoa.core_model.data.OrderStatus
import com.hmoa.core_model.response.FinalOrderResponseDto
import com.hmoa.core_model.response.GetRefundRecordResponseDto
import com.hmoa.core_model.response.PostNoteSelectedResponseDto
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    lateinit var dummyDate: List<GetRefundRecordResponseDto>
    @Before
    fun setting(){
        dummyDate = listOf(
            GetRefundRecordResponseDto(
                orderId = 0,
                orderStatus = OrderStatus.PAY_CANCEL,
                orderProducts = FinalOrderResponseDto(
                    paymentAmount = 0,
                    productInfo = PostNoteSelectedResponseDto(emptyList(), 0),
                    shippingAmount = 0,
                    totalAmount = 0
                ),
                createdAt = "2024/10/01",
                courierCompany = null,
                trackingNumber = null
            ),
            GetRefundRecordResponseDto(
                orderId = 0,
                orderStatus = OrderStatus.RETURN_PROGRESS,
                orderProducts = FinalOrderResponseDto(
                    paymentAmount = 0,
                    productInfo = PostNoteSelectedResponseDto(emptyList(), 0),
                    shippingAmount = 0,
                    totalAmount = 0
                ),
                createdAt = "2024/10/01",
                courierCompany = null,
                trackingNumber = null
            ),
            GetRefundRecordResponseDto(
                orderId = 0,
                orderStatus = OrderStatus.PAY_CANCEL,
                orderProducts = FinalOrderResponseDto(
                    paymentAmount = 0,
                    productInfo = PostNoteSelectedResponseDto(emptyList(), 0),
                    shippingAmount = 0,
                    totalAmount = 0
                ),
                createdAt = "2024/09/21",
                courierCompany = null,
                trackingNumber = null
            ),
            GetRefundRecordResponseDto(
                orderId = 0,
                orderStatus = OrderStatus.RETURN_COMPLETE,
                orderProducts = FinalOrderResponseDto(
                    paymentAmount = 0,
                    productInfo = PostNoteSelectedResponseDto(emptyList(), 0),
                    shippingAmount = 0,
                    totalAmount = 0
                ),
                createdAt = "2024/10/01",
                courierCompany = null,
                trackingNumber = null
            ),
            GetRefundRecordResponseDto(
                orderId = 0,
                orderStatus = OrderStatus.RETURN_COMPLETE,
                orderProducts = FinalOrderResponseDto(
                    paymentAmount = 0,
                    productInfo = PostNoteSelectedResponseDto(emptyList(), 0),
                    shippingAmount = 0,
                    totalAmount = 0
                ),
                createdAt = "2024/09/09",
                courierCompany = null,
                trackingNumber = null
            )
        )
    }
    @Test
    fun sort_test() {
        val expected = listOf(
            GetRefundRecordResponseDto(
                orderId = 0,
                orderStatus = OrderStatus.RETURN_COMPLETE,
                orderProducts = FinalOrderResponseDto(
                    paymentAmount = 0,
                    productInfo = PostNoteSelectedResponseDto(emptyList(), 0),
                    shippingAmount = 0,
                    totalAmount = 0
                ),
                createdAt = "2024/09/09",
                courierCompany = null,
                trackingNumber = null
            ),
            GetRefundRecordResponseDto(
                orderId = 0,
                orderStatus = OrderStatus.PAY_CANCEL,
                orderProducts = FinalOrderResponseDto(
                    paymentAmount = 0,
                    productInfo = PostNoteSelectedResponseDto(emptyList(), 0),
                    shippingAmount = 0,
                    totalAmount = 0
                ),
                createdAt = "2024/09/21",
                courierCompany = null,
                trackingNumber = null
            ),
            GetRefundRecordResponseDto(
                orderId = 0,
                orderStatus = OrderStatus.PAY_CANCEL,
                orderProducts = FinalOrderResponseDto(
                    paymentAmount = 0,
                    productInfo = PostNoteSelectedResponseDto(emptyList(), 0),
                    shippingAmount = 0,
                    totalAmount = 0
                ),
                createdAt = "2024/10/01",
                courierCompany = null,
                trackingNumber = null
            ),
            GetRefundRecordResponseDto(
                orderId = 0,
                orderStatus = OrderStatus.RETURN_PROGRESS,
                orderProducts = FinalOrderResponseDto(
                    paymentAmount = 0,
                    productInfo = PostNoteSelectedResponseDto(emptyList(), 0),
                    shippingAmount = 0,
                    totalAmount = 0
                ),
                createdAt = "2024/10/01",
                courierCompany = null,
                trackingNumber = null
            ),
            GetRefundRecordResponseDto(
                orderId = 0,
                orderStatus = OrderStatus.RETURN_COMPLETE,
                orderProducts = FinalOrderResponseDto(
                    paymentAmount = 0,
                    productInfo = PostNoteSelectedResponseDto(emptyList(), 0),
                    shippingAmount = 0,
                    totalAmount = 0
                ),
                createdAt = "2024/10/01",
                courierCompany = null,
                trackingNumber = null
            )
        )
        assertEquals(expected, sortData(dummyDate))
    }

    private fun sortData(data: List<GetRefundRecordResponseDto>): List<GetRefundRecordResponseDto>{
        val result = data.toMutableList()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val statusPriority = listOf(OrderStatus.PAY_CANCEL, OrderStatus.RETURN_PROGRESS, OrderStatus.RETURN_COMPLETE)
        result.sortWith(compareBy<GetRefundRecordResponseDto>{
            LocalDate.parse(it.createdAt, dateFormatter)
        }.thenBy{
            statusPriority.indexOf(it.orderStatus)
        })
        return result
    }
}